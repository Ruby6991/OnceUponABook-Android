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
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Activities.CartActivity;
import com.example.onceuponabook.Activities.ItemDetailsActivity;
import com.example.onceuponabook.Activities.LoginActivity;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderBookDTO> mData;
    private OrderDTO orderDTO;

    public CartRecyclerAdapter(Context mContext, List<OrderBookDTO> mData, OrderDTO orderDTO) {
        this.mContext = mContext;
        this.mData = mData;
        this.orderDTO=orderDTO;
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
        viewHolder.tv_cartitem.setText(item.getTitle());
        String url=item.getImagePath();
        Picasso.get().load(url).into(viewHolder.img_cartitem);
        int currentQuantity=mData.get(position).getQuantity();
        viewHolder.tv_itemquantity.setText(Integer.toString(currentQuantity));
        viewHolder.tv_itemprice.setText("US$"+Double.toString(item.getPrice()*currentQuantity));

        viewHolder.btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderBookDTO orderBook=new OrderBookDTO();
                orderBook.setBook(item);
                orderBook.setOrder(orderDTO);

                Call<OrderBookDTO> apiClient = APIBuilder.createAuthBuilder(mContext).UpdateBookQuantity("increase", orderBook);
                apiClient.enqueue(new Callback<OrderBookDTO>() {
                    @Override
                    public void onResponse(Call<OrderBookDTO> call, Response<OrderBookDTO> response) {

                            OrderBookDTO orderBook = response.body();
                            if (orderBook!=null) {
                                int quantity=Integer.parseInt(viewHolder.tv_itemquantity.getText().toString())+1;
                                viewHolder.tv_itemquantity.setText(Integer.toString(quantity));

                                double newPrice=item.getPrice()*quantity;
                                viewHolder.tv_itemprice.setText("US$"+Double.toString(newPrice));

                                ((Activity)mContext).finish();
                                Intent intent=new Intent(mContext, CartActivity.class);
                                mContext.startActivity(intent);
                            }
                    }

                    @Override
                    public void onFailure(Call<OrderBookDTO> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });

            }
        });

        viewHolder.btn_Deduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBookDTO orderBook=new OrderBookDTO();
                orderBook.setBook(item);
                orderBook.setOrder(orderDTO);

                Call<OrderBookDTO> apiClient = APIBuilder.createAuthBuilder(mContext).UpdateBookQuantity("decrease", orderBook);
                apiClient.enqueue(new Callback<OrderBookDTO>() {
                    @Override
                    public void onResponse(Call<OrderBookDTO> call, Response<OrderBookDTO> response) {

                        OrderBookDTO orderBook = response.body();
                        if (orderBook!=null) {
                            int quantity=Integer.parseInt(viewHolder.tv_itemquantity.getText().toString())-1;
                            viewHolder.tv_itemquantity.setText(Integer.toString(quantity));

                            double newPrice=item.getPrice()*quantity;
                            viewHolder.tv_itemprice.setText("US$"+Double.toString(newPrice));

                            ((Activity)mContext).finish();
                            Intent intent=new Intent(mContext, CartActivity.class);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderBookDTO> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });

            }
        });

        viewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Boolean> apiClient = APIBuilder.createAuthBuilder(mContext).DeleteOrderBook(orderDTO.getId(),item.getId());
                apiClient.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        Boolean deleted = response.body();
                        if (deleted) {
                            Toast.makeText(mContext, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();

                            ((Activity)mContext).finish();
                            Intent intent=new Intent(mContext, CartActivity.class);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_cartitem;
        TextView tv_itemprice;
        TextView tv_itemquantity;
        ImageView img_cartitem;
        Button btn_Add;
        Button btn_Deduct;
        Button btn_Delete;


        public MyViewHolder(View itemView){
            super(itemView);

            tv_cartitem=itemView.findViewById(R.id.tv_cartitem_name);
            tv_itemprice=itemView.findViewById(R.id.tv_cartitem_tprice);
            tv_itemquantity=itemView.findViewById(R.id.tv_cartitem_quantity);
            img_cartitem=itemView.findViewById(R.id.image_cart);
            btn_Add=itemView.findViewById(R.id.plus_button);
            btn_Deduct=itemView.findViewById(R.id.minus_button);
            btn_Delete=itemView.findViewById(R.id.remove_button);
        }
    }

}
