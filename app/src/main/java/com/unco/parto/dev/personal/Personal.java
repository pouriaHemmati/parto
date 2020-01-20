package com.unco.parto.dev.personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.unco.parto.R;
import com.unco.parto.base.BaseAppCompatActivity;

import butterknife.ButterKnife;

public class Personal extends BaseAppCompatActivity {
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .fullscreen()
                .noActionbar()
                .contentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        // get bundle
        Intent in = getIntent();
        Bundle content_search = in.getExtras();
        if (content_search != null) {
            id = content_search.getString("id");
        }
    }
}
