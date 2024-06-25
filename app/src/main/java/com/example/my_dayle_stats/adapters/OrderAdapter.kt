package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.LineChartXAxisValueFormatter
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.articleLink
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

class OrderAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    val ordersToday: List<Order>,
    val ordersAll: List<Order>
) : RecyclerView.Adapter<OrderAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(
            parent.inflate(R.layout.items_orders),
            onItemClicked
        )
    }

    override fun getItemCount(): Int {
        return ordersToday.size
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val order = ordersToday[position]
        holder.bind(order, ordersToday, allOrders = ordersAll)
    }

    class Holder(
        view: View,
        onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val dateTextView: TextView = view.findViewById(R.id.dateTxtView)
        private val brandTextView: TextView = view.findViewById(R.id.brandView)
        private val categorySubjectTextView: TextView = view.findViewById(R.id.categorySubjectView)
        private val sizeTextView: TextView = view.findViewById(R.id.sizeView)
        private val werehouseTextView: TextView = view.findViewById(R.id.wereHouseView)
        private val costTextView: TextView = view.findViewById(R.id.costView)
        private val totalOrderTodayTextView: TextView = view.findViewById(R.id.totalOrdersToday)
        private val imageOrder: ImageView = view.findViewById(R.id.imageOrder)

        private val dopItemLiner: LinearLayout = view.findViewById(R.id.dropDownStats)
        private val yesterDayOrderView: TextView = view.findViewById(R.id.yesterdayOrders)
        private val sevenDaysOrderView: TextView = view.findViewById(R.id.sevenDaysOrders)
        private val monthOrdersView: TextView = view.findViewById(R.id.monthOrders)
        private val graph: BarChart = view.findViewById(R.id.graph)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        @SuppressLint("SetTextI18n", "ResourceAsColor")

        fun bind(order: Order, ordersToday: List<Order>, allOrders: List<Order>) {
            val zdt = ZonedDateTime.now(ZoneId.systemDefault())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)

            dopItemLiner.visibility = View.GONE

            val ordersMinus1day = allOrders.filter { it.barcode == order.barcode }
                .filter { it.date.dayOfYear == LocalDateTime.now().minusDays(1).dayOfYear }
            val weekOrders = allOrders.filter { it.barcode.equals(order.barcode) }
                .filter { it.date.isAfter(zdt.minusDays(6)) }

            val monthOrders = allOrders.filter { it.barcode.equals(order.barcode) }
                .filter { it.date.isAfter(zdt.minusDays(30)) }
            val formatter = DateTimeFormatter.ofPattern("HH:MM dd MMMM yyyy")

            dateTextView.text =
                "Заказ №${ordersToday.size - ordersToday.indexOf(order)} от ${
                    order.date.format(
                        formatter
                    )
                } "
            brandTextView.text = "${order.brand} | ${order.nmId}"
            categorySubjectTextView.text = "Предмет: ${order.subject}"
            sizeTextView.text = "Размер: ${order.techSize}\nАрт.:${order.supplierArticle}"
            werehouseTextView.text = "${order.warehouseName} -> ${order.regionName}"
            costTextView.text = order.priceWithDisc.toString() + " руб СПП:${order.spp}%"
            var todayCount =
                ordersToday.stream().filter { o -> o.barcode == order.barcode && !order.isCancel }
                    .count()
            var todaySum =
                ordersToday.stream().filter { o -> o.barcode == order.barcode && !order.isCancel }
                    .mapToInt { it.finishedPrice.toInt() }.sum()
            var todayCancelCount = ordersToday.stream()
                .filter { o -> o.barcode == order.barcode && order.isCancel }
                .count()
            totalOrderTodayTextView.text = "Сегодня заказано $todayCount шт на $todaySum руб \n" +
                    "Отменено $todayCancelCount штук(и)"
//            orderID.text = order.srid
//            orderID.textSize = 8f

            Picasso.get()
                .load(articleLink.getImgUrl1(order.nmId))
                .resize(100, 150)
//                .placeholder(R.drawable.ic_crop_portrait)
                .into(imageOrder)

            if (order.isCancel) {
                dateTextView.setBackgroundColor(Color.parseColor("#E66761"))
            } else dateTextView.setBackgroundColor(Color.parseColor("#7CB342"))

            val yestDayOrderSum =
                ordersMinus1day.stream().mapToInt { it.finishedPrice.toInt() }.sum()
            val sevenDayOrderSum = weekOrders.stream().mapToInt { it.finishedPrice.toInt() }.sum()
            val monthOrderSum = monthOrders.stream().mapToInt { it.finishedPrice.toInt() }.sum()
            yesterDayOrderView.text = "Вчера заказано ${ordersMinus1day.size} шт на ${
                String.format(
                    Locale.CANADA_FRENCH, "%,d", yestDayOrderSum
                )
            } руб"
            sevenDaysOrderView.text = "За 7 дней заказано ${weekOrders.size} шт на ${
                String.format(
                    Locale.CANADA_FRENCH,
                    "%,d",
                    sevenDayOrderSum
                )
            } руб"
            monthOrdersView.text = "За 30 дней заказано ${monthOrders.size} шт на ${
                String.format(
                    Locale.CANADA_FRENCH,
                    "%,d",
                    monthOrderSum
                )
            } руб"

            var ldt = ZonedDateTime.now(ZoneId.systemDefault()).minusDays(6)


            val arrayDataPoint = ArrayList<BarEntry>()
            val arrayCancelDataPoint = ArrayList<BarEntry>()
            graph.xAxis.valueFormatter = LineChartXAxisValueFormatter()
            graph.xAxis.position = XAxis.XAxisPosition.BOTTOM

            while (ldt.dayOfYear <= LocalDateTime.now(ZoneId.systemDefault()).dayOfYear) {
                val count = allOrders.filter { it.barcode == order.barcode }
                    .count { it.date.dayOfYear == ldt.dayOfYear && it.date.dayOfMonth == ldt.dayOfMonth }
                val dbl = TimeUnit.SECONDS.toDays(ldt.toInstant().epochSecond)

                val cancelCount = allOrders.filter { it.barcode == order.barcode }
                    .filter { it.date.dayOfYear == ldt.dayOfYear }.count { it.isCancel }
                val dataPoint = BarEntry(dbl.toFloat(), count.toFloat())
                val cancelDataPoint = BarEntry(dbl.toFloat(), cancelCount.toFloat())
                arrayDataPoint.add(dataPoint)
                arrayCancelDataPoint.add(cancelDataPoint)
                ldt = ldt.plusDays(1)
            }

            val dataset = BarDataSet(arrayDataPoint.toMutableList(), "Заказы")
            val dataset2 = BarDataSet(arrayCancelDataPoint.toMutableList(), "Отмены")
            dataset.color = Color.GREEN
            dataset2.color = Color.RED

            val datasets = ArrayList<IBarDataSet>()
            datasets.add(dataset)
            datasets.add(dataset2)

            val data = BarData(datasets)
            graph.data = data
            graph.invalidate()
            graph.animateY(500)

        }

    }
}