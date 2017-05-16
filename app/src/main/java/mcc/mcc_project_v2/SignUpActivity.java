package mcc.mcc_project_v2;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import static java.net.Proxy.Type.HTTP;

public class SignUpActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private LoginActivity.UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
////                    attemptLogin();
//
//                    return true;
//                }
//                return false;
//            }
//        });

        final EditText username_new = (EditText) findViewById(R.id.uname);
        final EditText password_new = (EditText) findViewById(R.id.password);
        final EditText email_new = (EditText) findViewById(R.id.email);
        final EditText name_new = (EditText) findViewById(R.id.name);



        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Button SignUp_button = (Button) findViewById(R.id.SignUpUser);
        SignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    URL url = new URL("https://mccproj543.herokuapp.com/signup");
                    String uname=String.valueOf(username_new.getText());
                    String pass=String.valueOf(password_new.getText());
                    String email= String.valueOf(email_new.getText());
                    String name=String.valueOf(name_new.getText());

                    String param="username="+ URLEncoder.encode(uname,"UTF-8")+
                            "&password="+URLEncoder.encode(pass,"UTF-8")+
                            "&email="+URLEncoder.encode(email,"UTF-8")+
                            "&name="+URLEncoder.encode(name,"UTF-8");
                    httpPoster hp=new httpPoster(param);
                    hp.execute(url);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        });


    }
    private class httpPoster extends AsyncTask<URL,Void,String>{
        private final String param;

        httpPoster(String param)
        {
            this.param=param;
        }
        @Override
        protected String doInBackground(URL... params) {
            try {
                return makeRequest(params[0],param);
            }catch(IOException e)
            {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String s) {
//            new AlertDialog.Builder(SignUpActivity.this)
//                    .setTitle("ms2")
//                    .setMessage(s)
//                    .show();
            super.onPostExecute(s);

            Intent myIntent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(myIntent);


        }
    }

//    private String makeRequest(URL url, String params) throws IOException{
//        HttpClient client = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//        HttpResponse response;
//        JSONObject json = new JSONObject();
//
//        String responseStr = null;
//        try {
//            HttpPost post = new HttpPost(url);
//            json.put("Userid", "asdd");
//            json.put("FirstName", "sdsds");
//            json.put("LastName", "sdsdsd");
//            json.put("Contact", "4545445");
//            json.put("Emailid", "sssss");
//            json.put("Interest", "cvcvcvcv");
//            json.put("path1", "vfvfvvf/vf/vf");
//            StringEntity se = new StringEntity(json.toString());
//            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//            post.setEntity(se);
//            response = client.execute(post);
//            responseStr = EntityUtils.toString(response.getEntity());
//
//                    /*Checking response */
//
//            Log.d("yexy1", "no response" + responseStr);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("text", "respinse=");
//        }
//
//        Looper.loop(); //Loop in the message queue
//    }

    private String makeRequest(URL url, String params) throws IOException{

        HttpURLConnection client = null;

            client = (HttpURLConnection) url.openConnection();
//            client.connect();
            Log.d("params",params);
            client.setRequestMethod("POST");
//            client.setFixedLengthStreamingMode(params.getBytes().length);
//            client.setRequestProperty("Content-Type", "text/plain");
            client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            client.setRequestProperty("charset","utf-8");
            client.setRequestProperty("Content-Length",""+Integer.toString(params.getBytes().length));
            client.setDoOutput(true);
            String response= "";

            DataOutputStream oStream = new DataOutputStream(client.getOutputStream());
            oStream.writeBytes(params);
            oStream.flush();
            oStream.close();
                int responseCode= client.getResponseCode();
            String output="";
//            output+=System.getProperty("line.seperator")+"Request Parameters "+params;
//            output+=System.getProperty("line.seperator")+"Response Code"+ responseCode;

            BufferedReader inStream=new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder respop=new StringBuilder();
            while((response=inStream.readLine())!=null){
                respop.append(response);
            }
            inStream.close();
            output+=System.getProperty("line.seperator")+respop.toString();
            Log.d("msg2", "Response Code2: " + output );

            return output;
    }
}
