package com.unco.parto.dev.list_api;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.unco.parto.R;
import com.unco.parto.base.BaseAppCompatActivity;

import butterknife.ButterKnife;

public class ListApi extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .fullscreen()
                .noActionbar()
                .contentView(R.layout.list_api_activity)
                .build();
        ButterKnife.bind(this);

    }
}
