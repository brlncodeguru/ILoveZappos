package com.example.lakshminarayanabr.ilovezappos;

import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by lakshminarayanabr on 2/5/17.
 */

public interface GetProductsAPI {
    @GET("/Search")
    void getProductsFromAPI(@Query("term") String searchQuery, @Query("key") String key, Callback<ProductData> callback);

}
