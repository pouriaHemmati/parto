package com.unco.parto.dev.personal;


import com.unco.parto.model.JListApi;
import com.unco.parto.model.JPersonal;

import java.util.ArrayList;

public interface IPersonalInteractor {
    void callPersonal(int id, IPersonalFinishedListener iPersonalFinishedListener);
    interface IPersonalFinishedListener {
        void successPersonal(JPersonal jPersonal);
        void errorPersonal(String noResponse);
        void errorTokenPersonal(String token);

    }
}
