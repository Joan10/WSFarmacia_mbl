package com.wsfarmacia_mbl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class ConnexioServidor {

	
	private static final String ELEMENT_SEPARATOR = "@@LTIM@@";
	private static final String ENTRADA_SEPARATOR = "@@LTIMNL@@"; 
	 
	private boolean acabat=false;
	private String response="";
		
	
	Async_getBBDD myRequest;
    
	public ConnexioServidor(){

		//parse response
	}

	
	public void consultaBBDD(String XMLrequest) throws Exception{
		
		myRequest = new Async_getBBDD(XMLrequest);
		myRequest.execute();
		acabat = false;
		
		for (int i=0; i<10 && (!acabat); i++){
		//Espera activa mentre es carrega la pàgina. La i fa de timeout.	
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (acabat == false) throw new Exception();
		
		
	}
	
	public String getMedicamentFromId(String id){

		try {
			this.consultaBBDD("medicamentos@@LTIM@@lista");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
		
		for (int j = 0; j < this.getNumEntrades(); j++){
			//Cercam entre totes les entrades el medicament amb el mateix id.
			if (this.treuElement(j, 0).equals(id)) {
				//En trobar-lo posam el nom del medicament a la var. medicament
				return this.treuElement(j, 4);

			}
		}
		
		return "";
	}

	public String getFarmaciaFromId(String id){

		try {
			this.consultaBBDD("farmacias@@LTIM@@lista");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
		
		for (int j = 0; j < this.getNumEntrades(); j++){
			//Cercam entre totes les entrades el medicament amb el mateix id.
			if (this.treuElement(j, 0).equals(id)) {
				//En trobar-lo posam el nom del medicament a la var. medicament
				return this.treuElement(j, 1);

			}
		}
		
		return "";
	}
	
	public int getNumEntrades(){
		return response.split(ENTRADA_SEPARATOR).length;
	}
	
	public int getNumElements(int i) throws IllegalArgumentException{
		
		if (i<getNumEntrades()){
			String entrades;
			entrades = response.split(ENTRADA_SEPARATOR)[i];
			return entrades.split(ELEMENT_SEPARATOR).length;
		}else{
			Log.i("ERROR","getNumElements: Entrada Malament");
			throw new IllegalArgumentException();
		}
	}	
	
	public String treuElement(int entrada, int element)throws IllegalArgumentException{
		
		if (entrada < getNumEntrades()){
			if (element < getNumElements(entrada)){
				//Itera entre les entrades	
				String entrades;
				entrades = response.split(ENTRADA_SEPARATOR)[entrada];
				return entrades.split(ELEMENT_SEPARATOR)[element];
			}else{
				Log.i("ERROR","treuElement: Element Malament");
				throw new IllegalArgumentException();
			}
		} else {
			Log.i("ERROR","treuElement: Entrada Malament");
			throw new IllegalArgumentException();

		}
	}


/*onPreExecute() – Fahrenheit textview control is set with text ‘Calculation…’
doInBackground() – Invoke getFahrenheit()
onProgressUpdate() -Does nothing
onPostExecute() – Set the Fahrenheit value in textview control
*/
    private class Async_getBBDD extends AsyncTask<String, Void, Void> {
	
		private String TAG = "WEBSERVICE";
	    private static final String SOAP_ACTION = "http://WS.ltimwsfarmacia/";
	    private String method;
	    private static final String NAMESPACE = "http://WS.ltimwsfarmacia/";
	    private final String URL = "http://"+MainActivity.IP_SERVER+":8080/WSFarmacia/WSFarmacias";
	    
	    private int inici_str;
	    private int final_str;
	    
	    public Async_getBBDD(String method){
	    	super();
	    	this.method = method;
	    	
	    }
	    @Override
	    protected Void doInBackground(String... params) {
	        String SOAPRequestXML =
	                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
	                "<SOAP-ENV:Header/>" +
	                "<S:Body>" +
	                "<ns2:db xmlns:ns2=\"http://WS.ltimwsfarmacia/\">" +
	                "<text>" + method + "</text>" +
	                "</ns2:db>" +
	                "</S:Body>" +
	                "</S:Envelope>";
	        
	    	HttpPost httppost = new HttpPost(URL);
	    	try {
				StringEntity se = new StringEntity(SOAPRequestXML,HTTP.UTF_8);
				se.setContentType("text/xml");  
				//httppost.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
				httppost.setEntity(se);  
				
				HttpClient httpclient = new DefaultHttpClient();
				//Log.w(TAG,httppost.toString());
				BasicHttpResponse httpResponse = 
				    (BasicHttpResponse) httpclient.execute(httppost);
				Log.w(TAG, httpResponse.getStatusLine().toString());
				String raw_resp = EntityUtils.toString(httpResponse.getEntity());
				inici_str = raw_resp.indexOf("<return>") + 8;
				final_str = raw_resp.indexOf("</return>");
				response = raw_resp.substring(inici_str, final_str);
				acabat = true;
	    	
	    	} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;	
	    	
	    	
	    }
	/*   

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
	        acabat = true;
	        
	        return null;
	        
	        
	    }
	*/
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