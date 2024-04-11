package com.example.my_dayle_stats

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter

class SaleAdapter(
    val sales: List<Sale>,
):RecyclerView.Adapter<SaleAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(parent.inflate(R.layout.items_sales))

    }

    override fun getItemCount(): Int {
        return sales.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val sale = sales[position]
        holder.bind(sale, sales)
    }
    class Holder(view:View):RecyclerView.ViewHolder(view){

        private val dateTextView:TextView = view.findViewById(R.id.dateTxtView)
        private val brandTextView:TextView = view.findViewById(R.id.brandView)
        private val categorySubjectTextView:TextView = view.findViewById(R.id.categorySubjectView)
        private val sizeTextView:TextView = view.findViewById(R.id.sizeView)
        private val werehouseTextView:TextView = view.findViewById(R.id.wereHouseView)
        private val costTextView:TextView = view.findViewById(R.id.costView)
        private val totalOrderTodayTextView:TextView = view.findViewById(R.id.totalOrdersToday)
        private val imageOrder:ImageView = view.findViewById(R.id.imageOrder)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SuspiciousIndentation")
        fun bind(sale: Sale, salesAll : List<Sale>){
            var totalSum = 0.0
            for(sale1: Sale in salesAll){
                if(sale1.barcode.equals(sale.barcode))
                totalSum += sale1.forPay
            }

            var backSum = 0.0
            for(sale1: Sale in salesAll){
                if(sale1.barcode.equals(sale.barcode) && sale1.forPay<0)
                    backSum += sale1.forPay
            }

            val formatter = DateTimeFormatter.ofPattern("hh:MM dd MMMM yyyy")

            dateTextView.text = "Выкуп №${salesAll.size -  salesAll.indexOf(sale)} от  ${sale.date.format(formatter)}"
            brandTextView.text =" ${sale.brand } | ${sale.nmId}"
            categorySubjectTextView.text= "${sale.category} : ${sale.subject}"
            sizeTextView.text = "Размер: ${sale.techSize}"
            werehouseTextView.text = "${sale.warehouseName} -> ${sale.regionName}"
            costTextView.text = sale.priceWithDisc.toString()+" руб"
            totalOrderTodayTextView.text ="Сегодня выкупили ${salesAll.stream().filter { o-> o.barcode.equals(sale.barcode) }.filter{it.forPay >0}.count()} шт/ ${totalSum.toInt()} руб\n" +
                    "Возвратов ${salesAll.stream().filter {it.barcode.equals(sale.barcode) }.filter{it.forPay <0}.count()}шт/${backSum.toInt()} руб"


            Picasso.get()
                .load(articleLink.getImgUrl1(sale.nmId))
                .resize(100,150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(imageOrder)

            if(sale.forPay <0){
                dateTextView.setBackgroundColor(Color.parseColor("#E66761"))
            } else dateTextView.setBackgroundColor(Color.parseColor("#7CB342"))
        }
    }
}