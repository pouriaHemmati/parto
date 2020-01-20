package com.unco.parto.dev.list_api;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.unco.parto.R;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.model.JListApi;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class ListApi extends BaseAppCompatActivity implements IListApiView{
    // request webService
    ListApiPeresenter listApiPeresenter;
    // page show
    private int pageShow = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .fullscreen()
                .noActionbar()
                .contentView(R.layout.list_api_activity)
                .build();
        ButterKnife.bind(this);

        // webservice
        listApiPeresenter = new ListApiPeresenter(this ,new ListApiInteractor());
        listApiPeresenter.callListApi(pageShow);
    }

    @Override
    public void successListApi(ArrayList<JListApi> jListApi) {

    }

    @Override
    public void errorListApi(String noResponse) {

    }

    @Override
    public void errorTokenListApi(String token) {

    }
}
