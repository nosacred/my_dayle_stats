package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.AbClass
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.articleLink
import com.squareup.picasso.Picasso

class AbcAdapter(private val abc_item: List<AbClass>) : RecyclerView.Adapter<AbcAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            parent.inflate(R.layout.items_abc),
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val abc = abc_item[position]
        holder.bind(abc)
    }

    override fun getItemCount(): Int {
        return abc_item.size
    }

    class Holder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_imageVIew = view.requireViewById<ImageView>(R.id.i_abc_imageVIew)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_arcticle_nmId = view.requireViewById<TextView>(R.id.i_abc_arcticle_nmId)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_subject_category =
            view.requireViewById<TextView>(R.id.i_abc_subject_category)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_bcode = view.requireViewById<TextView>(R.id.i_abc_bcode)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_size = view.requireViewById<TextView>(R.id.i_abc_size)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_count_sale = view.requireViewById<TextView>(R.id.i_abc_count_sale)

        @RequiresApi(Build.VERSION_CODES.P)
        private val i_abc_count_back = view.requireViewById<TextView>(R.id.i_abc_count_back)


        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(abc: AbClass) {
            i_abc_arcticle_nmId.text = "${abc.brand} | ${abc.nmId}"
            i_abc_subject_category.text = "${abc.category} ${abc.subject}"
            i_abc_bcode.text = "${abc.barcode}"
            i_abc_size.text = "Размер ${abc.techSize}"
            i_abc_count_back.text = "Возвратов ${abc.cancelCountSale} шт на  ${abc.moneyBack} руб"
            i_abc_count_sale.text = "Выкупили  ${abc.countSale} шт на  ${abc.totalSaleForPay} руб"

            Picasso.get()
                .load(articleLink.getImgUrl1(abc.nmId.toInt()))
                .resize(100, 150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(i_abc_imageVIew)

        }
    }
}