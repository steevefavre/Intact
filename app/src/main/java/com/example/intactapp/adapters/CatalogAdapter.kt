package com.example.intactapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.intactapp.R
import com.example.intactapp.communication.HttpRequestManager
import com.example.intactapp.interfaces.HttpManagerCallBack
import com.example.intactapp.models.Product
import com.example.intactapp.models.ProductsData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CatalogAdapter(val productList: List<Product>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    lateinit var mContex: Context

    interface OnItemClickListener{
        fun onItemClicked(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.catalog_item, parent, false)
        mContex = parent.context
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: CatalogAdapter.ViewHolder, position: Int) {
        holder.bindItems(productList[position], itemClickListener)
        //Picasso
        productList.get(position).image?.let {
               Picasso.get().load(it).into(holder.pImg, object: Callback{
                   override fun onSuccess() {

                   }

                   override fun onError(e: Exception?) {
                       //Something to do if we can not get image, add a default picture maybe

                       /*holder.pImg.setImageDrawable(
                           ResourcesCompat.getDrawable(
                               mContex.resources,
                               R.drawable.no_image,
                               null
                           )
                       )*/
                   }

               })
        }


    }


    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pImg : ImageView = itemView.findViewById(R.id.iv_catalogue_item_picture) as ImageView
        var pName : TextView = itemView.findViewById(R.id.tv_catalogue_item_name) as TextView

        fun bindItems(product: Product, clickListener: OnItemClickListener) {

            pName.text = product.title
            pName.isSelected = true
            itemView.setOnClickListener {
                clickListener.onItemClicked(product)
            }

        }

    }
}