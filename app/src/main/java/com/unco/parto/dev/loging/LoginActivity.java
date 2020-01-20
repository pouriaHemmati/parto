package com.unco.parto.dev.loging;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.dev.list_api.ListApi;
import com.unco.parto.model.JLogin;
import com.unco.parto.utilis.SharedPreferences;
import com.unco.parto.widgets.CustomToastMasseg;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseAppCompatActivity implements IloginView {
    @BindView(R.id.cardView_login)
    CardView cardView_login;
    @BindView(R.id.cardView_details_login)
    CardView cardView_details_login;
    @BindView(R.id.parent_lay_login)
    ConstraintLayout parent_lay_login;
    @BindView(R.id.loading_login)
    LottieAnimationView loading_login;
    @BindView(R.id.btn_enter_login)
    Button btn_enter_login;
    private boolean exit = false;

    // username
    @BindView(R.id.edt_username_login)
    EditText edt_username_login;
    @BindView(R.id.txt_username_login)
    TextView txt_username_login;

    // password
    @BindView(R.id.edt_password_login)
    EditText edt_password_login;
    @BindView(R.id.txt_password_login)
    TextView txt_password_login;

    // show details
    @BindView(R.id.txt_username_show_login)
    TextView txt_username_show_login;
    @BindView(R.id.txt_name_family_show_login)
    TextView txt_name_family_show_login;


    //SharedPreferences
    SharedPreferences sharedPreferences;

    // request webService
    LoginPeresenter loginPeresenter;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .noActionbar()
                .fullscreen()
                .contentView(R.layout.activity_login)
                .build();
        ButterKnife.bind(this);
        // set radius cardView
        cardView_login.setBackgroundResource(R.drawable.shape_card_view);
        cardView_details_login.setBackgroundResource(R.drawable.shape_card_view);
        sharedPreferences = SharedPreferences.getCashoutsSaveFilter();
        loginPeresenter = new LoginPeresenter(this, new LoginInteractor());

    }

    // onClick

    @OnClick(R.id.btn_enter_login)
    public void btn_enter_login() {
        username = String.valueOf(edt_username_login.getText());
        sharedPreferences.setUserName(username);
        sharedPreferences.saveUserName();
        password = String.valueOf(edt_password_login.getText());
        sharedPreferences.setPassword(password);
        sharedPreferences.savePassword();
        if (!username.equals("")) {
            if (!password.equals("")) {
                loading_login.setVisibility(View.VISIBLE);
                btn_enter_login.setVisibility(View.GONE);
                loginPeresenter.callLogin(username, password);
            } else {
                animation(txt_password_login);
            }

        } else {
            animation(txt_username_login);
        }
    }

    @OnClick(R.id.btn_lets_go_login)
    public void btn_lets_go_login() {
        startActivity(ListApi.class);
        finish();
    }

    // response webService
    @SuppressLint("SetTextI18n")
    @Override
    public void successLogin(JLogin jLogin) {
        txt_username_show_login.setText(jLogin.getUsername());
        txt_name_family_show_login.setText(jLogin.getFirstName() + " " + jLogin.getLastName());
        sharedPreferences.setToken(jLogin.getToken());
        sharedPreferences.saveToken();
        visibitilyVisible();
        visibitilyGone();

    }

    @Override
    public void errorLogin(String noResponse) {
        loading_login.setVisibility(View.GONE);
        btn_enter_login.setVisibility(View.VISIBLE);
        CustomToastMasseg.showToastMessage(BaseActivity.getContext(), noResponse);
    }


    // animations
    private void animation(TextView textView) {
        Animation animation = new TranslateAnimation(0, 35, 0, 0);
        animation.setDuration(60);
        animation.setFillAfter(true);
        animation.setRepeatCount(5);
        animation.setRepeatMode(Animation.REVERSE);
        textView.startAnimation(animation);
    }

    private void visibitilyGone() {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, cardView_login.getHeight() + 100);
        animate.setDuration(500);
        animate.setFillAfter(true);
        cardView_login.startAnimation(animate);
        cardView_login.setVisibility(View.GONE);
    }

    private void visibitilyVisible() {
        TranslateAnimation animate = new TranslateAnimation(0, 0, cardView_login.getHeight(), 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        cardView_details_login.startAnimation(animate);
        cardView_details_login.setVisibility(View.VISIBLE);
    }

    // onBack
    @Override
    public void onBackPressed() {
        if (exit)
            LoginActivity.this.finish();
        else {
            CustomToastMasseg.showToastMessage(BaseActivity.getContext(), "برای خروج یک بار دیگر کلیک کنید");
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

}
