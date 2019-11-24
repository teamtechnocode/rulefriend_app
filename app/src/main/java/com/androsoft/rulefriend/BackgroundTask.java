package com.androsoft.rulefriend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static android.content.ContentValues.TAG;

public class BackgroundTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context ctx;

    BackgroundTask(Context ctx) {
     this.ctx=ctx.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null) {
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            Response r=new Response();

            if (s.equals("<!-- Database connection -->WELCOME")) {
                //ctx.startActivity(new Intent(ctx, DashboardActivity.class));
                r.setResponse("YOU ARE SUCCESSFULLY LOGGED IN");
                Intent intent=new Intent(ctx,DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                Log.e(TAG, "onPostExecute: "+s);
            }

            if (s.equals("<!-- Database connection -->WELCOME")) {
                r.setResponse("YOU ARE REGISTERED SUCCESSFULLY");
                Intent intent=new Intent(ctx,DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                Log.e(TAG, "onPostExecute: "+s);
            }

            //if (s.equals("USER ALREADY EXISTS...TRY WITH SOME ANOTHER E-MAIL ID"))
                //Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        String regUrl="https://rulefriend.tk/api/android_signup.php";
        String LoginUrl="https://rulefriend.tk/api/android_signin.php";
        String method=strings[0];
        if(method.equals("register"))             //method, mailId, name, Gender, Address, pass, confPass
        {
            String email=strings[1];
            String name=strings[2];
            String mobileNo=strings[3];
            String password=strings[4];
           // Log.e(TAG, "doInBackground: "+name+" "+password);
            try {
                URL url=new URL(regUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                String data= URLEncoder.encode("name", "UTF-8")+"="+ URLEncoder.encode(name,"UTF-8")+"&"
                        + URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"
                        + URLEncoder.encode("mobile","UTF-8")+"="+ URLEncoder.encode(mobileNo,"UTF-8")+"&"
                        + URLEncoder.encode("password","UTF-8")+"=" + URLEncoder.encode(password,"UTF-8");
                Log.e("POST_Data", "doInBackground: "+data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                StringBuilder response= new StringBuilder();
                String line;
                while ((line=bufferedReader.readLine())!=null)
                {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.e(TAG, "doInBackground: Register "+response);
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder response= new StringBuilder();

        if(method.equals("login"))
        {
            String email=strings[1];
            String password=strings[2];
            try {
                URL url=new URL(LoginUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                String data= URLEncoder.encode("email", "UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"
                        + URLEncoder.encode("password", "UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                //Log.e(TAG, "doInBackground: "+data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

                String line;
                while ((line=bufferedReader.readLine())!=null)
                {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.e(TAG, "doInBackground: LogIn "+response);
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response.toString();
    }
}