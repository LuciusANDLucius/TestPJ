package com.example.hitcapp.api;

import com.example.hitcapp.models.Cart;
import com.example.hitcapp.models.Category;
import com.example.hitcapp.models.Product;
import com.example.hitcapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // --- USERS ---
    @GET("user/getall")
    Call<List<User>> getAllUsers();

    @GET("user/get/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("user/register")
    Call<User> register(@Body User user);

    @POST("user/login")
    Call<User> login(@Body User user);

    @PUT("user/update")
    Call<User> updateUser(@Body User user);

    @DELETE("user/delete/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    // --- PRODUCTS ---
    @GET("products/getall")
    Call<List<Product>> getAllProducts();

    @GET("products/get/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @DELETE("products/remove/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    // --- CATEGORIES ---
    @GET("ProductCategory/GetAll")
    Call<List<Category>> getAllCategories();

    // --- CARTS ---
    @GET("cart/get/{userId}")
    Call<Cart> getCartByUserId(@Path("userId") int userId);

    @GET("cart_items/get/{cartId}")
    Call<List<Cart.CartProduct>> getCartItems(@Path("cartId") int cartId);

    @POST("cart/add")
    Call<Cart> addCart(@Body Cart cart);
}
