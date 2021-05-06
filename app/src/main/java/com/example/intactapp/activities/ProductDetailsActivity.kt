package com.example.intactapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intactapp.Globals
import com.example.intactapp.R
import com.example.intactapp.adapters.ColorsAdapter
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var imgView: ImageView
    lateinit var descText: TextView
    lateinit var sizeText: TextView
    lateinit var priceText: TextView
    lateinit var actionButton: Button
    private lateinit var colorListView: RecyclerView

    val stars : ArrayList<ImageView> = ArrayList()
    val colorList: ArrayList<String> = ArrayList()

    private var isInWishList = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val aBar = supportActionBar
        aBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setIcon(R.drawable.arrow_forward)
            it.setHomeAsUpIndicator(R.drawable.baseline_chevron_left_36);

        }



        val prodId =  getIntent().getExtras()?.getInt("prodId")

        imgView = findViewById(R.id.iv_product_details_picutre)
        descText = findViewById(R.id.tv_product_details_description)
        sizeText = findViewById(R.id.tv_product_details_size_details)
        priceText = findViewById(R.id.tv_product_details_price)
        colorListView = findViewById(R.id.product_details_color_list)

        colorListView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        actionButton = findViewById(R.id.bt_product_detail_action_button)
        actionButton.setOnClickListener {
            updateWishList()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        stars.add(findViewById(R.id.iv_grade_star_1))
        stars.add(findViewById(R.id.iv_grade_star_2))
        stars.add(findViewById(R.id.iv_grade_star_3))
        stars.add(findViewById(R.id.iv_grade_star_4))
        stars.add(findViewById(R.id.iv_grade_star_5))


        for (i in 0..4) {
            stars[i].setOnClickListener {
                toggleStars(i)
            }
        }

        prodId?.let{
            loadDetails()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)

    }

    fun toggleStars(starNumber: Int) {
        for (i in 0..starNumber) {
            stars.get(i).setColorFilter(ContextCompat.getColor(this, R.color.star_yellow), android.graphics.PorterDuff.Mode.SRC_IN);

        }
        for (i in starNumber+1..4) {

            stars.get(i).setColorFilter(ContextCompat.getColor(this, R.color.star_grey), android.graphics.PorterDuff.Mode.SRC_IN);

        }

    }


    fun loadDetails() {
        val id = getIntent().getExtras()?.getInt("prodId")


        for (product in Globals.productCatatlog.products) {
            if (product.id==id) {
                Picasso.get().load(product.image).into(imgView)
                descText.text = product.description

                var sizeInfo =  ""


                if (product.size.has("H")) {
                    sizeInfo = sizeInfo + "H: " + product.size.get("H")
                }
                if (product.size.has("W")) {
                    if (!sizeInfo.equals(""))  sizeInfo = sizeInfo + "\n"
                    sizeInfo = sizeInfo + "W: " + product.size.get("W")
                }
                if (product.size.has("D")) {
                    if (!sizeInfo.equals(""))  sizeInfo = sizeInfo
                    sizeInfo = sizeInfo + "\nD: " + product.size.get("D")
                }

                sizeText.text = sizeInfo
                val price = resources.getString(R.string.currency_dollar_symbol) + " " + product.price
                priceText.text = price


                colorList.clear()
                for (color in product.colors) {
                    if (color.asJsonObject.has("code")) {
                        colorList.add(color.asJsonObject.get("code").asString)
                    }

                }
                val colorsAdapter = ColorsAdapter(colorList)
                colorListView.adapter = colorsAdapter

                setTitle(product.title);

                if (product.whishList) {
                    isInWishList = true
                    actionButton.setBackgroundResource(R.drawable.product_details_black_buttom)
                    actionButton.setText(R.string.product_details_remove_button_text)

                }
               return
            }
        }



    }

    fun updateWishList() {

        val id = getIntent().getExtras()?.getInt("prodId")

        id?.let { pId->
            for (product in Globals.productCatatlog.products) {

                if (product.id==id) {
                    product.whishList = !product.whishList
                    return
                }
            }

        }

    }


}