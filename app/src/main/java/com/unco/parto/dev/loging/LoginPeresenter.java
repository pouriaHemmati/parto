package com.unco.parto.dev.loging;


import com.unco.parto.model.JLogin;

public class LoginPeresenter implements ILoginPeresenter {
    IloginView iloginView;
    ILoginInteractor iLoginInteractor;

    public LoginPeresenter(IloginView iloginView, ILoginInteractor iLoginInteractor) {
        this.iloginView = iloginView;
        this.iLoginInteractor = iLoginInteractor;
    }


    @Override
    public void callLogin(String username, String password) {
        iLoginInteractor.callLogin(username, password, new ILoginInteractor.ILoginFinishedListener() {
            @Override
            public void successLogin(JLogin jLogin) {
                iloginView.successLogin(jLogin);
            }

            @Override
            public void errorLogin(String noResponse) {
                iloginView.errorLogin(noResponse);
            }
        });
    }
}

