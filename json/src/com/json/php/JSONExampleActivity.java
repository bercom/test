package com.json.php;

/*
 * programmet tager to felter fra edit tekst 1 og to, og sender med i en url med gettektst
 * dette kan verificeres på på apache loggen
 * de modtagne parametre puttes på et array, og json encodes
 * returstrengen fra get er altså en json strent
 * denne dekodes, til par 1 og 2
 * disse sendes tilbage til editfelterne - i ombyttet rækkefølge,
 * Så er det let at verificere virkningen!
 * 
 * 
 * */

import android.app.Activity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONExampleActivity extends Activity {
    /** Called when the activity is first created. */

    TextView parm1;
    TextView parm2;
    TextView parm3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        parm1 = (TextView) findViewById(R.id.par1);
        parm2 = (TextView) findViewById(R.id.par2);
        parm3 = (TextView) findViewById(R.id.par3);
        final Button button = (Button) findViewById(R.id.doit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                download();
            }

            private void download() {
                String parStr = String
                        .format("http://192.168.1.5/bclipse/php/index.php?par1=%s&par2=%s",
                                parm1.getText().toString(), parm2.getText()
                                        .toString());
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(parStr);

                try {
                    HttpResponse response = httpclient.execute(httpget);
                    String jsonResult = inputStreamToString(
                            response.getEntity().getContent()).toString();
                    // logcat.i

                    JSONObject object = new JSONObject(jsonResult);
                    String par1 = object.getString("par1");
                    String par2 = object.getString("par2");
                    parm1.setText(par2.toString());
                    parm2.setText(par1.toString());
                    int temp = Integer.valueOf(parm1.getText().toString())
                            + Integer.valueOf(parm2.getText().toString());
                    parm3.setText(String.valueOf(temp));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
