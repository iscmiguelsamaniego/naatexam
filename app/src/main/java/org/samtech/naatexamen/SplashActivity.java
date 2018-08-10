package org.samtech.naatexamen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.samtech.naatexamen.activities.LoginActivity;

import static org.samtech.naatexamen.utils.Keys.SPLASH_DURATION;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handlerActivity();
    }

    public void handlerActivity() {
        try {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                ;
            }, SPLASH_DURATION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
