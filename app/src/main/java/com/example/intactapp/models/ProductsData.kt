package com.example.intactapp.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductsData {
    @SerializedName("products")
    @Expose
    var products : List<Product> = emptyList()

    var uploadTimestamp: Long  = 0

    fun resetWishes() {
       for (product in products) {
           product.whishList = false
       }
    }

}