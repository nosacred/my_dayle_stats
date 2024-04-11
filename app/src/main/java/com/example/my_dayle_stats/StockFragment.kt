package com.example.my_dayle_stats

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StockFragment : Fragment(R.layout.fragment_stock) {
    var stocks = emptyList<Stock>()
    lateinit var recyclerView: RecyclerView
    private val dataModel : DataModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.requireViewById (R.id.stockRecycleView)
        val fab = view.requireViewById<FloatingActionButton>(R.id.fabScrollDownStock)
        dataModel.stocksVM.observe(viewLifecycleOwner) {
            stocks = dataModel.stocksVM.value.orEmpty()
            recyclerView.adapter = CustomStocksAdapter( { position -> showDopStocksViews(position)  },makeCustomStocks(stocks),stocks)
            recyclerView.layoutManager = LinearLayoutManager(this.context)
        }
        fab.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = StockFragment()

    }


    private fun makeCustomStocks(stocks: List<Stock>):List<CustomStock>{
        val customStocks = ArrayList<CustomStock>()

        val bcodesHs = HashSet<String>()
        for(s :Stock in stocks){
            bcodesHs.add(s.barcode)
        }


        for(s :String in bcodesHs){
            val st =stocks.filter { it.barcode == s }

            val supplierArticle=st.stream().findFirst().get().supplierArticle
            val nmId =st.stream().findFirst().get().nmId
            val barcode =st.stream().findFirst().get().barcode
            val quantity=st.map { it.quantity }.sum()

            val inWayToClient=st.map { it.inWayToClient }.sum()
            val inWayFromClient=st.map { it.inWayFromClient}.sum()
            val quantityFull=st.map { it.quantityFull }.sum()
            val category=st.stream().findFirst().get().category
            val subject=st.stream().findFirst().get().subject
            val brand=st.stream().findFirst().get().brand
            val techSize=st.stream().findFirst().get().techSize
            val price=st.stream().findFirst().get().price
            val discount=st.stream().findFirst().get().discount
            val wereHouseArray = st.map { it.warehouseName }.toSet()
            val cStock = CustomStock(supplierArticle,nmId,barcode,quantity,inWayToClient,
                inWayFromClient,quantityFull,category,subject,brand,techSize,price, discount,wereHouseArray
            )
            customStocks.add(cStock)

        }
        return customStocks.sortedBy { it.quantity }.reversed()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun upDate(listStock : List<Stock>){
        recyclerView.adapter=CustomStocksAdapter( { position -> showDopStocksViews(position)  },makeCustomStocks(listStock),stocks)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun showDopStocksViews(position :Int){
        val view1 = view?.findViewById<RecyclerView>(R.id.stockRecycleView)
        val dopView = view1?.findViewHolderForAdapterPosition(position)?.itemView?.
        findViewById<LinearLayout>(R.id.stocksConteiner)

        if(dopView?.visibility ==View.GONE){
            dopView.visibility=View.VISIBLE
        } else {
            dopView?.visibility = View.GONE
        }
    }
}