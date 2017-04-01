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

public class ListMine extends ListActivity {
    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Me> mine = new ArrayList<Me>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListMine.MeWSSelect select=new ListMine.MeWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_item:

                Me me = mine.get(selectedPosition);
                Bundle bundleMe = new Bundle();
                for (int i = 0; i < me.getPropertyCount(); i++) {
                    bundleMe.putString("valor" + i, me.getProperty(i)
                            .toString());
                }
                bundleMe.putString("accion", "modificar");
                Intent intent = new Intent(ListMine.this, MeActivity.class);
                intent.putExtras(bundleMe);
                startActivity(intent);

                return true;
            case R.id.remove_item:
                ListMine.MeWSDelete delete = new ListMine.MeWSDelete();
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
                startActivity(new Intent(ListMine.this, MeActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class MeWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getMine";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            mine.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(MeActivity.URL);

            try {
                transport.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                        Me me = new Me();

                        me.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));
                        me.setProperty(1, objSoap.getProperty("fullname")
                                .toString());
                        me.setProperty(2, objSoap.getProperty("nickname")
                                .toString());
                        me.setProperty(3, objSoap.getProperty("age")
                                .toString());
                        me.setProperty(4, objSoap.getProperty("hobby")
                                .toString());

                        mine.add(me);
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
                    Me me = new Me();
                    me.setProperty(0, Integer.parseInt(objSoap
                            .getProperty("id").toString()));
                    me.setProperty(1, objSoap.getProperty("fullname")
                            .toString());
                    me.setProperty(2, objSoap.getProperty("nickname")
                            .toString());
                    me.setProperty(3, objSoap.getProperty("age")
                            .toString());
                    me.setProperty(4, objSoap.getProperty("hobby")
                            .toString());
                    mine.add(me);
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
                final String[] data = new String[mine.size()];
                for (int i = 0; i < mine.size(); i++) {
                    data[i] = mine.get(i).getProperty(0) + " - "
                            + mine.get(i).getProperty(1);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListMine.this,
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
        idSelected = (Integer) mine.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class MeWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeMe";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(MeActivity.URL);
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
                ListMine.MeWSSelect query = new ListMine.MeWSSelect();
                query.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error on remove",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}
