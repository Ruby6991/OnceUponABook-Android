package com.example.onceuponabook.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.onceuponabook.APIs.APIBuilder;
import com.example.onceuponabook.Models.UserDTO;
import com.example.onceuponabook.SharedPrefUtility;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPrefUtility sharedPref = SharedPrefUtility.getInstance(SplashActivity.this);

        String token = sharedPref.getUserToken();
        if(token.isEmpty()) {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            UserDTO userDTO = new UserDTO(sharedPref.getUserEmail(),sharedPref.getUserPass(),sharedPref.getUserToken());
            Call<Boolean> apiClient = APIBuilder.createBuilder().validateToken(userDTO);
            apiClient.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean status = response.body();
                    System.out.println("status "+status);
                    if(status==null){
                        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    System.err.println("error "+t.getMessage());
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();
    }
}
