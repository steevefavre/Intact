package com.example.intactapp


import com.example.intactapp.models.ProductsData

class Globals {
    companion object {
        val URL_SERVER = "https://drive.google.com/"
        val TAGLOG = "LOG-INTACT-APP"
        val productCatatlog = ProductsData()

        val CATALOG_DATA_VALIDITY = 1000 * 60 * 60 // 1 hour

    }

}