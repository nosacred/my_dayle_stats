package com.example.my_dayle_stats.fragments

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.adapters.MonthStatsAdapter
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.Sale
import com.example.my_dayle_stats.Stock
import com.example.my_dayle_stats.adapters.getCompostiteStatList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale


class MonthStatsFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    private lateinit var recycle: RecyclerView
    private lateinit var tv: TextView
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


    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv = view.requireViewById(R.id.infoMonthStatsTV)
        recycle = view.requireViewById(R.id.monthStatsRecycleView)
        val fab = view.requireViewById<FloatingActionButton>(R.id.fabScrollUp)
        dataModel.ordersVM.observe(activity as LifecycleOwner) {
            tv = view.requireViewById(R.id.infoMonthStatsTV)
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList = dataModel.salesVM.value.orEmpty()
            stockList = dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter(
                { position -> showDopOrderViews(position) },
                getCompostiteStatList(orderList, saleList, stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }

        dataModel.salesVM.observe(activity as LifecycleOwner) {
            tv = view.requireViewById(R.id.infoMonthStatsTV)
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList = dataModel.salesVM.value.orEmpty()
            stockList = dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter(
                { position -> showDopOrderViews(position) },
                getCompostiteStatList(orderList, saleList, stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }

        dataModel.stocksVM.observe(activity as LifecycleOwner) {
            orderList = dataModel.ordersVM.value.orEmpty()
            saleList = dataModel.salesVM.value.orEmpty()
            stockList = dataModel.stocksVM.value.orEmpty()

            recycle.adapter = MonthStatsAdapter(
                { position -> showDopOrderViews(position) },
                getCompostiteStatList(orderList, saleList, stockList)
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }
        val monthOrderMoney = orderList.stream().mapToInt { it.priceWithDisc.toInt() }.sum()
        val monthSaleMoney = saleList.stream().mapToInt { it.forPay.toInt() }.sum()
        val monthBackSaleMoney =
            saleList.filter { it.forPay < 0 }.map { it.forPay }.map { it.toInt() }.sum()

        val monthCardView =
            requireView().findViewById<com.google.android.material.card.MaterialCardView>(
                R.id.monthCardView
            )
        val monthImageButton = requireView().findViewById<ImageButton>(R.id.monthImageButton)
        val layoutParams = monthCardView.layoutParams
        val heightLP = monthCardView.height
        Log.d("Base height", "${heightLP}")
        monthImageButton.setOnClickListener {
            if (monthCardView.layoutParams.height != LayoutParams.WRAP_CONTENT) {
                monthCardView.layoutParams =
                    layoutParams.apply { height = LayoutParams.WRAP_CONTENT }
                monthImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up))
                Log.d("height UP", "${monthCardView.height}")
            } else {
                monthCardView.layoutParams = layoutParams.apply { height = 110 }
                Log.d("height DOWN", "${monthCardView.height}")
                monthImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down))
            }

        }

        tv.text = "За месяц заказано: ${orderList.size}шт\n" +
                "на ${
                    String.format(
                        Locale.CANADA_FRENCH,
                        "%,d",
                        monthOrderMoney
                    )
                }руб\n" +
                "из них отменено: <b>${orderList.filter { it.isCancel }.count()}</b> шт \n" +
                "Выкупили ${saleList.size}шт на сумму: ${
                    String.format(
                        Locale.CANADA_FRENCH,
                        "%,d",
                        monthSaleMoney
                    )
                } руб \n" +
                "Возвращено товаров: ${
                    saleList.filter { it.forPay < 0 }.count()
                }шт\n" +
                "на сумму: ${String.format(Locale.CANADA_FRENCH, "%,d", monthBackSaleMoney)} руб"


        fab.setOnClickListener {
            recycle.scrollToPosition(0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showDopOrderViews(position: Int) {
        val view1 = view?.requireViewById<RecyclerView>(R.id.monthStatsRecycleView)
        val dopView =
            view1?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.mstat_bcode)?.text.toString()
        val bundle = Bundle()
        bundle.putString("BCODE", dopView)
        findNavController().navigate(R.id.action_monthStatsFragment_to_detailFragment, bundle)
        Toast.makeText(this.context, dopView, Toast.LENGTH_SHORT).show()

    }

    companion object {

        @JvmStatic
        fun newInstance() = MonthStatsFragment()
    }
}