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
import android.widget.ToggleButton;

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

public class FruitActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etFlavor;
    private EditText etColour;
    private EditText etPrice;
    private Button btSave;
    private Button btList;

    private Fruit fruit = null;

    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.126:8080/WSMovie/services/FruitWS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        initComponents();
    }
    private void initComponents(){
        etName = (EditText)findViewById(R.id.et_name);
        etFlavor = (EditText)findViewById(R.id.et_flavor);
        etColour = (EditText)findViewById(R.id.et_colour);
        etPrice = (EditText)findViewById(R.id.et_price);
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
                    FruitActivity.FruitWSUpdate fruitWSUpdate = new FruitActivity.FruitWSUpdate();
                    fruitWSUpdate.execute();
                }

            } catch (Exception e) {
                //Cuando no se haya mandado una accion por defecto es insertar.
                FruitActivity.FruitWSInsert insert = new FruitActivity.FruitWSInsert();
                insert.execute();
            }
        }
        if (btList.getId() == v.getId()) {
            startActivity(new Intent(FruitActivity.this, ListFruits.class));
        }
    }
    private class FruitWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addFruit";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            fruit = new Fruit();
            fruit.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("fruit");
            info.setValue(fruit);
            info.setType(fruit.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Fruit", Fruit.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

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
                        "Insert was success",
                        Toast.LENGTH_SHORT).show();
                cleanBox();

            }else {
                Toast.makeText(getApplicationContext(),
                        "Error on insert",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void cleanBox(){
        etName.setText("");
        etFlavor.setText("");
        etColour.setText("");
        etPrice.setText("");

    }
    private class FruitWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateFruit";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            fruit = new Fruit();
            fruit.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("fruit");
            info.setValue(fruit);
            info.setType(fruit.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Fruit", fruit.getClass());

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
                Toast.makeText(getApplicationContext(), "Update success",
                        Toast.LENGTH_SHORT).show();
                cleanBox();
                startActivity(new Intent(FruitActivity.this, FruitActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Error on update",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//
    private void getData() {
        fruit.setProperty(1, etName.getText().toString());
        fruit.setProperty(2, etFlavor.getText().toString());
        fruit.setProperty(3, etColour.getText().toString());
        fruit.setProperty(4, Float.parseFloat(
                etPrice.getText().toString()));

    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle returnData = this.getIntent().getExtras();
        try {
            Log.i("Data", returnData.getString("valor4"));

            etName.setText(returnData.getString("valor1"));
            etFlavor.setText(returnData.getString("valor2"));
            etColour.setText(returnData.getString("valor3"));
            etPrice.setText(returnData.getString("valor4"));

        } catch (Exception e) {
            Log.e("Load error", e.toString());
        }
    }
}

