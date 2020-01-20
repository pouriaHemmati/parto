package com.unco.parto.dev.splash;

import android.os.Bundle;
import android.os.Handler;

import com.unco.parto.R;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.dev.loging.LoginActivity;


public class SplashActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BaseAppCompatActivity.Founder(this)
                .noActionbar()
                .fullscreen()
                .contentView(R.layout.activity_splash)
                .build();

        // handler for lottie animation
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(LoginActivity.class);
                SplashActivity.this.finish();
            }
        }, 6000);
    }
}
