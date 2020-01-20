package com.unco.parto.dev.personal;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.model.JPersonal;
import com.willy.ratingbar.ScaleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Personal extends BaseAppCompatActivity implements IPersonalView{
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
        // get bundle
        Intent in = getIntent();
        Bundle content_search = in.getExtras();
        if (content_search != null) {
            id = content_search.getString("id");
        }
        personalPeresenter = new PersonalPeresenter(this, new PersonalInteractor());
        personalPeresenter.callPersonal(Integer.valueOf(id));
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

    }

    @Override
    public void errorPersonal(String noResponse) {

    }
}
