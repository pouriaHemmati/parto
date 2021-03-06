package com.unco.parto.dev.list_api;


import com.unco.parto.model.JListApi;

import java.util.ArrayList;

public class ListApiPeresenter implements IListApiPeresenter {
    IListApiView listApiView;
    IListApiInteractor iListApiInteractor;

    public ListApiPeresenter(IListApiView listApiView, IListApiInteractor iListApiInteractor) {
        this.listApiView = listApiView;
        this.iListApiInteractor = iListApiInteractor;
    }


    @Override
    public void callListApi(int page) {
        iListApiInteractor.callListApi(page, new IListApiInteractor.IListApiFinishedListener() {
            @Override
            public void successListApi(ArrayList<JListApi> jListApi) {
                listApiView.successListApi(jListApi);
            }

            @Override
            public void errorListApi(String noResponse) {
                listApiView.errorListApi(noResponse);
            }

            @Override
            public void errorTokenListApi(String token) {
                listApiView.errorTokenListApi(token);
            }
        });
    }
}

