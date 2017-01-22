package com.example.lavanya.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherTask task=new WeatherTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=55043ca149550f44459ac5108bf6f7ac");
    }

    public class WeatherTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            String result="";
            try {
                url=new URL(strings[0]);
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
                int data= inputStreamReader.read();
                while(data != -1){
                    char current= (char) data;
                    result=result+ current;
                    data=inputStreamReader.read();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject= new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("weather");
                for(int i=0;i< jsonArray.length();i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.i("main",object.getString("main"));
                    Log.i("description",object.getString("description"));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
