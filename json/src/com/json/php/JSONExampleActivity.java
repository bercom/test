package com.json.php;

/*
 * Tager to felter fra edit tekst 1 og to, og sender med i en url med gettektst
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

 /** testing getparms til php - og php - json - client
 * @author poul
 *
 */
public class JSONExampleActivity extends Activity {
    private static final String webadr = 
            "http://192.168.1.5/bclipse/php/index.php?par1=%s&par2=%s";
    /** Called when the activity is first created. */

    private TextView parm1;
    private TextView parm2;
    private TextView parm3;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setParm1((TextView) findViewById(R.id.par1));
        setParm2((TextView) findViewById(R.id.par2));
        setParm3((TextView) findViewById(R.id.par3));
        final Button button = (Button) findViewById(R.id.doit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                download();
            }

            private void download() {
                String parStr = String.format(webadr, getParm1().getText()
                        .toString(), getParm2().getText().toString());
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(parStr);

                try {
                    HttpResponse response = httpclient.execute(httpget);
                    String jsonResult = inputStreamToString(
                            response.getEntity().getContent()).toString();
                    // logcat.i

                    JSONObject object = new JSONObject(jsonResult);
                    procesInput(object);
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

    private StringBuilder inputStreamToString(final InputStream is) {
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

    private void procesInput(final JSONObject object) throws JSONException {
        String par1 = object.getString("par1");
        String par2 = object.getString("par2");
        getParm1().setText(par2.toString());
        getParm2().setText(par1.toString());
        int temp = Integer.valueOf(getParm1().getText().toString())
                + Integer.valueOf(getParm2().getText().toString());
        getParm3().setText(String.valueOf(temp));
    }

    TextView getParm1() {
        return parm1;
    }

    void setParm1(TextView parm1) {
        this.parm1 = parm1;
    }

    TextView getParm2() {
        return parm2;
    }

    void setParm2(TextView parm2) {
        this.parm2 = parm2;
    }

    TextView getParm3() {
        return parm3;
    }

    void setParm3(TextView parm3) {
        this.parm3 = parm3;
    }

}
