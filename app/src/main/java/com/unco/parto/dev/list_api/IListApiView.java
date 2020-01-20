package com.unco.parto.dev.list_api;


import com.unco.parto.model.JListApi;

import java.util.ArrayList;

public interface IListApiView {
    void successListApi(ArrayList<JListApi> jListApi);
    void errorListApi(String noResponse);
    void errorTokenListApi(String token);

}
