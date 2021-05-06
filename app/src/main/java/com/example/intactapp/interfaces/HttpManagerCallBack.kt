package com.example.intactapp.interfaces

import android.graphics.Bitmap
import com.example.intactapp.models.Product


interface HttpManagerCallBack {
    fun onProductsLoaded(list: List<Product>)

    fun onProductsError(error: String)
}