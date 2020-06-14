package com.example.onceuponabook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onceuponabook.Activities.ItemsActivity;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<BookDTO> mData;

    public ListRecyclerAdapter(Context mContext, List<BookDTO> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.card_vertical_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {

        viewHolder.tv_category_name.setText(mData.get(position).getCategory());
        String url=mData.get(position).getImagePath();
        Picasso.get().load(url).into(viewHolder.img_category);
        //viewHolder.img_category.setImageResource(mData.get(position).getThumbnail());

        viewHolder.img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ItemsActivity.class);
                intent.putExtra("Category",mData.get(position).getCategory());
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_category_name;
        ImageView img_category;


        public MyViewHolder(View itemView){
            super(itemView);

            tv_category_name=itemView.findViewById(R.id.tv_category_id);
            img_category=itemView.findViewById(R.id.image_vertical_id);

        }
    }

}
