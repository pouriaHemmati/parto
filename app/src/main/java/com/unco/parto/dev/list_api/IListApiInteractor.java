package com.unco.parto.dev.list_api;


import com.unco.parto.model.JListApi;


public interface IListApiInteractor {
    void callListApi(String page, IListApiFinishedListener iListApiFinishedListener);
    interface IListApiFinishedListener {
        void successListApi(JListApi jListApi);
        void errorListApi(String noResponse);
    }
}
