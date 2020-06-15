package com.example.onceuponabook.APIs;

import com.example.onceuponabook.Models.BookDTO;
import com.example.onceuponabook.Models.OrderBookDTO;
import com.example.onceuponabook.Models.OrderDTO;
import com.example.onceuponabook.Models.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIClient {

    @POST("/authenticate")
    Call<UserDTO> createAuthenticationToken(@Body UserDTO authenticationRequest);

    @POST("/validateToken")
    Call<Boolean> validateToken(@Body UserDTO userDTO);

    @POST("/Register")
    Call<Boolean> registerUser(@Body UserDTO userDTO);

    @POST("/GetBookList")
    Call<List<BookDTO>> getBookList();

    @POST("/GetOrderListByUser")
    Call<List<OrderDTO>> getOrderListByUser(@Body UserDTO user);

    @PUT("/UpdateOrderAddBook/{id}")
    Call<OrderDTO> UpdateOrderAddBook (@Path("id") int id, @Body OrderDTO order);

    @POST("/CreateOrder")
    Call<Boolean> CreateOrder (@Body OrderDTO order);

    @POST("/GetCurrentOrder")
    Call<OrderDTO> getCurrentOrderDetails(@Body UserDTO user);

    @PUT("/updateBookQuantity/{action}")
    Call<OrderBookDTO> UpdateBookQuantity (@Path("action") String action, @Body OrderBookDTO orderBookDTO);

    @DELETE("/DeleteOrderBook/{orderId}/{bookId}")
    Call<Boolean> DeleteOrderBook(@Path("orderId") int orderId, @Path("bookId") int bookId );

}
