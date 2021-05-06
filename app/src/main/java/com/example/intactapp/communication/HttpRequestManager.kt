package com.example.intactapp.communication


import android.util.Log
import com.example.intactapp.Globals
import com.example.intactapp.interfaces.HttpManagerCallBack
import com.example.intactapp.interfaces.WebApi
import com.example.intactapp.models.ProductsData
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class HttpRequestManager(val intf: HttpManagerCallBack) {

    var gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setLenient()
        .create()


    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Globals.URL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api : WebApi = retrofit.create(WebApi::class.java)


    fun getProductsData() {
        val call : Call<ProductsData> = api.GetProductData(
            "download",
            "180NdUCDsmJgCSAfwaJIoWOVSVdvqyNu2"
        )


        call.enqueue(object : Callback<ProductsData> {

            override fun onResponse(call: Call<ProductsData>, response: Response<ProductsData>) {
                if (response.isSuccessful) {
                    response.body()?.let { pData ->
                        Log.i(Globals.TAGLOG, "Product data downloaded!")
                        pData.products?.let { pList ->

                            intf.onProductsLoaded(pList)
                        }

                    } ?: kotlin.run {
                        Log.w(Globals.TAGLOG, "Could not get products form the server")
                    }
                } else {
                    Log.w(
                        Globals.TAGLOG,
                        "Could not get product products form the server: " + response.errorBody()
                    )
                    intf.onProductsError(response.errorBody().toString())
                }

            }

            override fun onFailure(call: Call<ProductsData>, t: Throwable) {
                Log.e(Globals.TAGLOG, t.message.toString() + " " + t.stackTrace)

            }

        })

    }


}