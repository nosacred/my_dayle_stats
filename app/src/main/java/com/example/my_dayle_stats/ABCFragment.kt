package com.example.my_dayle_stats

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ABCFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var tabABC: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abc, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabABC = view.requireViewById(R.id.tabABC)
        val listfrag = listOf(
            abcFragmentA.newInstance(),
            abcFragmentB.newInstance(),
            abcFragmentC.newInstance()
        )

        val viewPager2 = view.requireViewById<ViewPager2>(R.id.abcContainer)
        val vPadapter = VPadapter(this.requireActivity(), listfrag)
//        viewPager2.setPageTransformer(DepthTransformation())
        viewPager2.adapter = vPadapter
        TabLayoutMediator(tabABC, viewPager2) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "A"
                    tab.icon = this.context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_orders
                        )
                    }
                }

                1 -> {
                    tab.text = "B"
                    tab.icon = this.context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_sales
                        )
                    }
                }

                2 -> {
                    tab.text = "C"
                    tab.icon = this.context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_stocks
                        )
                    }
                }
            }
        }.attach()


    }

    companion object {
        @JvmStatic
        fun newInstance() = ABCFragment()
    }
}