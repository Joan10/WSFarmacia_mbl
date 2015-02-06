package com.wsfarmacia_mbl;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;



public class ConnexioServidor {


	public ConnexioServidor(){
		Async_getNoticia myRequest = new Async_getNoticia();
		myRequest.execute();
	}
	



/*onPreExecute() – Fahrenheit textview control is set with text ‘Calculation…’
doInBackground() – Invoke getFahrenheit()
onProgressUpdate() -Does nothing
onPostExecute() – Set the Fahrenheit value in textview control
*/
    private class Async_getNoticia extends AsyncTask<String, Void, Void> {
	
		private String TAG = "WEBSERVICE";
	    private static final String SOAP_ACTION = "http://footballpool.dataaccess.eu/TopGoalScorers";
	    private static final String METHOD_NAME = "TopGoalScorers";
	    private static final String NAMESPACE = "http://footballpool.dataaccess.eu";
	    private static final String URL = "http://footballpool.dataaccess.eu/data/info.wso?WSDL";
	    private String response;
	    
	    @Override
	    protected Void doInBackground(String... params) {
	        Log.i(TAG, "doInBackground");
	        
	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        request.addProperty("iTopN", "5"); 
	
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
	        envelope.setOutputSoapObject(request);  
	
	        HttpTransportSE httpTransport = new HttpTransportSE(URL);
	
	        httpTransport.debug = true;  
	        try {
	            httpTransport.call(SOAP_ACTION, envelope);
	        } catch (HttpResponseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (XmlPullParserException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } //send request
	        SoapObject result = null;
	        try {
	            result = (SoapObject)envelope.getResponse();
	        } catch (SoapFault e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } 
	
	        Log.d("App",""+result.getProperty(1).toString());
	        response = result.getProperty(1).toString();
	        return null;
	        
	        
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        Log.i(TAG, "onPostExecute");
	    }
	
	    @Override
	    protected void onPreExecute() {
	        Log.i(TAG, "onPreExecute");
	    }
	
	    @Override
	    protected void onProgressUpdate(Void... values) {
	        Log.i(TAG, "onProgressUpdate");
	    }
    }
}