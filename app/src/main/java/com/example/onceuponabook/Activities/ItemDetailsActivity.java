package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.EnumClasses.OrderStatus;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView tvPrice;
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvDescription;
    private Button btnAddToCart;
    private ImageButton btnAddToWishlist;
    private ListView lvReviews;


    int code;
    String img;
    List<OrderBookDTO> lstCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Book Details");
        setSupportActionBar(toolbar);

        ivImage=(ImageView)findViewById(R.id.item_image);
        tvName=(TextView)findViewById(R.id.item_name_tv);
        tvPrice=(TextView)findViewById(R.id.item_price_tv);
        tvDescription=(TextView)findViewById(R.id.tv_item_description);

        //Receive Data
        Intent intent=getIntent();
        code=intent.getExtras().getInt("Code");
        img=intent.getExtras().getString("Image");
        String name=intent.getExtras().getString("ItemName");
        String Price=intent.getExtras().getString("Price");
        String description=intent.getExtras().getString("Description");
        final int quantity=intent.getExtras().getInt("quantity");

        //Setting Values
        Picasso.get().load(img).into(ivImage);
        tvName.setText(name);
        tvPrice.setText(Price);
        tvDescription.setText(description);

        btnAddToCart=findViewById(R.id.add_to_cart_btn);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstCartItems=new ArrayList<>();
                String userEmail= SharedPrefUtility.getInstance(ItemDetailsActivity.this).getUserEmail();
                final UserDTO userDTO=new UserDTO();
                userDTO.setEmail(userEmail);

                if(quantity>0){
                    final Call<List<OrderDTO>> apiClient = APIBuilder.createAuthBuilder(ItemDetailsActivity.this).getOrderListByUser(userDTO);
                    apiClient.enqueue(new Callback<List<OrderDTO>>() {
                        @Override
                        public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                            if(response.code()==401){
                                SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ItemDetailsActivity.this);
                                sharedPref.resetSharedPreferences();
                                Intent intent=new Intent(ItemDetailsActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                List<OrderDTO> orderList = response.body();

                                if(orderList.size()>0) {
                                    for(final OrderDTO order: orderList){
                                        if(order.getStatus().equals(OrderStatus.Pending)){
                                            BookDTO bookDTO = new BookDTO();
                                            bookDTO.setId(code);

                                            OrderBookDTO orderBook=new OrderBookDTO();
                                            orderBook.setBook(bookDTO);
                                            orderBook.setOrder(order);
                                            orderBook.setQuantity(1);

                                            List<OrderBookDTO> orderBookDTOS=new ArrayList<>();
                                            orderBookDTOS.add(orderBook);

                                            OrderDTO newOrder=new OrderDTO();
                                            newOrder.setOrderedBooks(orderBookDTOS);
                                            Call<OrderDTO> apiClient = APIBuilder.createAuthBuilder(ItemDetailsActivity.this).UpdateOrderAddBook(order.getId(), newOrder);
                                            apiClient.enqueue(new Callback<OrderDTO>() {
                                                @Override
                                                public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                                                    if(response.code()==401){
                                                        SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ItemDetailsActivity.this);
                                                        sharedPref.resetSharedPreferences();
                                                        Intent intent=new Intent(ItemDetailsActivity.this,LoginActivity.class);
                                                        startActivity(intent);
                                                    }else {
                                                        OrderDTO order = response.body();
                                                        if (order!=null) { ;
                                                            Toast.makeText(ItemDetailsActivity.this,
                                                                    "Product Added to Cart",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OrderDTO> call, Throwable t) {
                                                    System.err.println(t.getMessage());
                                                }
                                            });
                                        }else{
                                            //create a pending order
                                            createNewOrder();
                                        }
                                    }
                                }else{
                                    //create a new pending order
                                    createNewOrder();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                            System.err.println(t.getMessage());
                        }
                    });
                }else{
                    Toast.makeText(ItemDetailsActivity.this,
                            "Sorry This Item Is Out of Stock!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToWishlist=findViewById(R.id.add_to_wishlist_btn);
        btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=SharedPrefUtility.getInstance(ItemDetailsActivity.this).getUserEmail();
                BookDTO bookDTO=new BookDTO();
                bookDTO.setId(code);

                Call<Boolean> apiClient = APIBuilder.createAuthBuilder(ItemDetailsActivity.this).CreateWishlistItem(userEmail, bookDTO);
                apiClient.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.code()==401){
                            SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ItemDetailsActivity.this);
                            sharedPref.resetSharedPreferences();
                            Intent intent=new Intent(ItemDetailsActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            btnAddToWishlist.setImageResource(R.drawable.heartpink);
                            Toast.makeText(ItemDetailsActivity.this,
                                    "Product Added to Wishlist",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });
            }
        });

//        lvReviews=findViewById(R.id.list_view_reviews);
//        List<Review> reviews=Review.listAll(Review.class);
//        int i=0;
//        if(reviews.size()>0){
//            String[] arr=new String[reviews.size()];
//            if(reviews.size()>0) {
//                for (Review review : reviews) {
//                    Item reviewedItem = review.getItem();
//                    if (reviewedItem.getCode() == code) {
//                        String reviewUser=review.getUser().getfName()+" "
//                                +review.getUser().getlName()+" : ";
//                        String reviewContent=review.getReview();
//                        arr[i]=reviewUser+"\n"+reviewContent;
//                        i++;
//                    }
//                }
//            }
//            if(arr[0]!=null) {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, android.R.id.text1, arr);
//                lvReviews.setAdapter(adapter);
//            }
//        }else {
//            String[] noReviews=new String[1];
//            noReviews[0]="No reviews have been made about this product yet";
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, android.R.id.text1, noReviews);
//            lvReviews.setAdapter(adapter);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_screen, menu);
        return true;
    }

    public void createNewOrder(){
        String userEmail= SharedPrefUtility.getInstance(ItemDetailsActivity.this).getUserEmail();
        final UserDTO userDTO=new UserDTO();
        userDTO.setEmail(userEmail);
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(userDTO);
        Call<Boolean> apiClient = APIBuilder.createAuthBuilder(ItemDetailsActivity.this).CreateOrder(orderDTO);
        apiClient.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ItemDetailsActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(ItemDetailsActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    btnAddToCart.performClick();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });
    }
}
