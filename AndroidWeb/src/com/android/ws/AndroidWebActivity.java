package com.android.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AndroidWebActivity extends Activity implements OnClickListener {
	Button calculate;
	private static final String TAG = "MyActivity";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        calculate = (Button)findViewById(R.id.button1);
        calculate.setOnClickListener((OnClickListener) this);
    }
    public void bmi(){
        HttpClient httpclient = new DefaultHttpClient();
          
        HttpPost httppost = new HttpPost("192.168.1.5/bclipse/php/bmi.php");
    	try{
    	EditText weight = (EditText) findViewById(R.id.editText1);
    	String myWeight = weight.getText().toString();
    	
    	EditText height = (EditText) findViewById(R.id.EditText01);
    	String myHeight = height.getText().toString();
    	
    	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("weight", myWeight));
         nameValuePairs.add(new BasicNameValuePair("height", myHeight));
         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         HttpResponse response = httpclient.execute(httppost);
         
         String bmiResult = inputStreamToString(response.getEntity().getContent()).toString();
         TextView result = (TextView) findViewById(R.id.textView4);
         result.setText("Your BMI : "+bmiResult);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		Log.v(TAG, "Fejl...");
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
    
    public void onClick(View view) {
        if(view == calculate){
        	bmi();
        }
      }
}