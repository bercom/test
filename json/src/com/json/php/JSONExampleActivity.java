package com.json.php;

import android.app.Activity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONExampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.5/bclipse/php/index.php");
        TextView textView = (TextView)findViewById(R.id.textView1);
		try {
			
			HttpResponse response = httpclient.execute(httppost);
			String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
			JSONObject object = new JSONObject(jsonResult);

            String name = object.getString("name");
            String comment = object.getString("comment");
	    	String verion = object.getString("version");
	    	textView.setText(name + " - " + comment + " - " + verion);
	    	
		} 
		catch (JSONException e) {
			e.printStackTrace();
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}


       }
    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         
        try {
         while ((rLine = rd.readLine()) != null) {
          answer.append(rLine);
           }
        }
         
        catch (IOException e) {
            e.printStackTrace();
         }
        return answer;
       }
}
    	
        
        
