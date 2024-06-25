package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.CustomStock
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.Stock
import com.example.my_dayle_stats.articleLink
import com.squareup.picasso.Picasso

class CustomStocksAdapter(
    private val onItemClicked:(position: Int) ->Unit,
    val stocks: List<CustomStock>,
    val allStocks: List<Stock>
):RecyclerView.Adapter<CustomStocksAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(parent.inflate (R.layout.items_stocks),onItemClicked)
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        return Holder(View.inflate(parent.context,R.layout.items_stocks,parent),onItemClicked)
//    }

    override fun getItemCount(): Int {
        return stocks.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val stock = stocks[position]
        holder.bind(stock, allStocks)
    }
    class Holder(
        view:View,
        onItemClicked: (position: Int) -> Unit
    ):RecyclerView.ViewHolder(view){

        private val brandTextView:TextView = view.findViewById(R.id.brandView)
        private val categorySubjectTextView:TextView = view.findViewById(R.id.categorySubjectView)
        private val sizeTextView:TextView = view.findViewById(R.id.sizeView)
        private val werehouseTextView:TextView = view.findViewById(R.id.wereHouseView)
        private val costTextView:TextView = view.findViewById(R.id.stockCount)
        private val stockCountView:TextView = view.findViewById(R.id.stockCount)
        private val inWayStockView:TextView = view.findViewById(R.id.inWayStocks)
        private val imageOrder:ImageView = view.findViewById(R.id.imageOrder)

        private val stocksConteiner: LinearLayout = view.findViewById(R.id.stocksConteiner)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "ResourceAsColor")


        fun bind(stock: CustomStock, allStocks: List<Stock>){
            var count = 0
            stocksConteiner.removeAllViews()
            stocksConteiner.visibility=View.GONE

            brandTextView.text ="${stock.brand} | ${stock.nmId}"
            categorySubjectTextView.text= "${stock.category} | ${stock.subject}"
            sizeTextView.text = "Размер: ${stock.techSize}\nАрт.:${stock.supplierArticle}"
            werehouseTextView.textSize = 16.0f
            werehouseTextView.text = "Цена без СПП ${stock.price*(100-stock.discount)/100} руб"
            inWayStockView.text = "К клиенту -> ${stock.inWayToClient}; На склад ${stock.inWayFromClient} <-"
            costTextView.text = "${stock.price}руб скидка ${stock.discount}% цена со скидкой: ${stock.price*(100-stock.discount)/100} руб"
            stockCountView.text ="На складах ${stock.quantity}шт"

            Picasso.get()
                .load(articleLink.getImgUrl1(stock.nmId))
                .resize(100,150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(imageOrder)


            for (str : String in stock.werehouseArray) {
                val parentContainer = LinearLayout(stocksConteiner.context)
                parentContainer.layoutParams=LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                parentContainer.orientation=LinearLayout.VERTICAL
                if(count%2==0){
                    parentContainer.setBackgroundColor( ContextCompat.getColor(stocksConteiner.context,
                        R.color.md_theme_light_onSecondary
                    ))
                }
                parentContainer.setPadding(0,10,0,10)


                val childContainer = LinearLayout(stocksConteiner.context)
                childContainer.layoutParams=LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                childContainer.orientation=LinearLayout.HORIZONTAL

                val whTxT = TextView(stocksConteiner.context)
                whTxT.layoutParams=LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                whTxT.gravity=Gravity.CENTER
                whTxT.textSize=19f
                whTxT.setTextColor(Color.BLACK)

                val quantityTV = TextView(stocksConteiner.context)
                quantityTV.layoutParams=LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1f)
                quantityTV.gravity=Gravity.CENTER
                quantityTV.setTextColor(Color.BLACK)
                quantityTV.textSize=16f
                val toClientTV = TextView(stocksConteiner.context)
                toClientTV.layoutParams=LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1f)
                toClientTV.gravity=Gravity.CENTER
                toClientTV.setTextColor(Color.BLACK)
                toClientTV.textSize=16f
                val fromClientTV = TextView(stocksConteiner.context)
                fromClientTV.layoutParams=LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1f)
                fromClientTV.gravity=Gravity.CENTER
                fromClientTV.setTextColor(Color.BLACK)
                fromClientTV.textSize=16f

                childContainer.addView(quantityTV,0)
                childContainer.addView(toClientTV,1)
                childContainer.addView(fromClientTV,2)

                parentContainer.addView(whTxT)
                parentContainer.addView(childContainer)


                val qntity =allStocks.stream().filter{it.warehouseName == str && it.barcode == stock.barcode}.findFirst().get().quantity
                val inWayTo =allStocks.stream().filter{it.warehouseName == str && it.barcode == stock.barcode}.findFirst().get().inWayToClient
                val inWayFrom =allStocks.stream().filter{it.warehouseName == str && it.barcode == stock.barcode}.findFirst().get().inWayFromClient
//
                if (qntity==0){
                    whTxT.setTextColor(Color.RED)
                    quantityTV.setTextColor(Color.RED)
                }
//                if (qntity in 1..10){
//                    whTxT.setTextColor(ContextCompat.getColor(parentContainer.context,R.color.md_theme_light_onError))
//                    quantityTV.setTextColor(ContextCompat.getColor(parentContainer.context,R.color.md_theme_light_onError))
//                }
//                if (qntity>=40){
//                    whTxT.setTextColor(ContextCompat.getColor(parentContainer.context,R.color.md_theme_dark_onTertiary))
//                    quantityTV.setTextColor(ContextCompat.getColor(parentContainer.context,R.color.md_theme_dark_onTertiary))
//                }

                whTxT.text = str
                quantityTV.text = "Доступно \n$qntity шт"
                toClientTV.text = "Едет к клиенту \n-> $inWayTo шт"
                fromClientTV.text = "Едет на склад \n<- $inWayFrom шт"
                stocksConteiner.addView(parentContainer)
                count++
            }


        }


    }
}