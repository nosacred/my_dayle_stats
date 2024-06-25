package com.example.my_dayle_stats.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.OrderPerBarcode
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.adapters.OrderAdapter
import com.example.my_dayle_stats.adapters.WHAdapter
import com.example.my_dayle_stats.adapters.getOrdersPerBarcode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class WereHouse() : Fragment(R.layout.fragment_were_house) {
    var wh_name=""
    var oList = emptyList<OrderPerBarcode>()
    var countOrders = 0
    var orderList = emptyList<Order>()
    private val dataModel: DataModel by activityViewModels()
    private lateinit var recycle: RecyclerView


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycle = view.requireViewById(R.id.wereHouse_recycleView)
        dataModel.ordersVM.observe(activity as LifecycleOwner) {
            orderList = dataModel.ordersVM.value.orEmpty()
            oList = getOrdersPerBarcode(wh_name,orderList)
            countOrders = oList.stream().mapToInt { it.count }.sum()
            recycle.adapter = WHAdapter(
                oList
            )
            recycle.layoutManager = LinearLayoutManager(this.context)
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(whName : String) :WereHouse {
            val wh = WereHouse()
            wh.wh_name = whName
            return wh
        }

    }
}