package com.example.intactapp.activities

import android.content.Context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intactapp.Globals
import com.example.intactapp.R
import com.example.intactapp.adapters.CatalogAdapter
import com.example.intactapp.adapters.WishListAdapter
import com.example.intactapp.communication.HttpRequestManager
import com.example.intactapp.interfaces.HttpManagerCallBack
import com.example.intactapp.models.Product

class MainActivity : AppCompatActivity() {

    private lateinit var catalogListView: RecyclerView
    private lateinit var wishListView: RecyclerView
    private lateinit var reqMngr : HttpRequestManager
    private lateinit var mContext : Context
    private lateinit var  catalogAdapter: CatalogAdapter

    private lateinit var subTotal1: TextView
    private lateinit var subTotal2: TextView

    lateinit var wishListAdapter: WishListAdapter
    private var wishList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("")
        val aBar = supportActionBar
        aBar?.let {
            it.setDisplayShowCustomEnabled(true)
            val cView = layoutInflater.inflate(R.layout.action_bar_home, null)
            it.customView = cView
        }


        catalogListView = findViewById(R.id.catalogue_list_view)
        wishListView = findViewById(R.id.wish_list_view)
        subTotal1 = findViewById(R.id.tv_total_price)
        subTotal2 = findViewById(R.id.tv_wish_list_sub_total)


        val proceedButton : AppCompatButton = findViewById(R.id.bt_wish_list_proceed_button)

        proceedButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
            val btDismiss : AppCompatButton = dialogView.findViewById(R.id.bt_dialog_cancel)
            val btOk : AppCompatButton = dialogView.findViewById(R.id.bt_dialog_ok)

            val alertDialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .show()

            btDismiss.setOnClickListener {
                alertDialog.dismiss()
            }

            btOk.setOnClickListener {
                Globals.productCatatlog.resetWishes()
                updateWishList()
                alertDialog.dismiss()
            }



        }

        catalogListView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        wishListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        wishListAdapter = WishListAdapter(wishList, object: WishListAdapter.OnItemClickListener{
            override fun onItemClicked(product: Product) {
                val intent = Intent(mContext, ProductDetailsActivity::class.java)
                intent.putExtra("prodId", product.id)
                startActivity(intent)
            }

        })

        wishListView.adapter = wishListAdapter

        mContext = this

    }

    override fun onResume() {
        super.onResume()

        if (Globals.productCatatlog.uploadTimestamp == 0L ||
            System.currentTimeMillis() > (Globals.productCatatlog.uploadTimestamp + Globals.CATALOG_DATA_VALIDITY)) {

            reqMngr = HttpRequestManager(object: HttpManagerCallBack{

                override fun onProductsLoaded(list: List<Product>) {


                    list.let {
                        for (item in it) {
                            Log.i(Globals.TAGLOG,item.brand + " " + item.description )
                        }
                        Globals.productCatatlog.uploadTimestamp = System.currentTimeMillis()
                        Globals.productCatatlog.products = it

                        catalogAdapter = CatalogAdapter(Globals.productCatatlog.products, object: CatalogAdapter.OnItemClickListener{
                            override fun onItemClicked(product: Product) {
                                val intent = Intent(mContext, ProductDetailsActivity::class.java)
                                intent.putExtra("prodId", product.id)

                                startActivity(intent)
                            }

                        })

                        catalogListView.adapter = catalogAdapter

                    }
                }

                override fun onProductsError(error: String) {
                    reqMngr.getProductsData()
                }
            })

            reqMngr.getProductsData()

        } else {
            catalogAdapter = CatalogAdapter(Globals.productCatatlog.products, object: CatalogAdapter.OnItemClickListener{
                override fun onItemClicked(product: Product) {
                    val intent = Intent(mContext, ProductDetailsActivity::class.java)
                    intent.putExtra("prodId", product.id)

                    startActivity(intent)
                }

            })

            catalogListView.adapter = catalogAdapter
        }

       updateWishList()

    }


    private fun updateWishList(){
        var total = 0F;
        wishList.clear()

        for (product in Globals.productCatatlog.products) {

            if (product.whishList) {
                wishList.add(product)

                product.price?.let {price->
                    total = total + price
                }

                Log.i(Globals.TAGLOG, "Product id " + product.id + " in wishlist")
            }
        }


        val price1 = resources.getString(R.string.wish_list_total_label) + " " + resources.getString(R.string.currency_dollar_symbol) +   String.format("%.2f", total)
        val price2 = resources.getString(R.string.currency_dollar_symbol) + String.format("%.2f", total)
        subTotal1.text = price1
        subTotal2.text = price2

        wishListAdapter.notifyDataSetChanged()

    }



}