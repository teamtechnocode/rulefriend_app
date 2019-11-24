package com.androsoft.rulefriend;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        final Runnable doNextActivity = () -> {
            Intent intent= new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
        };

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                handler.post(doNextActivity);
            }
        }.start();

        Toast.makeText(getApplicationContext(),"LOADING...",Toast.LENGTH_LONG).show();;

    }
}
