package com.example.onceuponabook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PurchaseRecyclerAdapter extends RecyclerView.Adapter<PurchaseRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderBookDTO> mData;

    public PurchaseRecyclerAdapter(Context mContext, List<OrderBookDTO> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.purchased_items_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final BookDTO item=mData.get(position).getBook();
        final int code=item.getId();
        viewHolder.tv_purchaseditem.setText(item.getTitle());
        String url=item.getImagePath();
        Picasso.get().load(url).into(viewHolder.img_purchaseditem);
        int currentQuantity=mData.get(position).getQuantity();
        viewHolder.tv_purchasedquantity.setText(Integer.toString(currentQuantity));
        viewHolder.tv_purchasedprice.setText("Rs."+Double.toString(item.getPrice()*currentQuantity));

        viewHolder.btn_submitreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review=viewHolder.et_writereview.getText().toString();

                String userEmail= SharedPrefUtility.getInstance(mContext).getUserEmail();
//                List<User> users=User.listAll(User.class);
//                for (User user:users) {
//                    if (user.getEmail().equals(userEmail)) {
//                        List<Item> items=Item.listAll(Item.class);
//                        for(Item item:items) {
//                            if (item.getCode() == code) {
//                                Review review1 = new Review(review, user, item);
//                                review1.save();
//                                Toast.makeText(mContext,
//                                        "Your Review Has Been Sent",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_purchaseditem;
        TextView tv_purchasedsize;
        TextView tv_purchasedcolor;
        TextView tv_purchasedprice;
        TextView tv_purchasedquantity;
        ImageView img_purchaseditem;
        EditText et_writereview;
        Button btn_submitreview;



        public MyViewHolder(View itemView){
            super(itemView);

            tv_purchaseditem=itemView.findViewById(R.id.tv_purchased_item);
            tv_purchasedsize=itemView.findViewById(R.id.tv_purchased_size);
            tv_purchasedcolor=itemView.findViewById(R.id.tv_purchased_color);
            tv_purchasedprice=itemView.findViewById(R.id.tv_purchased_price);
            tv_purchasedquantity=itemView.findViewById(R.id.tv_purchased_quantity);
            img_purchaseditem=itemView.findViewById(R.id.image_purchased);
            et_writereview=itemView.findViewById(R.id.et_review);
            btn_submitreview=itemView.findViewById(R.id.btn_send_review);
        }
    }

}
