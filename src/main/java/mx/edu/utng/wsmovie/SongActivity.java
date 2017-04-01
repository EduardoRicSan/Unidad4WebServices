package mx.edu.utng.wsmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SongActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etName;
    private EditText etAuthor;
    private EditText etAlbum;
    private EditText etYear;
    private Button btSave;
    private Button btList;

    private Song song = null;

    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.126:8080/WSMovie/services/SongWS";

            //192.168.0.7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        initComponents();
    }
    private void initComponents(){
        etName = (EditText)findViewById(R.id.et_name_song);
        etAuthor = (EditText)findViewById(R.id.et_author);
        etAlbum = (EditText)findViewById(R.id.et_album);
        etYear = (EditText)findViewById(R.id.et_year);
        btSave = (Button) findViewById(R.id.bt_save);
        btList = (Button)findViewById(R.id.bt_list);
        btSave.setOnClickListener(this);
        btList.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== btSave.getId()){
            try {
                if (getIntent().getExtras().getString("accion")
                        .equals("modificar")) {
                    SongActivity.SongWSUpdate songWSUpdate = new SongActivity.SongWSUpdate();
                    songWSUpdate.execute();
                }

            } catch (Exception e) {
                //Cuando no se haya mandado una accion por defecto es insertar.
                SongActivity.SongWSInsert insert = new SongActivity.SongWSInsert();
                insert.execute();
            }
        }
        if (btList.getId() == v.getId()) {
            startActivity(new Intent(SongActivity.this, ListSongs.class));
        }
    }
    private class SongWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addSong";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            song = new Song();
            song.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("song");
            info.setValue(song);
            info.setType(song.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Song", Song.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);*/

            HttpTransportSE transport = new HttpTransportSE(URL);
            try {
                transport.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast.makeText(getApplicationContext(),
                        "Success insertion.",
                        Toast.LENGTH_SHORT).show();
                cleanBox();

            }else {
                Toast.makeText(getApplicationContext(),
                        "Error on Insert",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void cleanBox(){
        etName.setText("");
        etAuthor.setText("");
        etAlbum.setText("");
        etYear.setText("");

    }
    private class SongWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateSong";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            song = new Song();
            song.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("song");
            info.setValue(song);
            info.setType(song.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Song", song.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transport = new HttpTransportSE(URL);

            try {
                transport.call(SOAP_ACTION,envelope);
                SoapPrimitive result_xml = (SoapPrimitive) envelope.getResponse();
                String res = result_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(), "Update Success",
                        Toast.LENGTH_SHORT).show();
                cleanBox();
                startActivity(new Intent(SongActivity.this, SongActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Error on update",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void getData() {
        song.setProperty(1, etName.getText().toString());
        song.setProperty(2, etAuthor.getText().toString());
        song.setProperty(3, etAlbum.getText().toString());
        song.setProperty(4, etYear.getText().toString());


    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle returnData = this.getIntent().getExtras();
        try {
            Log.i("Data", returnData.getString("valor4"));

            etName.setText(returnData.getString("valor1"));
            etAuthor.setText(returnData.getString("valor2"));
            etAlbum.setText(returnData.getString("valor3"));
            etYear.setText(returnData.getString("valor4"));
        } catch (Exception e) {
            Log.e("Loading Error ", e.toString());
        }
    }
}


