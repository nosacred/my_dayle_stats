package com.example.my_dayle_stats.fragments

import android.annotation.SuppressLint
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
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.Sale
import com.example.my_dayle_stats.adapters.SaleAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class SaleFragment : Fragment(R.layout.fragment_sale) {
    var saleList = emptyList<Sale>()
    private val dataModel : DataModel by activityViewModels()
    private lateinit var recycle : RecyclerView
    private lateinit var tv : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycle = view.requireViewById(R.id.saleRecycleView)
        tv=view.requireViewById(R.id.infoSalesTV)
        val fab = view.requireViewById<FloatingActionButton>(R.id.fabScrollDownSale)
        dataModel.salesVM.observe(activity as LifecycleOwner) {
            saleList = dataModel.salesVM.value.orEmpty()
            tv.text=setSalesInfo(saleList.filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear })
            recycle.adapter = SaleAdapter(
                saleList.filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear })
            recycle.layoutManager = LinearLayoutManager(this.context)
        }

        fab.setOnClickListener {
            recycle.scrollToPosition(0)
        }

    }

    private fun setSalesInfo(sales: List<Sale>) : String{
        var sum = 0.0
        var negativeSum = 0.0
        for(sale: Sale in sales){
            if(sale.forPay>0)
                sum += sale.forPay
        }

        for(sale: Sale in sales){
            if(sale.forPay<0)
                negativeSum += sale.forPay
        }
        return "Выкупов: ${sales.filter { sale -> sale.forPay >0 }.count()}шт на сумму ${sum.toInt()} руб \n" +
                "Возвратов: ${sales.filter { sale -> sale.forPay <0 }.count()} на ${negativeSum.toInt()} руб"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun upDate(listSale: List<Sale>){
        recycle.adapter = SaleAdapter(
            listSale.filter { o -> o.date.dayOfYear == LocalDateTime.now().dayOfYear })
        recycle.layoutManager = LinearLayoutManager(this.context)
    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SaleFragment()

    }
}