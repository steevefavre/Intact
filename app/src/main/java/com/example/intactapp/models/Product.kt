package com.example.intactapp.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("id")
    @Expose
    var id : Int? = null

    @SerializedName("title")
    @Expose
    var title : String? = null

    @SerializedName("brand")
    @Expose
    var brand : String? = null

    @SerializedName("short_description")
    @Expose
    var short_description : String? = null

    @SerializedName("description")
    @Expose
    var description : String? = null

    @SerializedName("price")
    @Expose
    var price : Float? = null

    @SerializedName("image")
    @Expose
    var image : String? = null

    @SerializedName("size")
    @Expose
    var size : JsonObject = JsonObject()

    @SerializedName("colors")
    @Expose
    var colors : JsonArray = JsonArray()

    @SerializedName("quantity")
    @Expose
    var quantity : Int? = null

    var whishList : Boolean = false

}