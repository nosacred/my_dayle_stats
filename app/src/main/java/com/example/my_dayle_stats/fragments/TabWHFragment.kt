package com.example.my_dayle_stats.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.VPadapter
import com.example.my_dayle_stats.adapters.sortWH
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Comparator

class TabWHFragment : Fragment(R.layout.fragment_tab) {

    private val dataModel: DataModel by activityViewModels()

    lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = view.requireViewById(R.id.tabContainer)
        tabLayout.tabMode = TabLayout.MODE_AUTO
        tabLayout.setTabTextColors(R.color.md_theme_dark_error,R.color.md_theme_dark_inversePrimary)
        tabLayout.setSelectedTabIndicatorColor(R.color.md_theme_dark_inversePrimary)




        var whNamesList = dataModel.ordersVM.value?.filter {
            it.date.dayOfYear == ZonedDateTime.now().dayOfYear
        }?.map { it.warehouseName }?.toHashSet().orEmpty()

        val whNamesFragmentsList = ArrayList<WereHouse>()

        for (it in whNamesList) {
            val whInst = WereHouse.newInstance(it)
            whNamesFragmentsList.add( whInst)
        }


        val viewPager2 = view.requireViewById<ViewPager2>(R.id.recycleContainer)
        val vPadapter = VPadapter(this.requireActivity(), whNamesFragmentsList)
//        viewPager2.setPageTransformer(DepthTransformation())
        viewPager2.adapter = vPadapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            val wh = whNamesFragmentsList[pos]
            Log.d("tab ","tab ${wh.wh_name} pos $pos")

            tab.text = wh.wh_name
            tab.orCreateBadge.backgroundColor=R.color.md_theme_dark_outlineVariant

            dataModel.ordersVM.observe(viewLifecycleOwner) { orders ->
                tab.orCreateBadge.number =
                    orders.filter { order -> order.date.dayOfYear == LocalDateTime.now().dayOfYear }
                        .count { it.warehouseName == wh.wh_name }
                wh.countOrders = tab.orCreateBadge.number
            }

        }.attach()

    }

    companion object {

        @JvmStatic
        fun newInstance() = TabWHFragment()
    }
}