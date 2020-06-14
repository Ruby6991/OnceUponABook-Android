package com.example.onceuponabook.APIs;

import com.example.onceuponabook.Models.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIClient {

    @POST("/authenticate")
    Call<UserDTO> createAuthenticationToken(@Body UserDTO authenticationRequest);

    @POST("/validateToken")
    Call<Boolean> validateToken(@Body UserDTO userDTO);

    @POST("/Register")
    Call<Boolean> registerUser(@Body UserDTO userDTO);

}
