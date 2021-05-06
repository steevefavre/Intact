package com.example.intactapp.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intactapp.R
import com.example.intactapp.models.Product
import com.squareup.picasso.Picasso

class WishListAdapter (val productList: List<Product>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    lateinit var mContext: Context

    interface OnItemClickListener {
        fun onItemClicked(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wish_list_item_line, parent, false)

        mContext = parent.context

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: WishListAdapter.ViewHolder, position: Int) {
        holder.bindItems(productList[position], itemClickListener, mContext)
        //Picasso
        productList.get(position).image?.let {
            Picasso.get().load(it).into(holder.pImg)
        }


    }


    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pName: TextView = itemView.findViewById(R.id.tv_wish_list_item_name) as TextView
        var pImg: ImageView = itemView.findViewById(R.id.iv_wish_list_picture) as ImageView
        var pPrice: TextView = itemView.findViewById(R.id.tv_wish_list_item_price) as TextView
        var pDesc: TextView = itemView.findViewById(R.id.tv_wish_list_item_description) as TextView
        var pAvailablility: TextView = itemView.findViewById(R.id.tv_item_wish_list_out_of_stock) as TextView
        var pColorList: RecyclerView = itemView.findViewById(R.id.wish_list_color_list) as RecyclerView

        fun bindItems(product: Product, clickListener: OnItemClickListener, context: Context) {

            pColorList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val colorList : ArrayList<String> = ArrayList()

            for (color in product.colors) {
                if (color.asJsonObject.has("code")) {
                    colorList.add(color.asJsonObject.get("code").asString)
                }

            }
            val colorsAdapter = ColorsAdapter(colorList)
            pColorList.adapter = colorsAdapter


            pName.text = product.title

            val price = "$ " + product.price.toString()

            pPrice.text = price
            pDesc.text = product.short_description

            if (product.quantity==0) {
                pAvailablility.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(product)
            }

        }

    }
}
