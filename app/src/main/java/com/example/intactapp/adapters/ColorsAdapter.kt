package com.example.intactapp.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intactapp.R

class ColorsAdapter (val hexColorsList: List<String>) : RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ColorsAdapter.ViewHolder, position: Int) {

        holder.bindItems(hexColorsList[position])
    }


    override fun getItemCount(): Int {
        return hexColorsList.size

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var box: LinearLayout = itemView.findViewById(R.id.box_item_color) as LinearLayout

        fun bindItems(hColor: String) {
            box.setBackgroundColor(Color.parseColor(hColor))
        }

    }
}