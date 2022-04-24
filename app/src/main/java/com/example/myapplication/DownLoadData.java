package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import java.net.HttpURLConnection;

public class DownLoadData extends AsyncTask<String, Integer, String>{
        static Double TheRealPrice = -0.2 ;
        static Boolean EveryThingIsFine =false;
        Context context;

        public DownLoadData(Context context) {

                this.context= context;
        }

        @Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String line = "";
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
        URL myURL = new URL(params[0]);
        URLConnection ucon = myURL.openConnection();
        InputStream in = ucon.getInputStream();
        byte[] buffer = new byte[4096];
        in.read(buffer);
        line = new String(buffer);
        } catch (Exception e) {
        line = e.getMessage();
        }
        return line;
        }

@Override
protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        }

@Override
protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        String EditResult = result;
        String[] parts = EditResult.split("volume");
        EditResult = parts[0];
        EditResult = EditResult.replaceAll("[^\\d.]", "");
        if (!EditResult.isEmpty()) {
        TheRealPrice = Double.valueOf(EditResult);
        EveryThingIsFine = true;
        System.out.println("עכשיו אתה יכול ללחוץ על הוספה/עדכן");
        Toast.makeText(context,"עכשיו אתה יכול ללחוץ על הוספה/עדכן", Toast.LENGTH_LONG).show();
        } else {
        //Toast.makeText(LookActivity.context, "שם מנייה לא נכון", Toast.LENGTH_LONG).show();
        }
        }

        }