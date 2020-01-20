package com.unco.parto.dev.list_api.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unco.parto.R;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.model.JListApi;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;


public class ListApi_RecyclerAdapter extends RecyclerView.Adapter<ListApi_RecyclerAdapter.ViewHolder> {
    private ModelItemListApi requestItems;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public ListApi_RecyclerAdapter(Context context, ModelItemListApi requestItems,
                                   OnItemClickListener onItemClickListener) {
        this.context = context;
        this.requestItems = requestItems;
        this.onItemClickListener = onItemClickListener;


    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_name_list_api_recycler;
        public TextView txt_rate_list_api_recycler;
        public TextView txt_type_list_api_recycler;
        public ImageView logo_list_recycler;
        public CardView layout_list_api_recycler;
        //        public  simpleRatingBar;
        ScaleRatingBar ratingBar = new ScaleRatingBar(BaseActivity.getContext());





        public ViewHolder(View itemView) {
            super(itemView);
            txt_name_list_api_recycler = (TextView) itemView.findViewById(R.id.txt_name_list_api_recycler);
            txt_rate_list_api_recycler = (TextView) itemView.findViewById(R.id.txt_rate_list_api_recycler);
            txt_type_list_api_recycler = (TextView) itemView.findViewById(R.id.txt_type_list_api_recycler);
            logo_list_recycler = (ImageView) itemView.findViewById(R.id.logo_list_recycler);
            layout_list_api_recycler = (CardView) itemView.findViewById(R.id.layout_list_api_recycler);
            ratingBar = (ScaleRatingBar) itemView.findViewById(R.id.simpleRatingBar);

        }

        @Override
        public void onClick(View v) {

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_api_recycler, parent, false);
        return new ViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ArrayList<JListApi> jListApiArrayList = requestItems.jListApis;

        if (!jListApiArrayList.get(position).getName().equals("")) {
            holder.txt_name_list_api_recycler.setText(jListApiArrayList.get(position).getName());
        }
        if (!jListApiArrayList.get(position).getType().equals("")) {
            holder.txt_type_list_api_recycler.setText(jListApiArrayList.get(position).getType());
        }
        if (jListApiArrayList.get(position).getScore() != -1) {
            double d = jListApiArrayList.get(position).getScore();
            float f = (float)d;
            holder.ratingBar.setRating(f/2);
            holder.txt_rate_list_api_recycler.setText(String.valueOf(jListApiArrayList.get(position).getScore()));
        }

        if (jListApiArrayList.get(position).getPicture() != null) {
            Picasso.with(BaseActivity.getContext())
                    .load("https://play.hen-dev.ir/" + jListApiArrayList.get(position).getPicture())
                    .into(holder.logo_list_recycler);
        }



        holder.layout_list_api_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickList(jListApiArrayList.get(position).getId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return requestItems.jListApis.size();
    }


}
