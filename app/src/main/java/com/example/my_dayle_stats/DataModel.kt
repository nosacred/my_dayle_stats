package com.example.my_dayle_stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val stocksVM : MutableLiveData<List<Stock>> by lazy {
        MutableLiveData<List<Stock>>()
    }

    val salesVM : MutableLiveData<List<Sale>> by lazy {
        MutableLiveData<List<Sale>>()
    }
    val ordersVM : MutableLiveData<List<Order>> by lazy {
        MutableLiveData<List<Order>>()
    }
}