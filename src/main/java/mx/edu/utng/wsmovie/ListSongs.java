package mx.edu.utng.wsmovie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ListSongs extends ListActivity {
    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Song> songs = new ArrayList<Song>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_songs);
        //super.onCreate(savedInstanceState);
        ListSongs.SongWSSelect select=new ListSongs.SongWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_item:

                Song song = songs.get(selectedPosition);
                Bundle bundleSong = new Bundle();
                for (int i = 0; i < song.getPropertyCount(); i++) {
                    bundleSong.putString("valor" + i, song.getProperty(i)
                            .toString());
                }
                bundleSong.putString("accion", "modificar");
                Intent intent = new Intent(ListSongs.this, SongActivity.class);
                intent.putExtras(bundleSong);
                startActivity(intent);

                return true;
            case R.id.remove_item:
                ListSongs.SongWSDelete delete = new ListSongs.SongWSDelete();
                delete.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_item:
                startActivity(new Intent(ListSongs.this, SongActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class SongWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getSongs";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            songs.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(SongActivity.URL);

            try {
                transport.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                        Song song = new Song();

                        song.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));
                        song.setProperty(1, objSoap.getProperty("name")
                                .toString());
                        song.setProperty(2, objSoap.getProperty("author")
                                .toString());
                        song.setProperty(3, objSoap.getProperty("album")
                                .toString());
                        song.setProperty(4, objSoap.getProperty("year")
                                .toString());

                        songs.add(song);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {

                //Enviará aquí cuando exista un solo registro en la base.
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    Song song = new Song();
                    song.setProperty(0, Integer.parseInt(objSoap
                            .getProperty("id").toString()));
                    song.setProperty(1, objSoap.getProperty("name")
                            .toString());
                    song.setProperty(2, objSoap.getProperty("author")
                            .toString());
                    song.setProperty(3, objSoap.getProperty("album")
                            .toString());
                    song.setProperty(4, objSoap.getProperty("year")
                            .toString());
                   songs.add(song);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                final String[] data = new String[songs.size()];
                for (int i = 0; i < songs.size(); i++) {
                    data[i] = songs.get(i).getProperty(0) + " - "
                            + songs.get(i).getProperty(1);
                }
//////////////////////////////////este layout
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListSongs.this,
                        android.R.layout.simple_list_item_1, data);
                setListAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "Not found data",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }//

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position)
                .toString());
        idSelected = (Integer) songs.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class SongWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeSong";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(SongActivity.URL);
            try {
                transport.call(SOAP_ACTION, envelope);
                SoapPrimitive result_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = result_xml.toString();

                if (!res.equals("0")){
                    result = true;}

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Removed", Toast.LENGTH_SHORT).show();
                ListSongs.SongWSSelect query = new ListSongs.SongWSSelect();
                query.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error on remove",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}

