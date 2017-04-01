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

public class ListCompanies extends ListActivity {
    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Company> companies = new ArrayList<Company>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListCompanies.CompanyWSSelect select=new ListCompanies.CompanyWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_item:

                Company company= companies.get(selectedPosition);
                Bundle bundleCompany = new Bundle();
                for (int i = 0; i < company.getPropertyCount(); i++) {
                    bundleCompany.putString("valor" + i, company.getProperty(i)
                            .toString());
                }
                bundleCompany.putString("accion", "modificar");
                Intent intent = new Intent(ListCompanies.this, CompanyActivity.class);
                intent.putExtras(bundleCompany);
                startActivity(intent);

                return true;
            case R.id.remove_item:
                ListCompanies.CompanyWSDelete delete = new ListCompanies.CompanyWSDelete();
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
                startActivity(new Intent(ListCompanies.this, CompanyActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class CompanyWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getCompanies";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            companies.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(CompanyActivity.URL);

            try {
                transport.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                        Company company = new Company();

                        company.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));
                        company.setProperty(1, objSoap.getProperty("name")
                                .toString());
                        company.setProperty(2, objSoap.getProperty("owner")
                                .toString());
                        company.setProperty(3, objSoap.getProperty("foundation")
                                .toString());
                        company.setProperty(4, objSoap.getProperty("type")
                                .toString());
                        company.setProperty(5, objSoap.getProperty("objetive")
                                .toString());
                        company.setProperty(6, objSoap.getProperty("partner")
                                .toString());

                        companies.add(company);
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
                    Company company = new Company();
                    company.setProperty(0, Integer.parseInt(objSoap.getProperty(
                            "id").toString()));
                    company.setProperty(1, objSoap.getProperty("name")
                            .toString());
                    company.setProperty(2, objSoap.getProperty("owner")
                            .toString());
                    company.setProperty(3, objSoap.getProperty("foundation")
                            .toString());
                    company.setProperty(4, objSoap.getProperty("type")
                            .toString());
                    company.setProperty(5, objSoap.getProperty("objetive")
                            .toString());
                    company.setProperty(6, objSoap.getProperty("partner")
                            .toString());
                    companies.add(company);
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
                final String[] data = new String[companies.size()];
                for (int i = 0; i < companies.size(); i++) {
                    data[i] = companies.get(i).getProperty(0) + " - "
                            + companies.get(i).getProperty(1);
                }
//////////////////////////////////este layout
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListCompanies.this,
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
        idSelected = (Integer) companies.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class CompanyWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeCompany";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(CompanyActivity.URL);
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
                ListCompanies.CompanyWSSelect query = new ListCompanies.CompanyWSSelect();
                query.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error on remove",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}





