package com.example.my_dayle_stats
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale


class MonthStatsFragment : Fragment() {
    private val dataModel : DataModel by activityViewModels()
    private lateinit var recycle : RecyclerView
    private lateinit var tv : TextView
    var orderList = emptyList<Order>()
    var saleList = emptyList<Sale>()
    var stockList = emptyList<Stock>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_stats, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv = view.requireViewById(R.id.infoMonthStatsTV)
        recycle = view.requireViewById(R.id.monthStatsRecycleView)
        val fab = view.requireViewById<FloatingActionButton>(R.id.fabScrollUp)
        dataModel.ordersVM.observe(activity as LifecycleOwner) {
            tv = view.requireViewById(R.id.infoMonthStatsTV)
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList= dataModel.salesVM.value.orEmpty()
            stockList= dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter({position->showDopOrderViews(position) },
                getCompostiteStatList(orderList,saleList,stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }

        dataModel.salesVM.observe(activity as LifecycleOwner) {
            tv = view.requireViewById(R.id.infoMonthStatsTV)
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList= dataModel.salesVM.value.orEmpty()
            stockList= dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter({position->showDopOrderViews(position) },
                getCompostiteStatList(orderList,saleList,stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }

        dataModel.stocksVM.observe(activity as LifecycleOwner) {
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList= dataModel.salesVM.value.orEmpty()
            stockList= dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter({position->showDopOrderViews(position) },
                getCompostiteStatList(orderList,saleList,stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }
        val monthOrderMoney = orderList.stream().mapToInt { it.priceWithDisc.toInt() }.sum()
        val monthSaleMoney = saleList.stream().mapToInt { it.forPay.toInt() }.sum()
        val monthBackSaleMoney = saleList.filter { it.forPay <0 }.map { it.forPay }.map { it.toInt() }.sum()

        tv.text = "За месяц заказано: ${orderList.size}шт на ${String.format(Locale.CANADA_FRENCH,"%,d",monthOrderMoney)}руб\n" +
                "из них отменено: ${orderList.filter { it.isCancel }.count()} шт \n" +
                "Выкупили ${saleList.size}шт на сумму: ${String.format(Locale.CANADA_FRENCH,"%,d",monthSaleMoney)} руб \n" +
                "Возвращено товаров: ${saleList.filter { it.forPay <0 }.count()}шт на сумму: ${String.format(Locale.CANADA_FRENCH,"%,d",monthBackSaleMoney)} руб"


        fab.setOnClickListener {
            recycle.scrollToPosition(0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showDopOrderViews(position :Int){
        val view1 = view?.requireViewById<RecyclerView>(R.id.monthStatsRecycleView)
        val dopView = view1?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.mstat_bcode)?.text.toString()
        val bundle =Bundle()
        bundle.putString("BCODE",dopView)
        findNavController().navigate(R.id.action_monthStatsFragment_to_detailFragment,bundle)
        Toast.makeText(this.context, dopView, Toast.LENGTH_SHORT).show()

    }

    companion object {

        @JvmStatic
        fun newInstance() = MonthStatsFragment()
    }
}