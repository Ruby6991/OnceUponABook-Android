package com.example.onceuponabook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderBookDTO> mData;

    public CartRecyclerAdapter(Context mContext, List<OrderBookDTO> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.cart_items_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final BookDTO item=mData.get(position).getBook();
//        viewHolder.tv_cartitem.setText(item.getTitle());
//        String url=item.getImagePath();
//        Picasso.get().load(url).into(viewHolder.img_cartitem);
//        viewHolder.tv_itemsize.setText("Size : "+mData.get(position).getSize());
//        viewHolder.tv_itemcolor.setText("Color : "+mData.get(position).getColor());
//        int currentQuantity=mData.get(position).getQuantity();
//        viewHolder.tv_itemquantity.setText(Integer.toString(currentQuantity));
//        viewHolder.tv_itemprice.setText("Rs."+Integer.toString(item.getPrice()*currentQuantity));

        viewHolder.btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int quantity=Integer.parseInt(viewHolder.tv_itemquantity.getText().toString())+1;
//                mData.get(position).setQuantity(quantity);
//                mData.get(position).save();
//                viewHolder.tv_itemquantity.setText(Integer.toString(quantity));
//
//                Long itemID = item.getId();
//                Item updateItem = Item.findById(Item.class, itemID);
//                updateItem.setQtyInStock(item.getQtyInStock()-1);
//                updateItem.save();
//
//
//                int newPrice=item.getPrice()*quantity;
//                viewHolder.tv_itemprice.setText("Rs."+Integer.toString(newPrice));
//
//                ((Activity)mContext).finish();
//                Intent intent=new Intent(mContext,CartActivity.class);
//                mContext.startActivity(intent);
            }
        });

        viewHolder.btn_Deduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int quantity=Integer.parseInt(viewHolder.tv_itemquantity.getText().toString())-1;
//                mData.get(position).setQuantity(quantity);
//                mData.get(position).save();
//                viewHolder.tv_itemquantity.setText(Integer.toString(quantity));
//
//                Long itemID = item.getId();
//                Item updateItem = Item.findById(Item.class, itemID);
//                updateItem.setQtyInStock(item.getQtyInStock()+1);
//                updateItem.save();
//
//                int newPrice=item.getPrice()*quantity;
//                viewHolder.tv_itemprice.setText("Rs."+Integer.toString(newPrice));
//
//                ((Activity)mContext).finish();
//                Intent intent=new Intent(mContext,CartActivity.class);
//                mContext.startActivity(intent);
            }
        });

        viewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mData.get(position).delete();
//                viewHolder.itemView.setVisibility(View.INVISIBLE);
//
//                int quantity=Integer.parseInt(viewHolder.tv_itemquantity.getText().toString());
//                Long itemID = item.getId();
//                Item updateItem = Item.findById(Item.class, itemID);
//                updateItem.setQtyInStock(item.getQtyInStock()+quantity);
//                updateItem.save();
//
//                ((Activity)mContext).finish();
//                Intent intent=new Intent(mContext,CartActivity.class);
//                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_cartitem;
        TextView tv_itemsize;
        TextView tv_itemcolor;
        TextView tv_itemprice;
        TextView tv_itemquantity;
        ImageView img_cartitem;
        Button btn_Add;
        Button btn_Deduct;
        Button btn_Delete;


        public MyViewHolder(View itemView){
            super(itemView);

//            tv_cartitem=itemView.findViewById(R.id.tv_cartitem_name);
//            tv_itemsize=itemView.findViewById(R.id.tv_cartitem_size);
//            tv_itemcolor=itemView.findViewById(R.id.tv_cartitem_color);
//            tv_itemprice=itemView.findViewById(R.id.tv_cartitem_tprice);
//            tv_itemquantity=itemView.findViewById(R.id.tv_cartitem_quantity);
//            img_cartitem=itemView.findViewById(R.id.image_cart);
//            btn_Add=itemView.findViewById(R.id.plus_button);
//            btn_Deduct=itemView.findViewById(R.id.minus_button);
//            btn_Delete=itemView.findViewById(R.id.remove_button);
        }
    }

}
