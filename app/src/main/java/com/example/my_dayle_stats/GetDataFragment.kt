package com.example.my_dayle_stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetDataFragment : Fragment() {

    private val dataModel :DataModel by activityViewModels()
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
        val apiKey =
            "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTcxNzU3NjE4NSwiaWQiOiIxMDMzNTczNS0wNTViLTQ3NWQtOGQ2OC1mMTA1ZDdkMWFkYTgiLCJpaWQiOjkxNDU0MTMsIm9pZCI6MTM1NTI3LCJzIjozMiwic2lkIjoiZTI2NWVlZmEtYjY1My00OTlkLThmMTYtMjAzYzJmNjMwNGQ1IiwidWlkIjo5MTQ1NDEzfQ.omZjk0y5UhP4lDTeY6CP47acpmQY6cD8QpPZRF7vJ0f1KfYz6JEo7ahEGKfVYF2DIFzqmMcc_Rro5RUrpN1YAw"

        getallBtn.setOnClickListener {
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            buttonTimer60sec(getOrdersbtn, getOrdersbtn.text.toString())
            buttonTimer60sec(getSalesBtn, getSalesBtn.text.toString())
            buttonTimer60sec(getStocksBtn, getStocksBtn.text.toString())

            GlobalScope.launch {
                try {
                    allOrder = Networking.wbApi.searchOrders(date, apiKey).execute().body()!!
                }catch (ex :NullPointerException){
                    Toast.makeText(this@GetDataFragment.context, "Попробуйте позже", Toast.LENGTH_SHORT).show()
                }

                dataModel.ordersVM.postValue(allOrder)

                salesToday = Networking.wbApi.searchSales(date, apiKey).execute().body()!!
                dataModel.salesVM.postValue(salesToday)


                stocksToday = Networking.wbApi.searchStocks(LocalDate.now().minusDays(10).toString(), apiKey)
                    .execute().body()!!
                dataModel.stocksVM.postValue(stocksToday)

            }
//            findNavController().navigate(R.id.action_upDateFragment_to_tabFragment)
        }

        getOrdersbtn.setOnClickListener {
            buttonTimer60sec(getOrdersbtn,getOrdersbtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                allOrder = Networking.wbApi.searchOrders(date, apiKey).execute().body()!!
                dataModel.ordersVM.postValue(allOrder)
            }
        }

        getSalesBtn.setOnClickListener {
            buttonTimer60sec(getSalesBtn,getSalesBtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                salesToday = Networking.wbApi.searchSales(date, apiKey).execute().body()!!
                dataModel.salesVM.postValue(salesToday)
            }
        }
        getStocksBtn.setOnClickListener {
            buttonTimer60sec(getStocksBtn,getStocksBtn.text.toString())
            buttonTimer60sec(getallBtn, getallBtn.text.toString())
            GlobalScope.launch {
                stocksToday =
                    Networking.wbApi.searchStocks(LocalDate.now().minusDays(10).toString(), apiKey)
                        .execute().body()!!
                dataModel.stocksVM.postValue(stocksToday)
            }
        }

    }

    companion object {

        fun newInstance() = GetDataFragment()
    }
}