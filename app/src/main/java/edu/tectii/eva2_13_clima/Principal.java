package edu.tectii.eva2_13_clima;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Principal extends AppCompatActivity {
    TextView txtVw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtVw =(TextView)findViewById(R.id.txtVw);
        //txtVwClima = (TextView)findViewById(R.id.txtVwClima);
    }
    public void onClick(View v){
        new ClimaApp().execute();
    }
    class ClimaApp extends AsyncTask<Void,Void,String>{
        final String sEnlace = "http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&units=metric&appid=5c7bb48c77a6be341083d038cd2f8ad7";

        @Override
        protected String doInBackground(Void... voids) {
            String sResu = "";
            try {
                URL url = new URL(sEnlace);
                HttpURLConnection httpConClima = (HttpURLConnection)url.openConnection();
                if (httpConClima.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader brDatos = new BufferedReader( new InputStreamReader(httpConClima.getInputStream()));
                    sResu = brDatos.readLine();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsnCiudades =  null;
            if (s !=null){
                try {
                    JSONObject jsDatos = new JSONObject(s);
                    jsnCiudades = jsDatos.getJSONArray("list");
                    for (int i=0;i<jsnCiudades.length(); i++){
                        JSONObject jsCiudad = jsnCiudades.getJSONObject(i);
                        txtVw.append("Ciudad: " + jsCiudad.getString("name") + "\n");
                        //txtVwClima.appened("Ciudad: " + jsCiudad.getString("name") + "\n");
                        JSONObject jsMain = jsCiudad.getJSONObject("main");
                        txtVw.append("Temperatura: " + jsCiudad.getString("temp") + "\n");
                        //txtVwClima.appened("Temperatura: " + jsCiudad.getString("temp") + "\n");
;                       JSONArray jsWeat = jsCiudad.getJSONArray("weather");
                    for (int j =0; j<jsWeat.length(); j++){
                     JSONObject jsClima = jsWeat.getJSONObject(j);
                        txtVw.append("Clima: " + jsCiudad.getString("main") + "\n");
                        txtVw.append("Descripcion: " + jsCiudad.getString("description") + "\n");
                     //txtVwClima.appened("Clima: " + jsCiudad.getString("main") + "\n");
                     //txtVwClima.appened("Descripcion: " + jsCiudad.getString("description") + "\n");
        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
