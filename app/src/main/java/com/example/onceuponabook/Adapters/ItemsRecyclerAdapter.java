package com.example.onceuponabook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onceuponabook.Activities.ItemDetailsActivity;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<BookDTO> mData;



    public ItemsRecyclerAdapter(Context mContext, List<BookDTO> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.cardview_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        viewHolder.tv_item_name.setText(mData.get(position).getTitle());
        final String price="Rs."+Double.toString(mData.get(position).getPrice());

        if(mData.get(position).getQtyInStock()==0){
            viewHolder.img_item_thumbnail.setImageResource(R.drawable.soldout);
        }else{

            String url=mData.get(position).getImagePath();
            Picasso.get().load(url).into(viewHolder.img_item_thumbnail);

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(mContext, ItemDetailsActivity.class);

                    //Passing data to the item details activity
                    intent.putExtra("Code",mData.get(position).getId());
                    intent.putExtra("Image",mData.get(position).getImagePath());
                    intent.putExtra("ItemName",mData.get(position).getTitle());
                    intent.putExtra("Price",price);
                    intent.putExtra("Description",mData.get(position).getDescription());
                    intent.putExtra("quantity",mData.get(position).getQtyInStock());

                    //Start the activity
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_item_name;
        ImageView img_item_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_item_name=itemView.findViewById(R.id.item_name_id);
            img_item_thumbnail=itemView.findViewById(R.id.item_img_id);
            cardView=itemView.findViewById(R.id.card_view_id);
        }
    }
}
