package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Adapters.PurchaseRecyclerAdapter;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_items);

        Intent intent=getIntent();
        String cartID=intent.getStringExtra("cartID");

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Cart ID: "+ cartID);
        setSupportActionBar(toolbar);

        final List<OrderBookDTO> lst=new ArrayList<>();

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setId(Integer.parseInt(cartID));

        Call<OrderDTO> apiClient = APIBuilder.createAuthBuilder(HistoryItemsActivity.this).getOrderDetails(orderDTO);
        apiClient.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(HistoryItemsActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(HistoryItemsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    OrderDTO pastOrder = response.body();
                    if(pastOrder!=null){
                        List<OrderBookDTO> cartitems=pastOrder.getOrderedBooks();
                        for(OrderBookDTO item:cartitems){
                            lst.add(item);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });

        RecyclerView recyclerView=findViewById(R.id.recycler_purchased_items);
        PurchaseRecyclerAdapter myAdapter=new PurchaseRecyclerAdapter(this,lst);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
