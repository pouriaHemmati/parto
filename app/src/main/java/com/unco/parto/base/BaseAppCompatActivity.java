package com.unco.parto.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseAppCompatActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity application = (BaseActivity) getApplication();
//        mTracker = application.getDefaultTracker();


    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseActivity.setCurrentActivity(this);
    }




    public void startActivity(Class<?> otherActivityClass) {
        Intent intent = new Intent(this, otherActivityClass);
        startActivity(intent);
    }



    public static class Founder {
        private final Activity activity;
        private int[] features;
        private boolean noTitlebar;
        private boolean noActionbar;
        private boolean fullscreen;
        private boolean rotate;
        private int layoutId;

        public Founder(Activity activity) {
            this.activity = activity;
        }

        public Founder requestFeatures(int... features) {
            this.features = features;
            return this;
        }

        public Founder noTitlebar() {
            this.noTitlebar = true;
            return this;
        }

        public Founder noActionbar() {
            this.noActionbar = true;
            return this;
        }

        public Founder fullscreen() {
            this.fullscreen = true;
            return this;
        }


        public Founder contentView(@LayoutRes int layoutResID) {
            this.layoutId = layoutResID;
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        public Founder build() {
            if (this.features != null) {
                for (int feature : this.features) {
                    activity.getWindow().requestFeature(feature);
                }
            }

            if (noTitlebar) {
                activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }

            if (fullscreen) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            if (noActionbar) {
                activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

                {
                    ActionBar actionBar = activity.getActionBar();
                    if (actionBar != null) {
                        actionBar.hide();
                    }
                }
            }
            activity.setContentView(layoutId);
            return this;
        }


    }


    public void startActivityBundle(Class<?> otherActivityClass , String keyValue ,  String bundle){
        Intent i = new Intent(BaseActivity.getCurrentActivity(), otherActivityClass);
        i.putExtra(keyValue, bundle);
        startActivity(i);
    }



}
