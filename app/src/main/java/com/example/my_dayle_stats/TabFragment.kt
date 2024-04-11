package com.example.my_dayle_stats

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.android_wb.SaleFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabFragment : Fragment(R.layout.fragment_tab) {

    val orderFragment = OrderFragment.newInstance()
    val saleFragment = SaleFragment.newInstance()
    val stockFragment = StockFragment.newInstance()
    val listfrag = mutableListOf<Fragment>(
        orderFragment,
        saleFragment,
        stockFragment
    )
    private val dataModel :DataModel by activityViewModels()

    lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout=view.requireViewById(R.id.tabContainer)
        val date = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val apiKey =
            "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTcxNzU3NjE4NSwiaWQiOiIxMDMzNTczNS0wNTViLTQ3NWQtOGQ2OC1mMTA1ZDdkMWFkYTgiLCJpaWQiOjkxNDU0MTMsIm9pZCI6MTM1NTI3LCJzIjozMiwic2lkIjoiZTI2NWVlZmEtYjY1My00OTlkLThmMTYtMjAzYzJmNjMwNGQ1IiwidWlkIjo5MTQ1NDEzfQ.omZjk0y5UhP4lDTeY6CP47acpmQY6cD8QpPZRF7vJ0f1KfYz6JEo7ahEGKfVYF2DIFzqmMcc_Rro5RUrpN1YAw"


        dataModel.ordersVM.observe(viewLifecycleOwner) {
            val bg = tabLayout.getTabAt(0)?.orCreateBadge
            if (bg != null) {
                bg.number =
                    it.filter { order -> order.date.dayOfYear == LocalDateTime.now().dayOfYear }
                        .count()
            }
        }
        dataModel.salesVM.observe(viewLifecycleOwner) {
            val bg = tabLayout.getTabAt(1)?.orCreateBadge
            if (bg != null) {
                bg.number = it.filter { sale -> sale.date.dayOfYear == LocalDateTime.now().dayOfYear}.count()
            }
        }
        dataModel.stocksVM.observe(viewLifecycleOwner) { it ->
            val bg = tabLayout.getTabAt(2)?.orCreateBadge
            if (bg != null) {
                bg.number = it.stream().mapToInt { it.quantity }.sum()
            }

        }

        val viewPager2 = view.requireViewById<ViewPager2>(R.id.recycleContainer)
        val vPadapter = VPadapter(this.requireActivity(), listfrag)
//        viewPager2.setPageTransformer(DepthTransformation())
        viewPager2.adapter = vPadapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = getText(R.string.orders)
//                    tab.icon = this.context?.let {
//                        ContextCompat.getDrawable(
//                            it,
//                            R.drawable.ic_orders
//                        )
//                    }
                }
                1 -> {
                    tab.text = getText(R.string.sales)
//                    tab.icon = this.context?.let {
//                        ContextCompat.getDrawable(
//                            it,
//                            R.drawable.ic_sales
//                        )
//                    }
                }
                2 -> {
                    tab.text = getText(R.string.stocks)
//                    tab.icon = this.context?.let {
//                        ContextCompat.getDrawable(
//                            it,
//                            R.drawable.ic_stocks
//                        )
//                    }
                }
            }
        }.attach()

    }

    companion object {

        @JvmStatic
        fun newInstance() = TabFragment()
    }
}