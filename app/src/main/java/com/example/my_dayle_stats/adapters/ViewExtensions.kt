package com.example.my_dayle_stats.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import com.example.my_dayle_stats.AbClass
import com.example.my_dayle_stats.CompositeStat
import com.example.my_dayle_stats.Order
import com.example.my_dayle_stats.OrderPerBarcode
import com.example.my_dayle_stats.Sale
import com.example.my_dayle_stats.Stock
import com.example.my_dayle_stats.fragments.WereHouse
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.stream.Collectors
import kotlin.jvm.optionals.getOrNull

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun buttonTimer60sec(btn: Button, text: String) {
    btn.isEnabled = false
    val txtColor = btn.textColors
    val cdt = object : CountDownTimer(60000L, 1000L) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {

            btn.text = "Повторное обновление доступно через  ${(millisUntilFinished / 1000)}"
        }

        override fun onFinish() {
            btn.text = text
            btn.isEnabled = true
        }

    }
    cdt.start()
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun getCompostiteStatList(
    orders: List<Order>,
    sales: List<Sale>,
    stocks: List<Stock>
): List<CompositeStat> {
    val cstats = ArrayList<CompositeStat>()

    val barcodes = HashSet<String>()

    barcodes.addAll(orders.stream().map(Order::barcode).collect(Collectors.toList()))
//    barcodes.addAll(sales.stream().map(Sale::barcode).collect(Collectors.toList()))
//    barcodes.addAll(stocks.stream().map(Stock::barcode).collect(Collectors.toList()))

    for (bcode in barcodes) {
        val whSet = HashSet<String>()
        whSet.addAll(stocks.stream().filter { it.barcode == bcode }.map { it.warehouseName }
            .collect(Collectors.toList()))
        whSet.sorted()
        val supplierArticle = orders.filter { it.barcode == bcode }.getOrNull(0)?.supplierArticle
        val nmId = orders.filter { it.barcode == bcode }[0].nmId
        val quantity = stocks.stream().filter { it.barcode == bcode }.mapToInt { it.quantity }.sum()
        val inWayToClient =
            stocks.stream().filter { it.barcode == bcode }.mapToInt { it.inWayToClient }.sum()
        val inWayFromClient =
            stocks.stream().filter { it.barcode == bcode }.mapToInt { it.inWayFromClient }.sum()
        val category = orders.stream().filter { it.barcode == bcode }.findAny().get().category
        val subject = orders.stream().filter { it.barcode == bcode }.findAny().get().subject
        val brand = orders.stream().filter { it.barcode == bcode }.findAny().get().brand
        val techSize = orders.stream().filter { it.barcode == bcode }.findAny().get().techSize
        val regionNameList = HashSet<String>()
        regionNameList.addAll(orders.stream().filter { it.barcode == bcode }.map { it.regionName }
            .collect(Collectors.toList()))
        regionNameList.addAll(sales.stream().filter { it.barcode == bcode }.map { it.regionName }
            .collect(Collectors.toList()))
        val cancelCountOrder =
            orders.stream().filter { it.barcode == bcode }.filter { it.isCancel }.count().toInt()
        val cancelCountSale =
            sales.stream().filter { it.barcode == bcode }.filter { it.forPay < 0 }.count().toInt()
        val countSale = sales.stream().filter { it.barcode == bcode }.count().toInt()
        val countOrder = orders.stream().filter { it.barcode == bcode }.count().toInt()
        val totalOrdersPrice =
            orders.stream().filter { it.barcode == bcode }.mapToInt { it.finishedPrice.toInt() }
                .sum()
        val totalSaleForPay = sales.stream().filter { it.barcode == bcode }.filter { it.forPay < 0 }
            .mapToInt { it.forPay.toInt() }.sum()
        val moneyBack =
            sales.stream().filter { it.barcode == bcode }.mapToInt { it.forPay.toInt() }.sum()


        val compositeStat = CompositeStat(
            whSet.toList(),
            supplierArticle,
            nmId,
            bcode,
            quantity,
            inWayToClient,
            inWayFromClient,
            category,
            subject,
            brand,
            techSize,
            regionNameList.toList(),
            cancelCountOrder,
            cancelCountSale,
            countSale,
            countOrder,
            totalOrdersPrice,
            totalSaleForPay,
            moneyBack
        )
        cstats.add(compositeStat)
    }
    return cstats.sortedBy { it.countOrder }.reversed()
}

fun getAbcClassList(sales: List<Sale>): List<AbClass> {

    val arrayABc = ArrayList<AbClass>()

    val bcodes = HashSet<String>()
    sales.map { it.barcode }.toCollection(bcodes)

    for (bc in bcodes) {
        val sale = sales.filter { it.barcode == bc }[0]
        val abc = AbClass(
            nmId = sale.nmId.toString(),
            barcode = bc,
            category = sale.category,
            cancelCountSale = sales.filter { it.barcode == bc }.filter { it.forPay.toInt() < 0 }
                .count(),
            countSale = sales.filter { it.barcode == bc }.filter { it.forPay.toInt() > 0 }.count(),
            supplierArticle = sale.supplierArticle,
            subject = sale.subject,
            brand = sale.brand,
            techSize = sale.techSize,
            moneyBack = sales.filter { it.barcode == bc }.filter { it.forPay.toInt() < 0 }
                .map { it.forPay.toInt() }.sum(),
            totalSaleForPay = sales.filter { it.barcode == bc }.sumOf { it.forPay.toInt() }
        )
        arrayABc.add(abc)
    }


    return arrayABc
}

fun getAbcAList(sales: List<Sale>): List<AbClass> {
    val abcList = getAbcClassList(sales)
    val abcAlist = ArrayList<AbClass>()
    val bcodes = HashSet<String>()
    sales.map { it.barcode }.toCollection(bcodes)
    val hashMap = HashMap<String, Int>()
    for (bc in bcodes) {
        hashMap[bc] = sales.filter { it.barcode == bc }.sumOf { it.forPay.toInt() }
    }
    var sortedMap = hashMap.toList().sortedBy { (key, value) -> value }.reversed().toMap()


    val saleMoneyCount = sales.sumOf { it.forPay.toInt() }

    val hashMapA = HashMap<String, Int>()
    val hashMapB = HashMap<String, Int>()
    var hashMapC = HashMap<String, Int>()
    val moneyA = (saleMoneyCount * 0.8).toInt()

    var sumA = 0
    for ((key, value) in sortedMap) {
        sumA += value
        if (sumA > moneyA) {
            break
        }
        hashMapA[key] = value
        hashMap.remove(key, value)
    }

    for ((key, value) in hashMapA) {
        abcAlist.add(abcList.filter { it.barcode == key }[0])
    }
    return abcAlist.sortedBy { it.totalSaleForPay }.reversed()
}

fun getAbcBList(sales: List<Sale>): List<AbClass> {
    val abcList = getAbcClassList(sales)
    val abcBlist = ArrayList<AbClass>()
    val bcodes = HashSet<String>()
    sales.map { it.barcode }.toCollection(bcodes)
    val hashMap = HashMap<String, Int>()
    for (bc in bcodes) {
        hashMap[bc] = sales.filter { it.barcode == bc }.sumOf { it.forPay.toInt() }
    }
    var sortedMap = hashMap.toList().sortedBy { (key, value) -> value }.reversed().toMap()


    val saleMoneyCount = sales.sumOf { it.forPay.toInt() }

    val hashMapA = HashMap<String, Int>()
    val hashMapB = HashMap<String, Int>()
    var hashMapC = HashMap<String, Int>()
    val moneyA = (saleMoneyCount * 0.8).toInt()
    val moneyB = (saleMoneyCount * 0.15).toInt()

    var sumA = 0
    for ((key, value) in sortedMap) {
        sumA += value
        if (sumA > moneyA) {
            break
        }
        hashMapA[key] = value
        hashMap.remove(key, value)
    }
    sortedMap = hashMap.toList().sortedBy { (key, value) -> value }.reversed().toMap()

    var sumB = 0
    for ((key, value) in sortedMap) {
        sumB += value
        if (sumB >= moneyB) {
            break
        }
        hashMapB[key] = value
        hashMap.remove(key, value)
    }

    for ((key, value) in hashMapB) {
        abcBlist.add(abcList.filter { it.barcode == key }[0])
    }
    return abcBlist.sortedBy { it.totalSaleForPay }.reversed()
}

fun getAbcCList(sales: List<Sale>): List<AbClass> {
    val abcList = getAbcClassList(sales)
    val abcClist = ArrayList<AbClass>()
    val bcodes = HashSet<String>()
    sales.map { it.barcode }.toCollection(bcodes)
    val hashMap = HashMap<String, Int>()
    for (bc in bcodes) {
        hashMap[bc] = sales.filter { it.barcode == bc }.sumOf { it.forPay.toInt() }
    }
    var sortedMap = hashMap.toList().sortedBy { (key, value) -> value }.reversed().toMap()


    val saleMoneyCount = sales.sumOf { it.forPay.toInt() }

    val hashMapA = HashMap<String, Int>()
    val hashMapB = HashMap<String, Int>()
    var hashMapC = HashMap<String, Int>()
    val moneyA = (saleMoneyCount * 0.8).toInt()
    val moneyB = (saleMoneyCount * 0.15).toInt()

    var sumA = 0
    for ((key, value) in sortedMap) {
        sumA += value
        if (sumA > moneyA) {
            break
        }
        hashMapA[key] = value
        hashMap.remove(key, value)
    }
    sortedMap = hashMap.toList().sortedBy { (key, value) -> value }.reversed().toMap()

    var sumB = 0
    for ((key, value) in sortedMap) {
        sumB += value
        if (sumB >= moneyB) {
            break
        }
        hashMapB[key] = value
        hashMap.remove(key, value)
    }
    hashMapC = hashMap.toList().sortedBy { (key, value) -> value }.reversed()
        .toMap() as HashMap<String, Int>
    for ((key, value) in hashMapC) {
        abcClist.add(abcList.filter { it.barcode == key }[0])
    }
    return abcClist.sortedBy { it.totalSaleForPay }.reversed()
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun getOrdersPerBarcode(wh: String, orders: List<Order>): List<OrderPerBarcode> {
    val opbc = ArrayList<OrderPerBarcode>()
    val bcodes = orders.stream().filter{it.date.dayOfYear == LocalDateTime.now().dayOfYear}.map { it.barcode }.collect(Collectors.toList()).toSet()

    for (bc in bcodes) {
        val order = orders.stream().filter { it.barcode==bc && it.warehouseName==wh }.findFirst().getOrNull()
        if(order == null ) continue
        val orderperbc = OrderPerBarcode(warehouseName = order.warehouseName,
            supplierArticle = order.supplierArticle,
            barcode = order.barcode,
            techSize = order.techSize,
            nmId = order.nmId,
            subject = order.subject,
            brand = order.brand,
            category = order.category,
            count = orders.stream().filter { it.barcode==bc && it.warehouseName==wh  && it.date.dayOfYear == LocalDateTime.now().dayOfYear}.count().toInt())
        if(orderperbc.count>0){
            opbc.add(orderperbc)
        }

    }
    return opbc.sortedWith(compareBy( OrderPerBarcode::count)).reversed()
}

fun sortWH ( wh : List<WereHouse>) : ArrayList<WereHouse> {

    val whsort = ArrayList<WereHouse>()

    whsort.add(wh.get(0))
    for ( i in 1..wh.size-1){
        if(wh.get(i).countOrders >= whsort.get(i-1).countOrders){
            whsort.add(0,wh.get(i))
        }
        else whsort.add(wh.size-i,wh.get(i))
    }
    return whsort
}