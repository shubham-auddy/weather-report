package com.example.weatherreport;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    int flag=0;
    EditText cityName;
    String temperatureinfo,humidityinfo,pressureinfo,basicinfo,windinfo;
    TextView temp,temp2,humidity,humidity2,basicDescription,wind,wind2;
    public class DownloadTask extends AsyncTask<String,Void,String> {
        String result = "";



        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(httpURLConnection.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    char ch = (char) data;
                    result += ch;
                    data = reader.read();

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }


        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            temp.animate().alpha(0).setDuration(0l);
            wind.animate().alpha(0).setDuration(0l);
            humidity.animate().alpha(0).setDuration(0l);
            temp2.animate().alpha(0).setDuration(0l);
            wind2.animate().alpha(0).setDuration(0l);
            humidity2.animate().alpha(0).setDuration(0l);
            basicDescription.animate().alpha(0).setDuration(0l);
            if(flag==1){
                basicDescription.animate().translationY(-100f);
                flag=0;
            }

            try{
                JSONObject jsonObject=new JSONObject(result);
                String main=jsonObject.getString("main");
                JSONObject jsonPart=new JSONObject(main);
                temperatureinfo=jsonPart.getString("temp");
                humidityinfo=jsonPart.getString("humidity");
                pressureinfo=jsonPart.getString("pressure");




                String basic=jsonObject.getString("weather");
                JSONArray arr=new JSONArray(basic);
                JSONObject jsonPart2=arr.getJSONObject(0);
                basicinfo=jsonPart2.getString("description");




                String winds=jsonObject.getString("wind");
                JSONObject jsonPart3=new JSONObject(winds);
                windinfo=jsonPart3.getString("speed");

                temp2.setText(temperatureinfo+"°C");
                humidity2.setText(humidityinfo+"%");
                basicDescription.setText(basicinfo);
                wind2.setText(windinfo+"km/h");

                temp.animate().alpha(1).setDuration(500);
                humidity.animate().alpha(1).setDuration(500);
                temp2.animate().alpha(1).setDuration(500);
                wind2.animate().alpha(1).setDuration(500);
                humidity2.animate().alpha(1).setDuration(500);
                basicDescription.animate().alpha(1).setDuration(500);
                wind.animate().alpha(1).setDuration(500);






            }
            catch (Exception e){
                e.printStackTrace();
                basicDescription.setText("Please Enter A Valid City Name or Check Your Internet Connection");
                basicDescription.animate().alpha(1).translationY(300f).setDuration(500);
                flag=1;
            }



        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=(EditText)findViewById(R.id.cityName);
        temp=(TextView)findViewById(R.id.temp);
        temp2=(TextView)findViewById(R.id.temp2);
        wind=(TextView)findViewById(R.id.wind);
        wind2=(TextView)findViewById(R.id.wind2);
        humidity=(TextView)findViewById(R.id.humidity);
        humidity2=(TextView)findViewById(R.id.humidity2);
        basicDescription=(TextView)findViewById(R.id.basicDescription);



    }

    public void findWeather(View view){
        temp.animate().alpha(0).setDuration(0l);
        wind.animate().alpha(0).setDuration(0l);
        humidity.animate().alpha(0).setDuration(0l);
        temp2.animate().alpha(0).setDuration(0l);
        wind2.animate().alpha(0).setDuration(0l);
        humidity2.animate().alpha(0).setDuration(0l);
        basicDescription.animate().alpha(0).setDuration(0l);
        if(flag==1){
            basicDescription.animate().translationY(-100f);
            flag=0;
        }

        Log.i("city Name",cityName.getText().toString());
        DownloadTask task=new DownloadTask();
        String result=null;
        try{
            result=task.execute("http://api.openweathermap.org/data/2.5/weather?q="+cityName.getText().toString()+",india&units=metric&APPID=8083d838939c8cd37b58e968e52c6839").get();

        }catch (Exception e){
            e.printStackTrace();
        }



    }

}