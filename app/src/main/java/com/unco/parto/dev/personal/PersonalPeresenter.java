package com.unco.parto.dev.personal;


import com.unco.parto.dev.list_api.IListApiInteractor;
import com.unco.parto.dev.list_api.IListApiPeresenter;
import com.unco.parto.dev.list_api.IListApiView;
import com.unco.parto.model.JListApi;
import com.unco.parto.model.JPersonal;

import java.util.ArrayList;

public class PersonalPeresenter implements IPersonalPeresenter {
    IPersonalView iPersonalView;
    IPersonalInteractor iPersonalInteractor;

    public PersonalPeresenter(IPersonalView iPersonalView, IPersonalInteractor iPersonalInteractor) {
        this.iPersonalView = iPersonalView;
        this.iPersonalInteractor = iPersonalInteractor;
    }


    @Override
    public void callPersonal(int id) {
        iPersonalInteractor.callPersonal(id, new IPersonalInteractor.IPersonalFinishedListener() {
            @Override
            public void successPersonal(JPersonal jPersonal) {
                iPersonalView.successPersonal(jPersonal);
            }

            @Override
            public void errorPersonal(String noResponse) {
                iPersonalView.errorPersonal(noResponse);
            }

        });
    }
}

