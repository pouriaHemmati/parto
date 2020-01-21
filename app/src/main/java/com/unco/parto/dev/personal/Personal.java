package com.unco.parto.dev.personal;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.dev.loging.IloginView;
import com.unco.parto.dev.loging.LoginInteractor;
import com.unco.parto.dev.loging.LoginPeresenter;
import com.unco.parto.model.JLogin;
import com.unco.parto.model.JPersonal;
import com.unco.parto.utilis.SharedPreferences;
import com.unco.parto.widgets.CustomToastMasseg;
import com.willy.ratingbar.ScaleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Personal extends BaseAppCompatActivity implements IPersonalView , IloginView {
    private String id = "-1";
    PersonalPeresenter personalPeresenter;
    @BindView(R.id.img_personal)
    ImageView img_personal;
    @BindView(R.id.txt_type_personal)
    TextView txt_type_personal;
    @BindView(R.id.txt_rate_personal)
    TextView txt_rate_personal;
    @BindView(R.id.txt_name_personal)
    TextView txt_name_personal;
    ScaleRatingBar ratingBar;
    // load user and pass
    SharedPreferences sharedPreferences;
    LoginPeresenter loginPeresenter;
    // loading
    @BindView(R.id.layout_loading)
    RelativeLayout layout_loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .fullscreen()
                .noActionbar()
                .contentView(R.layout.activity_personal)
                .build();
        ButterKnife.bind(this);
        ratingBar = new ScaleRatingBar(BaseActivity.getContext());
        ratingBar = (ScaleRatingBar) findViewById(R.id.simpleRatingBar_Personal);
        sharedPreferences = SharedPreferences.getCashoutsSaveFilter();
        loginPeresenter = new LoginPeresenter(this , new LoginInteractor());
        // get bundle
        Intent in = getIntent();
        Bundle content_search = in.getExtras();
        if (content_search != null) {
            id = content_search.getString("id");
        }
        personalPeresenter = new PersonalPeresenter(this, new PersonalInteractor());
        personalPeresenter.callPersonal(Integer.valueOf(id));
        layout_loading.setVisibility(View.VISIBLE);

    }

    // response webservice
    @Override
    public void successPersonal(JPersonal jPersonal) {
        if (jPersonal !=null){
            double d = jPersonal.getScore();
            float f = (float)d;
            ratingBar.setRating(f/2);

            Picasso.with(BaseActivity.getContext())
                    .load("https://play.hen-dev.ir/" + jPersonal.getPicture())
                    .into(img_personal);

            txt_type_personal.setText(jPersonal.getType());
            txt_name_personal.setText(jPersonal.getName());
            txt_rate_personal.setText(String.valueOf(jPersonal.getScore()));
        }
        layout_loading.setVisibility(View.GONE);

    }

    @Override
    public void errorPersonal(String noResponse) {
        CustomToastMasseg.showToastMessage(BaseActivity.getContext(), noResponse);
        layout_loading.setVisibility(View.GONE);

    }

    @Override
    public void errorTokenPersonal(String token) {
        layout_loading.setVisibility(View.GONE);
        CustomToastMasseg.showToastMessage(BaseActivity.getContext(),"درحال بروز رسانی اطلاعات لطفا صبر کنید");
        sharedPreferences.loadUserName();
        sharedPreferences.loadPassword();
        loginPeresenter.callLogin(sharedPreferences.getUserName() , sharedPreferences.getPassword());
        layout_loading.setVisibility(View.VISIBLE);

    }

    // get new token
    @Override
    public void successLogin(JLogin jLogin) {
        sharedPreferences.setToken(jLogin.getToken());
        sharedPreferences.saveToken();
        CustomToastMasseg.showToastMessage(BaseActivity.getContext(),"به روز رسانی تکمیل شد");
        personalPeresenter.callPersonal(Integer.valueOf(id));
        layout_loading.setVisibility(View.GONE);

    }

    @Override
    public void errorLogin(String noResponse) {
        layout_loading.setVisibility(View.GONE);
        CustomToastMasseg.showToastMessage(BaseActivity.getContext(),noResponse);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
