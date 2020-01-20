package com.unco.parto.dev.list_api.adapter;

import com.unco.parto.model.JListApi;

import java.util.ArrayList;


public class ModelItemListApi {


    ArrayList<JListApi> jListApis;


    public ModelItemListApi( ) {


    }
    public ModelItemListApi(ArrayList jListApis ) {
        this.jListApis = jListApis;

    }


    public void setList(ArrayList<JListApi> jListApiArrayList) {
        this.jListApis = jListApiArrayList;
    }

    public ArrayList<JListApi> getList() {
        return jListApis;
    }
}


