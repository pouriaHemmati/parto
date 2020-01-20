package com.unco.parto.dev.list_api;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.dev.list_api.adapter.ListApi_RecyclerAdapter;
import com.unco.parto.dev.list_api.adapter.ModelItemListApi;
import com.unco.parto.dev.list_api.adapter.OnItemClickListener;
import com.unco.parto.model.JListApi;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class ListApi extends BaseAppCompatActivity implements IListApiView , OnItemClickListener {
    // request webService
    ListApiPeresenter listApiPeresenter;
    // page show
    private int pageShow = 1;
    // recycler
    ModelItemListApi modelItemListApi;
    RecyclerView recyclerView;
    ListApi_RecyclerAdapter listApi_recyclerAdapter;
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
        modelItemListApi = new ModelItemListApi(jListApi);
        recyclerView = findViewById(R.id.recycler_view);
        listApi_recyclerAdapter = new ListApi_RecyclerAdapter(BaseActivity.getContext() , modelItemListApi , this);
        recyclerView.setLayoutManager(new LinearLayoutManager(BaseActivity.getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(listApi_recyclerAdapter);
    }

    @Override
    public void errorListApi(String noResponse) {

    }

    @Override
    public void errorTokenListApi(String token) {

    }

    @Override
    public void onClickList(int id) {

    }
}
