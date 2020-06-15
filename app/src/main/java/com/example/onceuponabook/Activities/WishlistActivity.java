package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Adapters.ItemsRecyclerAdapter;
import com.example.onceuponabook.EnumClasses.OrderStatus;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    List<BookDTO> lstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Wishlist");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        String userEmail= SharedPrefUtility.getInstance(WishlistActivity.this).getUserEmail();
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(userEmail);

        final Call<List<BookDTO>> apiClient = APIBuilder.createAuthBuilder(WishlistActivity.this).getWishList(userDTO);
        apiClient.enqueue(new Callback<List<BookDTO>>() {
            @Override
            public void onResponse(Call<List<BookDTO>> call, Response<List<BookDTO>> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(WishlistActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(WishlistActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    List<BookDTO> wishList = response.body();

                    if(wishList.size()>0) {
                        lstItem=new ArrayList<>();
                        for(BookDTO book: wishList){
                            lstItem.add(book);
                        }
                        RecyclerView recyclerView=findViewById(R.id.recycler_view_wishlist);
                        ItemsRecyclerAdapter myAdapter=new ItemsRecyclerAdapter(WishlistActivity.this,lstItem);
                        recyclerView.setLayoutManager(new GridLayoutManager(WishlistActivity.this,2));
                        recyclerView.setAdapter(myAdapter);

                    }else{
                        Toast.makeText(WishlistActivity.this, "No Items in your Wishlist", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookDTO>> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });




    }
}

