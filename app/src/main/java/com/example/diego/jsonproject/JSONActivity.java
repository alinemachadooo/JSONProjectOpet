package com.example.diego.jsonproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONActivity extends Activity {

    private TextView textJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        textJSON = (TextView) findViewById(R.id.textJSON);
    }

    public void conectar(View v){
        new DownloadFromOpenWeather().execute();
    }

    private class DownloadFromOpenWeather extends AsyncTask<Void, Void, String> {
        //Assíncrone = Async - não pode travar a atividade principal. Principalmente para programas web que variam a velocidade.

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Curitiba&mode=json&units=metric&cnt=1&appid=440b84a8027be4fcf90f9b83e4b45aa9");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET"); //pelo link
                urlConnection.connect();

                String result = Util.webToString(urlConnection.getInputStream());

                return result;
            } catch (Exception e) {
                Log.e("Error", "Error ", e); //sistema de Log do android. Criando uma tag chamada ERROR
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Previsao previsao = Util.JSONtoPrevisao(s);
            if(previsao != null) {
                String data = "Cidade: " + previsao.getCidade() + "\n";
                data += "Temperatura: " + previsao.getTemperatura();
                textJSON.setText(data);
            }
        }
    }
}
