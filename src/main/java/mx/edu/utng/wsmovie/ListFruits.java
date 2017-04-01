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

public class ListFruits extends ListActivity {
    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Fruit> fruits = new ArrayList<Fruit>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListFruits.FruitWSSelect select=new ListFruits.FruitWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_item:

                Fruit fruit = fruits.get(selectedPosition);
                Bundle bundleFruit = new Bundle();
                for (int i = 0; i < fruit.getPropertyCount(); i++) {
                    bundleFruit.putString("valor" + i, fruit.getProperty(i)
                            .toString());
                }
                bundleFruit.putString("accion", "modificar");
                Intent intent = new Intent(ListFruits.this, FruitActivity.class);
                intent.putExtras(bundleFruit);
                startActivity(intent);

                return true;
            case R.id.remove_item:
                ListFruits.FruitWSDelete delete = new ListFruits.FruitWSDelete();
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
                startActivity(new Intent(ListFruits.this, FruitActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class FruitWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getFruits";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            fruits.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(FruitActivity.URL);

            try {
                transport.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                       Fruit fruit = new Fruit();

                        fruit.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));
                         fruit.setProperty(1, objSoap.getProperty("name")
                                .toString());
                         fruit.setProperty(2, objSoap.getProperty("flavor")
                                .toString());
                         fruit.setProperty(3, objSoap.getProperty("colour")
                                .toString());
                         fruit.setProperty(4, Float.parseFloat(objSoap
                                .getProperty("price").toString()));

                        fruits.add(fruit);
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
                    Fruit fruit = new Fruit();
                    fruit.setProperty(0, Integer.parseInt(objSoap
                            .getProperty("id").toString()));
                    fruit.setProperty(1, objSoap.getProperty("name")
                            .toString());
                    fruit.setProperty(2, objSoap.getProperty("flavor")
                            .toString());
                    fruit.setProperty(2, objSoap.getProperty("colour")
                            .toString());
                    fruit.setProperty(3, Float.parseFloat(objSoap
                            .getProperty("price").toString()));
                    fruits.add(fruit);
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
                final String[] data = new String[fruits.size()];
                for (int i = 0; i < fruits.size(); i++) {
                    data[i] = fruits.get(i).getProperty(0) + " - "
                            + fruits.get(i).getProperty(1);
                }
//////////////////////////////////este layout
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListFruits.this,
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
        idSelected = (Integer) fruits.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class FruitWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeFruit";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(FruitActivity.URL);
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
                ListFruits.FruitWSSelect query = new ListFruits.FruitWSSelect();
                query.execute();
            } else {
                Toast.makeText(getApplicationContext(), "remove error",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }
}
