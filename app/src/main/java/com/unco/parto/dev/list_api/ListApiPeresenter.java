package com.unco.parto.dev.list_api;


import com.unco.parto.model.JListApi;

public class ListApiPeresenter implements IListApiPeresenter {
    IListApiView listApiView;
    IListApiInteractor iListApiInteractor;

    public ListApiPeresenter(IListApiView listApiView, IListApiInteractor iListApiInteractor) {
        this.listApiView = listApiView;
        this.iListApiInteractor = iListApiInteractor;
    }


    @Override
    public void callListApi(String page) {
        iListApiInteractor.callListApi(page, new IListApiInteractor.IListApiFinishedListener() {
            @Override
            public void successListApi(JListApi jListApi) {
                listApiView.successListApi(jListApi);
            }

            @Override
            public void errorListApi(String noResponse) {
                listApiView.errorListApi(noResponse);
            }
        });
    }
}

