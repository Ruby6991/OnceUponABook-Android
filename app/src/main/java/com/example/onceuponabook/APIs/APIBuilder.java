package com.example.onceuponabook.APIs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.onceuponabook.Activities.HomeActivity;
import com.example.onceuponabook.Activities.LoginActivity;
import com.example.onceuponabook.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import androidx.fragment.app.FragmentActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIBuilder {

    private static APIClient apiClient;
    private static APIClient apiAuthClient;
    public static APIClient createBuilder(){
        if(apiClient==null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl("http://10.0.2.2:8080")
                    .baseUrl("http://192.168.1.6:8080")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            apiClient = retrofit.create(APIClient.class);
        }
        return apiClient;
    }

    public static APIClient createAuthBuilder(Context context){
        if(apiAuthClient==null){
            final String token = "Bearer "+ SharedPrefUtility.getInstance(context).getUserToken();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor( new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Authorization", token)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("http://10.0.2.2:8080")
                    .baseUrl("http://192.168.1.6:8080")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            apiAuthClient = retrofit.create(APIClient.class);
        }
        return apiAuthClient;
    }

}

