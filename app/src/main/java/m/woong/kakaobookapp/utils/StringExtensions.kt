package m.woong.kakaobookapp.utils

import android.util.Log

/*
fun String.toDate(

): Date? {

}*/

fun String.toDate(): String {
    val index = this.indexOfFirst { it == 'T' }
    return this.substring(0 until index)
}

fun String.toKoreanWon(): String {
    val strPrice = this.reversed()
    var result = ""
    val size = strPrice.count()
    for (i in 0 until size) {
        val temp = if (i % 3 == 2 && i != size - 1) "," else ""
        result = temp + strPrice[i] + result
    }
    return "₩$result"
}