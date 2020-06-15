package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Adapters.CartRecyclerAdapter;
import com.example.onceuponabook.EnumClasses.OrderStatus;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    TextView numItems;
    TextView totalAmount;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        numItems=findViewById(R.id.item_count_tv);
        totalAmount=findViewById(R.id.total_amount_tv);
        checkout=findViewById(R.id.checkout_btn);

        final List<OrderBookDTO> lst=new ArrayList<>();
        final double[] total = {0};
        final int[] itemNo = {0};

        String userEmail= SharedPrefUtility.getInstance(CartActivity.this).getUserEmail();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEmail);

        Call<OrderDTO> apiClient = APIBuilder.createAuthBuilder(CartActivity.this).getCurrentOrderDetails(userDTO);
        apiClient.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(CartActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(CartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    //success scenario
                    OrderDTO orderDTO = response.body();
                    if(orderDTO.getOrderedBooks()==null){
                        Toast.makeText(CartActivity.this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
                    }else {
                        List<OrderBookDTO>cartitems=orderDTO.getOrderedBooks();
                        for(OrderBookDTO item:cartitems){
                            lst.add(item);
                            total[0] = total[0] +item.getBook().getPrice()*item.getQuantity();
                            itemNo[0] = itemNo[0] +item.getQuantity();
                        }
                        RecyclerView recyclerView=findViewById(R.id.recycler_cart_items);
                        CartRecyclerAdapter myAdapter=new CartRecyclerAdapter(CartActivity.this,lst);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        numItems.setText("Item Count : "+Integer.toString(itemNo[0]));

                        totalAmount.setText("Rs."+Double.toString(total[0]));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

//        RecyclerView recyclerView=findViewById(R.id.recycler_cart_items);
//        CartRecyclerAdapter myAdapter=new CartRecyclerAdapter(this,lst);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());



        final double finalTotal = total[0];
        final int finalItemNo = itemNo[0];
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,CheckoutActivity.class);
                intent.putExtra("orderTotal", finalTotal);
                intent.putExtra("itemCount", finalItemNo);
                startActivity(intent);

            }
        });

    }


}
