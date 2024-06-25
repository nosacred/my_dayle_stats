package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.OrderPerBarcode
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.articleLink
import com.squareup.picasso.Picasso
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WHAdapter(
    val ordersToday: List<OrderPerBarcode>
) : RecyclerView.Adapter<WHAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(
            parent.inflate(R.layout.items_wh)
        )
    }

    override fun getItemCount(): Int {
        return ordersToday.size
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val order = ordersToday[position]
        holder.bind(order, ordersToday)
    }

    class Holder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        private val brandTextView: TextView = view.findViewById(R.id.brandViewWH)
        private val imageOrder: ImageView = view.findViewById(R.id.imageOrder)



        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        @SuppressLint("SetTextI18n", "ResourceAsColor")

        fun bind(order: OrderPerBarcode, ordersToday: List<OrderPerBarcode>) {

            brandTextView.text=""


            brandTextView.text = "${order.brand} | ${order.nmId}\n"+
                    "Предмет: ${order.subject}\n"+
                    "Размер: ${order.techSize}\nАрт.:${order.supplierArticle}\n"+
                    "Баркод: ${order.barcode}\n"+
                    "Склад: ${order.warehouseName}\n"+
                    "Со склада заказано ${order.count}"




            Picasso.get()
                .load(articleLink.getImgUrl1(order.nmId))
                .resize(100, 150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(imageOrder)


        }
    }
}