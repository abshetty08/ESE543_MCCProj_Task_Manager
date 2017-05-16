package mcc.mcc_project_v2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static java.security.AccessController.getContext;

/**
 * A login screen that offers login via email/password.
 */
//public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText username_new = (EditText) findViewById(R.id.username);
        final EditText password_new = (EditText) findViewById(R.id.password);
//        final EditText email_new = (EditText) findViewById(R.id.email);
//        final EditText name_new = (EditText) findViewById(R.id.name);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                attemptLogin();
                try{
                    URL url = new URL("https://mccproj543.herokuapp.com/login");
                    String uname=String.valueOf(username_new.getText());
                    String pass=String.valueOf(password_new.getText());
//                    String email= String.valueOf(email_new.getText());
//                    String name=String.valueOf(name_new.getText());

                    String param="username="+ URLEncoder.encode(uname,"UTF-8")+
                            "&password="+URLEncoder.encode(pass,"UTF-8");
//                            "&email="+URLEncoder.encode(email,"UTF-8")+
//                            "&name="+URLEncoder.encode(name,"UTF-8");

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


        Button SignUp_button = (Button) findViewById(R.id.SignUpRedirect);
        SignUp_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(myIntent);

            }
        });

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
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


            super.onPostExecute(s);

            Log.d("JSON string ", s);

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int status = 0;
            try {
                status = jsonObj.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("status ", String.valueOf(status));

            JSONObject jsonObj2 = null;
            try {
                jsonObj2 = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject data = null;
            try {
                data = jsonObj2.getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String name = null;
            try {
                name = data.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("name ", name);

            JSONObject data2 = null;
            try {
                data2 = jsonObj2.getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String username = null;
            try {
                username = data.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("username ", username);

            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//            myIntent.putExtra("username", username);
//            myIntent.putExtra("name", name);
            LoginActivity.this.startActivity(myIntent);

        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
        }
    }

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
//            Log.d("responses",response.toString());
            respop.append(response);
        }

        inStream.close();
        output+=respop.toString();
//        Log.d("msg2", "Response Code2: " + output );
//        Log.d("responses: ",response);
        return output;
    }
}

