package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.CompositeStat
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.articleLink
import com.squareup.picasso.Picasso
import java.time.ZoneId
import java.time.ZonedDateTime

class MonthStatsAdapter(
    private val onItemClicked:(position: Int) ->Unit,
    val compositeStats : List<CompositeStat>

):RecyclerView.Adapter<MonthStatsAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(parent.inflate(R.layout.item_month_stats),
            onItemClicked)
    }

    override fun getItemCount(): Int {
        return compositeStats.size
    }



    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val compositeStat = compositeStats[position]
        holder.bind(compositeStat)
    }

    class Holder(
        view: View,
        onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {


        val mstat_arcticle_nmId=view.findViewById<TextView>(R.id.mstat_arcticle_nmId)
        val mstat_subject_category=view.findViewById<TextView>(R.id.mstat_subject_category)
        val mstat_bcode=view.findViewById<TextView>(R.id.mstat_bcode)
        val mstat_size=view.findViewById<TextView>(R.id.mstat_size)
        val mstat_count_order=view.findViewById<TextView>(R.id.mstat_count_order)
        val mstat_count_sale=view.findViewById<TextView>(R.id.mstat_count_sale)
        val mstat_count_stocks=view.findViewById<TextView>(R.id.mstat_count_stocks)
        val imageOrder = view.findViewById<ImageView>(R.id.mstats_imageVIew)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        @SuppressLint("SetTextI18n", "ResourceAsColor")

        fun bind(compositeStat: CompositeStat) {
            val zdt = ZonedDateTime.now(ZoneId.systemDefault())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)

            mstat_arcticle_nmId.text= "${compositeStat.nmId} | ${compositeStat.supplierArticle}"
            mstat_subject_category.text= "${compositeStat.category} | ${compositeStat.subject}"
            mstat_bcode.text = compositeStat.barcode
             mstat_size.text = "Размер: ${compositeStat.techSize} "
             mstat_count_order.text = "${compositeStat.countOrder} шт заказано за месяц"
             mstat_count_sale.text = "${compositeStat.countSale} шт выкупили за месяц"
             mstat_count_stocks.text = "${compositeStat.quantity} шт на складах"



            Picasso.get()
                .load(articleLink.getImgUrl1(compositeStat.nmId))
                .resize(100, 150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(imageOrder)



            var ldt = ZonedDateTime.now(ZoneId.systemDefault()).minusDays(7)





        }

    }
}