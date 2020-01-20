package com.unco.parto.dev.list_api;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.base.BaseAppCompatActivity;
import com.unco.parto.dev.list_api.adapter.ListApi_RecyclerAdapter;
import com.unco.parto.dev.list_api.adapter.ModelItemListApi;
import com.unco.parto.dev.list_api.adapter.OnItemClickListener;
import com.unco.parto.model.JListApi;
import com.unco.parto.widgets.CustomToastMasseg;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListApi extends BaseAppCompatActivity implements IListApiView , OnItemClickListener {
    // request webService
    ListApiPeresenter listApiPeresenter;
    // page show
    private int pageShow = 1;
    ArrayList<JListApi> jListApiArrayList;
    private boolean noList = false;
    private boolean FirstSearch = true;
    // recycler
    ModelItemListApi modelItemListApi;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ListApi_RecyclerAdapter listApi_recyclerAdapter;
    // loading
    @BindView(R.id.layout_loading)
    RelativeLayout layout_loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Founder(this)
                .fullscreen()
                .noActionbar()
                .contentView(R.layout.list_api_activity)
                .build();
        ButterKnife.bind(this);

        // webservice
        listApiPeresenter = new ListApiPeresenter(this ,new ListApiInteractor());
        listApiPeresenter.callListApi(pageShow);

        // recycler page
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!noList) {
                        layout_loading.setVisibility(View.VISIBLE);
                        pageShow++;
                        listApiPeresenter.callListApi(pageShow);

                    }
                }
            }
        });
    }

    @Override
    public void successListApi(ArrayList<JListApi> jListApi) {
        jListApiArrayList = jListApi;
        if (jListApiArrayList.size() == 0){
            noList = true;
            CustomToastMasseg.showToastMessage(BaseActivity.getContext(),  "محصولی برای نمایش وجود ندارد");
        } else {
            noList = false;
            recycler();
        }

        layout_loading.setVisibility(View.GONE);

    }

    @Override
    public void errorListApi(String noResponse) {
        layout_loading.setVisibility(View.GONE);
    }

    @Override
    public void errorTokenListApi(String token) {
        layout_loading.setVisibility(View.GONE);
    }

    @Override
    public void onClickList(int id) {

    }

    // listener recycler
    private void recycler() {
        if (!FirstSearch){
            this.jListApiArrayList.addAll(jListApiArrayList);
            listApi_recyclerAdapter.notifyDataSetChanged();
        } else {
            modelItemListApi = new ModelItemListApi(jListApiArrayList);
            recyclerView = findViewById(R.id.recycler_view);
            listApi_recyclerAdapter = new ListApi_RecyclerAdapter(BaseActivity.getContext() , modelItemListApi , this);
            recyclerView.setLayoutManager(new LinearLayoutManager(BaseActivity.getContext() , LinearLayoutManager.VERTICAL , false));
            recyclerView.setAdapter(listApi_recyclerAdapter);
            FirstSearch = false;
        }




    }
}
