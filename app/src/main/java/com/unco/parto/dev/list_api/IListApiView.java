package com.unco.parto.dev.list_api;


import com.unco.parto.model.JListApi;

public interface IListApiView {
    void successListApi(JListApi jListApi);
    void errorListApi(String noResponse);

}
