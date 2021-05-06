package com.example.intactapp.interfaces

import com.example.intactapp.models.ProductsData
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApi {
    @GET("uc?")
    fun GetProductData(@Query("export") export:String, @Query("id") id:String) : Call<ProductsData>

//    @GET("view?")
//    fun GetProductData2(@Query("usp") usp: String = "sharing") : Call<ProductsData>

//    @GET("uc?")
//    fun GetJsonProductData(@Query("export") export:String, @Query("id") id:String) : Call<JsonObject>

//    @GET("uc?")
//    fun GetImageData(@Query("export") export:String, @Query("id") id:String) : Call<ResponseBody>
}