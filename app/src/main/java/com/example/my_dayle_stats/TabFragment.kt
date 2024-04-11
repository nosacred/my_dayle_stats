package com.example.my_dayle_stats

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.android_wb.SaleFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime

class TabFragment : Fragment(R.layout.fragment_tab) {

    val orderFragment = OrderFragment.newInstance()
    val saleFragment = SaleFragment.newInstance()
    val stockFragment = StockFragment.newInstance()
    val listfrag = mutableListOf<Fragment>(
        orderFragment,
        saleFragment,
        stockFragment
    )
    private val dataModel: DataModel by activityViewModels()

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
        tabLayout = view.requireViewById(R.id.tabContainer)

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
                bg.number =
                    it.filter { sale -> sale.date.dayOfYear == LocalDateTime.now().dayOfYear }
                        .count()
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