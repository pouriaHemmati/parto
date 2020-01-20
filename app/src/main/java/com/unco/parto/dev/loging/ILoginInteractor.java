package com.unco.parto.dev.loging;


import com.unco.parto.model.JLogin;

public interface ILoginInteractor {
    void callLogin(String username, String password, ILoginFinishedListener iLoginFinishedListener);
    interface ILoginFinishedListener {
        void successLogin(JLogin jLogin);
        void errorLogin(String noResponse);
    }
}
