package com.example.onceuponabook.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Adapters.ItemsRecyclerAdapter;
import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.R;
import com.example.onceuponabook.SharedPrefUtility;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsActivity extends AppCompatActivity {

    List<BookDTO> lstItem=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        androidx.appcompat.widget.Toolbar  toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Books");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        final String category=intent.getStringExtra("Category");

        Call<List<BookDTO>> apiClient = APIBuilder.createAuthBuilder(ItemsActivity.this).getBookList();
        apiClient.enqueue(new Callback<List<BookDTO>>() {
            @Override
            public void onResponse(Call<List<BookDTO>> call, Response<List<BookDTO>> response) {
                if(response.code()==401){
                    SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(ItemsActivity.this);
                    sharedPref.resetSharedPreferences();
                    Intent intent=new Intent(ItemsActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    List<BookDTO> bookList = response.body();

                    if(bookList!=null) {
                        for(BookDTO book: bookList){
                            if(book.getCategory().equals(category)){
                                lstItem.add(book);
                            }
                        }
                        RecyclerView recyclerView=findViewById(R.id.recycler_view_id);
                        ItemsRecyclerAdapter myAdapter=new ItemsRecyclerAdapter(ItemsActivity.this,lstItem);
                        recyclerView.setLayoutManager(new GridLayoutManager(ItemsActivity.this,2));
                        recyclerView.setAdapter(myAdapter);

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
