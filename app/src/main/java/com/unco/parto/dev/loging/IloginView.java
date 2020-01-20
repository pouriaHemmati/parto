package com.unco.parto.dev.loging;


import com.unco.parto.model.JLogin;

public interface IloginView {
    void successLogin(JLogin jLogin);
    void errorLogin(String noResponse);

}
