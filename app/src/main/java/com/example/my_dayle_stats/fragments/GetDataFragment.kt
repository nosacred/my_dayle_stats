package com.example.my_dayle_stats.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.my_dayle_stats.DataModel
import com.example.my_dayle_stats.Networking
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.R
import com.example.my_dayle_stats.Sale
import com.example.my_dayle_stats.Stock
import com.example.my_dayle_stats.adapters.buttonTimer60sec
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetDataFragment : Fragment() {

    private val dataModel: DataModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var allOrder = emptyList<Order>()
        var salesToday = emptyList<Sale>()
        var stocksToday = emptyList<Stock>()


        val getallBtn = view.findViewById<Button>(R.id.getAllDataBtn)
        val getOrdersbtn = view.findViewById<Button>(R.id.getOrdersButton)
        val getSalesBtn = view.findViewById<Button>(R.id.getSalesButton)
        val getStocksBtn = view.findViewById<Button>(R.id.getStocksButton)




        val date = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val apiKey = sharedPref?.getString(getString(R.string.api_key), null)


        getallBtn.setOnClickListener {

            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            buttonTimer60sec(getOrdersbtn, getOrdersbtn.text.toString())
            buttonTimer60sec(getSalesBtn, getSalesBtn.text.toString())
            buttonTimer60sec(getStocksBtn, getStocksBtn.text.toString())



            GlobalScope.launch {
                try {
                    allOrder = apiKey?.let { it1 ->
                        Networking.wbApi.searchOrders(date, it1).execute().body()
                    }!!
                } catch (ex: NullPointerException) {
                   Log.d("429code","at orders")
                }

                dataModel.ordersVM.postValue(allOrder)


                try {

                    salesToday = apiKey?.let { it1 ->
                        Networking.wbApi.searchSales(
                            date,
                            it1
                        ).execute().body()
                    }!!
                }catch (ex: NullPointerException) {
                    Log.d("429code","at sales")
                }
                dataModel.salesVM.postValue(salesToday)

                try {
                    stocksToday =
                        apiKey?.let { it1 ->
                            Networking.wbApi.searchStocks(
                                LocalDate.now().minusDays(10).toString(),
                                it1
                            )
                                .execute().body()
                        }!!
                }catch (ex: NullPointerException) {
                    Log.d("429code","at stocks")
                }
                dataModel.stocksVM.postValue(stocksToday)


            }

        }


        getOrdersbtn.setOnClickListener {
            buttonTimer60sec(getOrdersbtn, getOrdersbtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                allOrder = apiKey?.let { it1 ->
                    Networking.wbApi.searchOrders(
                        date,
                        it1
                    ).execute().body()
                }!!
                dataModel.ordersVM.postValue(allOrder)
            }
        }

        getSalesBtn.setOnClickListener {
            buttonTimer60sec(getSalesBtn, getSalesBtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                salesToday = apiKey?.let { it1 ->
                    Networking.wbApi.searchSales(
                        date,
                        it1
                    ).execute().body()
                }!!
                dataModel.salesVM.postValue(salesToday)
            }
        }
        getStocksBtn.setOnClickListener {
            buttonTimer60sec(getStocksBtn, getStocksBtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                stocksToday =
                    apiKey?.let { it1 ->
                        Networking.wbApi.searchStocks(LocalDate.now().minusDays(10).toString(), it1)
                            .execute().body()
                    }!!
                dataModel.stocksVM.postValue(stocksToday)
            }
        }

    }

    companion object {

        fun newInstance() = GetDataFragment()
    }
}