package com.example.my_dayle_stats

object articleLink {
    fun getImgUrl1(itemId: Int): String {
        val basketNumber: String

        val part: Int = itemId / 1000
        val vol: Int = itemId / 100000

        basketNumber = if (vol != 0 == vol >= 0 && vol <= 143) {
            "01"
        } else if (vol != 0 == vol >= 144 && vol <= 287) {
            "02"
        } else if (vol != 0 == vol >= 288 && vol <= 431) {
            "03"
        } else if (vol != 0 == vol >= 432 && vol <= 719) {
            "04"
        } else if (vol != 0 == vol >= 720 && vol <= 1007) {
            "05"
        } else if (vol != 0 == vol >= 1008 && vol <= 1061) {
            "06"
        } else if (vol != 0 == vol >= 1062 && vol <= 1115) {
            "07"
        } else if (vol != 0 == vol >= 1116 && vol <= 1169) {
            "08"
        } else if (vol != 0 == vol >= 1170 && vol <= 1313) {
            "09"
        } else if (vol != 0 == vol >= 1314 && vol <= 1601) {
            "10"
        } else if (vol != 0 == vol >= 1602 && vol <= 1655) {
            "11"
        } else if (vol != 0 == vol >= 1656 && vol <= 1919) {
            "12"
        } else if (vol != 0 == vol >= 1920 && vol <= 2045) {
            "13"
        } else if (vol != 0 == vol >= 2046 && vol <= 2189) {
            "14"
        } else if (vol != 0 == vol >= 2190 && vol <= 2405) {
            "15"
        } else if (vol != 0 == vol >= 2406 && vol <= 2641) {
            "16"
        } else {
            "17"
        }
        val url = "https://basket-$basketNumber.wbbasket.ru/vol$vol/part$part/$itemId/images/big/1.webp"
        return url
    }
    fun getImgUrl2(itemId: Int): String {
        val basketNumber: String

        val part: Int = itemId / 1000
        val vol: Int = itemId / 100000

        basketNumber = if (vol != 0 == vol >= 0 && vol <= 143) {
            "01"
        } else if (vol != 0 == vol >= 144 && vol <= 287) {
            "02"
        } else if (vol != 0 == vol >= 288 && vol <= 431) {
            "03"
        } else if (vol != 0 == vol >= 432 && vol <= 719) {
            "04"
        } else if (vol != 0 == vol >= 720 && vol <= 1007) {
            "05"
        } else if (vol != 0 == vol >= 1008 && vol <= 1061) {
            "06"
        } else if (vol != 0 == vol >= 1062 && vol <= 1115) {
            "07"
        } else if (vol != 0 == vol >= 1116 && vol <= 1169) {
            "08"
        } else if (vol != 0 == vol >= 1170 && vol <= 1313) {
            "09"
        } else if (vol != 0 == vol >= 1314 && vol <= 1601) {
            "10"
        } else if (vol != 0 == vol >= 1602 && vol <= 1655) {
            "11"
        } else if (vol != 0 == vol >= 1656 && vol <= 1919) {
            "12"
        } else if (vol != 0 == vol >= 1920 && vol <= 2045) {
            "13"
        } else if (vol != 0 == vol >= 2046 && vol <= 2189) {
            "14"
        } else if (vol != 0 == vol >= 2190 && vol <= 2405) {
            "15"
        } else if (vol != 0 == vol >= 2406 && vol <= 2641) {
            "16"
        } else {
            "17"
        }
        val url = "https://basket-$basketNumber.wbbasket.ru/vol$vol/part$part/$itemId/images/big/2.webp"
        return url
    }
    fun getImgUrl3(itemId: Int): String {
        val basketNumber: String

        val part: Int = itemId / 1000
        val vol: Int = itemId / 100000

        basketNumber = if (vol != 0 == vol >= 0 && vol <= 143) {
            "01"
        } else if (vol != 0 == vol >= 144 && vol <= 287) {
            "02"
        } else if (vol != 0 == vol >= 288 && vol <= 431) {
            "03"
        } else if (vol != 0 == vol >= 432 && vol <= 719) {
            "04"
        } else if (vol != 0 == vol >= 720 && vol <= 1007) {
            "05"
        } else if (vol != 0 == vol >= 1008 && vol <= 1061) {
            "06"
        } else if (vol != 0 == vol >= 1062 && vol <= 1115) {
            "07"
        } else if (vol != 0 == vol >= 1116 && vol <= 1169) {
            "08"
        } else if (vol != 0 == vol >= 1170 && vol <= 1313) {
            "09"
        } else if (vol != 0 == vol >= 1314 && vol <= 1601) {
            "10"
        } else if (vol != 0 == vol >= 1602 && vol <= 1655) {
            "11"
        } else if (vol != 0 == vol >= 1656 && vol <= 1919) {
            "12"
        } else if (vol != 0 == vol >= 1920 && vol <= 2045) {
            "13"
        } else if (vol != 0 == vol >= 2046 && vol <= 2189) {
            "14"
        } else if (vol != 0 == vol >= 2190 && vol <= 2405) {
            "15"
        } else if (vol != 0 == vol >= 2406 && vol <= 2641) {
            "16"
        } else {
            "17"
        }
        val url = "https://basket-$basketNumber.wb.ru/vol$vol/part$part/$itemId/images/big/3.webp"
        return url
    }

    fun getWBLink(nmId : String):String {
        return "http://www.wildberries.ru/catalog/${nmId}/detail.aspx?targetUrl=EX"
    }
}