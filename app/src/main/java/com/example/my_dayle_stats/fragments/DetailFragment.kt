package com.example.my_dayle_stats.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.fromHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.ValueFormatterPieChart
import com.example.my_dayle_stats.articleLink
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.squareup.picasso.Picasso
import java.util.Locale


class DetailFragment : Fragment() {
    private val dataModel : DataModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bcode = arguments?.getString("BCODE")
        val orders = dataModel.ordersVM.value.orEmpty()
        val sales = dataModel.salesVM.value.orEmpty()
        val stocks = dataModel.stocksVM.value.orEmpty()

        val nmid = orders.find { it.barcode==bcode }?.nmId

        val df_imageView1: ImageView = view.requireViewById(R.id.df_imageView1)
        val df_imageView2: ImageView = view.requireViewById(R.id.df_imageView2)
        val df_imageView3: ImageView = view.requireViewById(R.id.df_imageView3)

        Picasso.get()
            .load(nmid?.let { articleLink.getImgUrl1(it) })
            .into(df_imageView1)
        Picasso.get()
            .load(nmid?.let { articleLink.getImgUrl2(it) })
            .into(df_imageView2)
        Picasso.get()
            .load(nmid?.let { articleLink.getImgUrl3(it) })
            .into(df_imageView3)

        //Артикул вб
        val df_nmid : TextView = view.requireViewById(R.id.df_nmid)
        df_nmid.text=
//            "<a href=\"${articleLink.getWBLink(nmid.toString())}\">${nmid}</a>"
          fromHtml( "<a href=\"${articleLink.getWBLink(nmid.toString())}\">${nmid}</a>",HtmlCompat.FROM_HTML_MODE_COMPACT)

        //Категория и предмет
        val df_cat_subj : TextView = view.requireViewById(R.id.df_cat_subj)
        df_cat_subj.text = orders.find { it.barcode==bcode }?.category+"\n" +orders.find { it.barcode==bcode }?.subject
        //Кол-во заказов
        val df_orderCount : TextView = view.requireViewById(R.id.df_orderCount)
        val ordersCount = orders.filter { it.barcode==bcode }.filter { !it.isCancel }.count()
        df_orderCount.text = fromHtml("Заказали \n<b>$ordersCount</b> шт",
            HtmlCompat.FROM_HTML_MODE_COMPACT)
        //Сумма заказов
        val df_ordersSum : TextView = view.requireViewById(R.id.df_ordersSum)
        val orderSum = orders.filter { it.barcode==bcode }.filter { !it.isCancel }.map{it.finishedPrice}.sum().toInt()
        df_ordersSum.text = "на сумму: ${String.format(Locale.CANADA_FRENCH,"%,d",orderSum)}"
        //Кол-во отмененных заказов
        val df_ordersCancel : TextView = view.requireViewById(R.id.df_ordersCancel)
        val ordersCancelcount= orders.filter { it.barcode==bcode }.filter { it.isCancel }.count()
        df_ordersCancel.text= fromHtml("Отменено <b>$ordersCancelcount</b> шт",
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        //Процент отказа
        val df_cancelPercent : TextView = view.requireViewById(R.id.df_percent)
        val cancelPercent = (ordersCancelcount.toDouble()/ordersCount.toDouble() *100)
        df_cancelPercent.text = fromHtml("Процент отказа: <b>${String.format("%.2f",cancelPercent)}</b>%",
            HtmlCompat.FROM_HTML_MODE_COMPACT)
        //Кол-во выкупов
        val df_saleCount : TextView = view.requireViewById(R.id.df_saleCount)
        df_saleCount.text = fromHtml("Выкупили <b>${sales.filter { it.barcode==bcode }.filter { it.forPay >0 }.count()}</b> шт.",
            HtmlCompat.FROM_HTML_MODE_COMPACT)
        //Сумма выкупов
        val df_salesSum : TextView = view.requireViewById(R.id.df_salesSum)
        df_salesSum.text = fromHtml( "на <b>${sales.filter { it.barcode==bcode }.filter { it.forPay >0 }.map { it.forPay }.sum().toInt()}</b> руб",
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        //Сумма возвратов
        val df_salesCancelCountSum : TextView = view.requireViewById(R.id.df_salesCancelCountSum)
        df_salesCancelCountSum.text = "Возвратов на\n ${sales.filter { it.barcode==bcode }.filter { it.forPay <0 }.map { it.forPay }.sum().toInt()}"
        //Кол-во на остатках по всем складам
        val df_totalStockCount : TextView = view.requireViewById(R.id.df_totalStockCount)
        df_totalStockCount.text ="На складах ${stocks.filter { it.barcode==bcode }.map { it.quantity }.sum()} шт"
        //Едет к клиенту
        val df_toClient : TextView = view.requireViewById(R.id.df_toClient)
        df_toClient.text="К клиенту->  ${stocks.filter { it.barcode==bcode }.map { it.inWayToClient }.sum()} шт"
        //Едет на склад
        val df_toWH : TextView = view.requireViewById(R.id.df_toWH)
        df_toWH.text="На склад<- ${stocks.filter { it.barcode==bcode }.map { it.inWayFromClient }.sum()} шт"


        //График заказов по складам
        val pieChartOrders = view.requireViewById<PieChart>(R.id.df_graph_orderWH)
        pieChartOrders.setDrawHoleEnabled(true);
        pieChartOrders.setUsePercentValues(true);
        pieChartOrders.setEntryLabelTextSize(12f);
        pieChartOrders.setEntryLabelColor(Color.BLACK);
        pieChartOrders.setCenterText("Заказы ${
            orders.filter { it.barcode == bcode }.count { !it.isCancel }
        } шт");
        pieChartOrders.setCenterTextSize(20f);
        pieChartOrders.getDescription().setEnabled(false);

        val entriesOrders = ArrayList<PieEntry>()
        val whOrderSet = HashSet<String>()
        orders.filter { it.barcode==bcode }.map { it.warehouseName }.toCollection(whOrderSet)
        var anotherwh = 0
        whOrderSet.sorted()

        for(wh in whOrderSet){
            val count = orders.filter { it.barcode==bcode }.filter { it.warehouseName==wh }.count()
            if(count <10 && ordersCount > 30){
                anotherwh += count
                continue
            }
            entriesOrders.add(PieEntry(count.toFloat(), wh))
        }
        if (anotherwh >0) {
            entriesOrders.add(PieEntry(anotherwh.toFloat(),"Другие склады"))
        }

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val dataSetOrders = PieDataSet(entriesOrders, "Заказы со складов")
        dataSetOrders.colors = colors

        val dataOrders = PieData(dataSetOrders)
        dataOrders.setDrawValues(true)
        dataOrders.setValueFormatter(PercentFormatter(pieChartOrders))
        dataOrders.setValueTextSize(12f)
        dataOrders.setValueTextColor(Color.BLACK)
        pieChartOrders.data = dataOrders
        pieChartOrders.animateY(1400, Easing.EaseInOutQuad)
        val switch: Switch = view.requireViewById(R.id.df_switch_ordersWH)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switch.text = "Штуки"
                pieChartOrders.setUsePercentValues(false)
                dataOrders.setValueFormatter(ValueFormatterPieChart())
                pieChartOrders.invalidate()
            }
            else {
                dataOrders.setValueFormatter(PercentFormatter(pieChartOrders))
                pieChartOrders.setUsePercentValues(true)
                switch.text = "Проценты %"
                pieChartOrders.invalidate()
            }
        }


        //Остатки по складам график
        val pieChartStocks = view.requireViewById<PieChart>(R.id.df_graph_stockWH)
        pieChartStocks.setDrawHoleEnabled(true)
        pieChartStocks.setUsePercentValues(false)
        pieChartStocks.setEntryLabelTextSize(12f)
        pieChartStocks.setEntryLabelColor(Color.BLACK);
        pieChartStocks.setCenterText("Остатки \n${
            stocks.filter { it.barcode == bcode }.sumOf { it.quantity }
        } шт");
        pieChartStocks.setCenterTextSize(20f);
        pieChartStocks.getDescription().setEnabled(false)

        val entriesStocks = ArrayList<PieEntry>()
        var stockCount = 0
        for(wh in whOrderSet){
            val st =stocks.filter { it.barcode==bcode }.filter { it.warehouseName==wh }
            if(st.isNotEmpty()){
                if (st[0].quantity >= 5 || (stocks.filter { it.barcode==bcode }.map { it.quantity }.sum() <=10))
                entriesStocks.add(PieEntry(st[0].quantity.toFloat(), wh))
                else{
                    stockCount += st[0].quantity
                }
            }
        }
        if (stockCount > 0) entriesStocks.add(PieEntry(stockCount.toFloat(), "Другие склады"))
        val dataSetStocks = PieDataSet(entriesStocks, "Остатки")
        dataSetStocks.colors = colors
        dataSetStocks.setValueFormatter(ValueFormatterPieChart())
        val dataStocks = PieData(dataSetStocks)
        dataSetStocks.setDrawValues(true)
        dataSetStocks.setValueTextSize(12f)
        dataSetStocks.setValueTextColor(Color.BLACK)
        pieChartStocks.data = dataStocks
        pieChartStocks.invalidate()
        pieChartStocks.animateY(1400, Easing.EaseInOutQuad)

        //График заказов в регионы
        val pieChartRegion = view.requireViewById<PieChart>(R.id.df_graph_regionOrders)
        pieChartRegion.setDrawHoleEnabled(true)
        pieChartRegion.setUsePercentValues(true)
        pieChartRegion.setEntryLabelTextSize(12f)
        pieChartRegion.setEntryLabelColor(Color.BLACK);
        pieChartRegion.setCenterText("Заказы\n${
            orders.filter { it.barcode == bcode }.count()
        } шт");
        pieChartRegion.setCenterTextSize(20f);
        pieChartRegion.getDescription().setEnabled(false)

        val entriesRegions = ArrayList<PieEntry>()
        val regionSet = HashSet<String>()
        orders.filter { it.barcode==bcode }.map { it.oblastOkrugName }.toCollection(regionSet)

        for(region in regionSet){
            val reg =orders.filter { it.barcode==bcode }.filter { it.oblastOkrugName==region }
            if(reg.isNotEmpty()){
                    entriesRegions.add(PieEntry(reg.size.toFloat(), region))
            }
        }
        val dataSetRegions = PieDataSet(entriesRegions, "Регионы")
        dataSetRegions.colors = colors
        dataSetRegions.setValueFormatter(ValueFormatterPieChart())
        val dataRegion = PieData(dataSetRegions)
        dataRegion.setValueFormatter(PercentFormatter(pieChartRegion))
        dataSetRegions.setDrawValues(true)
        dataSetRegions.setValueTextSize(12f)
        dataSetRegions.setValueTextColor(Color.BLACK)
        pieChartRegion.data = dataRegion
        pieChartRegion.invalidate()
        pieChartRegion.animateY(1400, Easing.EaseInOutQuad)

        val switchReg: Switch = view.requireViewById(R.id.df_switch_ordersRegions)
        switchReg.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchReg.text = "Штуки"
                pieChartRegion.setUsePercentValues(false)
                dataRegion.setValueFormatter(ValueFormatterPieChart())
                pieChartRegion.invalidate()
            }
            else {
                dataRegion.setValueFormatter(PercentFormatter(pieChartRegion))
                pieChartRegion.setUsePercentValues(true)
                switchReg.text = "Проценты %"
                pieChartRegion.invalidate()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}