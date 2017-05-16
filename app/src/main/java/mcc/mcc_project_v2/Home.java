package mcc.mcc_project_v2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Home extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_home, container, false);

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        final Button mButton;
        final EditText mEdit;

        mButton = (Button)view.findViewById(R.id.button);
        mEdit   = (EditText)view.findViewById(R.id.editText);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Log.v("EditText", mEdit.getText().toString());
                        try {
                            URL url = new URL("https://mccproj543.herokuapp.com/teams/newTeam");
                            String team_name = String.valueOf(mEdit.getText());
                            String param = "teamName="+ URLEncoder.encode(team_name,"UTF-8");

                            httpPoster hp=new httpPoster(param);
                            hp.execute(url);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    }
                });

        return view;
    }

    private class httpPoster extends AsyncTask<URL,Void,String> {
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

//            Intent myIntent = new Intent(SignUpActivity.this, MainActivity.class);
//            startActivity(myIntent);


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
            respop.append(response);
        }
        inStream.close();
        output+=System.getProperty("line.seperator")+respop.toString();
        Log.d("msg2", "Response Code2: " + output );

        return output;
    }

}
