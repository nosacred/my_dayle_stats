package com.example.my_dayle_stats

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
import java.time.LocalDateTime
import java.util.Locale

class abcFragmentA : Fragment() {
lateinit var recycle: RecyclerView
    private val dataModel : DataModel by activityViewModels()
    lateinit var saleList:List<Sale>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abc_a, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tv = view.requireViewById<TextView>(R.id.abcTextView)

        dataModel.salesVM.observe(activity as LifecycleOwner) {
            saleList = dataModel.salesVM.value.orEmpty()
            val abcList = getAbcAList(saleList)
            val sum = abcList.map { it.totalSaleForPay }.sum()
            tv.text="Выкупили ${abcList.count()} артикулов на ${String.format(Locale.CANADA_FRENCH,"%,d",sum)} руб"
            recycle=view.requireViewById(R.id.abc_recycleView)
            recycle.adapter = AbcAdapter(abcList)
            recycle.layoutManager = LinearLayoutManager(this.context)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = abcFragmentA()
    }
}