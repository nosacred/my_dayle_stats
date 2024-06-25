package com.example.my_dayle_stats.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.adapters.OrderAdapter
import com.example.my_dayle_stats.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.util.Locale

@SuppressLint("NotifyDataSetChanged")
class OrderFragment : Fragment(R.layout.fragment_order) {
    var orderList = emptyList<Order>()
    private val dataModel: DataModel by activityViewModels()
    private lateinit var recycle: RecyclerView
    private lateinit var tv: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycle = view.requireViewById(R.id.orderRecycleView)
        val fab = view.requireViewById<FloatingActionButton>(R.id.fabScrollDownOrders)
        dataModel.ordersVM.observe(activity as LifecycleOwner) {
            tv = view.requireViewById(R.id.infoOrdersTV)
            orderList = dataModel.ordersVM.value.orEmpty()
            tv.text =
                setOrdersInfo(orderList.filter { o-> o.date.dayOfMonth == LocalDateTime.now().dayOfMonth }
                    .filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear })

            recycle.adapter = OrderAdapter({ position -> showDopOrderViews(position) },
                orderList.filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear }
                    .sortedBy { it.date }.reversed(),
                orderList
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }
        fab.setOnClickListener {
            recycle.scrollToPosition(0)
        }

    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun showDopOrderViews(position: Int) {
        val view1 = view?.requireViewById<RecyclerView>(R.id.orderRecycleView)
        val dopView =
            view1?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<LinearLayout>(
                R.id.dropDownStats
            )

        if (dopView?.visibility == View.GONE) {
            dopView.visibility = View.VISIBLE
            view1.scrollTo(0, dopView.bottom)
        } else {
            dopView?.visibility = View.GONE
        }
    }

    private fun setOrdersInfo(orders: List<Order>): String {
        var sum = 0.0
        for (order: Order in orders) {
            if (!order.isCancel)
                sum += order.priceWithDisc
        }
        return "Заказов: ${orders.size - orders.filter { order -> order.isCancel }.count()}шт на ${
            String.format(
                Locale.CANADA_FRENCH, "%,d", sum.toInt()
            )
        } руб \nОтказов: ${orders.filter { order -> order.isCancel }.count()}шт"
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun upDate(listOrder: List<Order>) {
        recycle.adapter = OrderAdapter({ position -> showDopOrderViews(position) },
            listOrder.filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear }
                .filter { o-> o.date.dayOfMonth == LocalDateTime.now().dayOfMonth }
                .sortedBy { it.date }.reversed(),
            orderList
        )
        recycle.layoutManager = LinearLayoutManager(this.context)
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = OrderFragment()

    }

}