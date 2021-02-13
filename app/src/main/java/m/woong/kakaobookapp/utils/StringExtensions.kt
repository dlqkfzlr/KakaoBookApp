package m.woong.kakaobookapp.utils

fun String.toDate(): String {
    return try {
        val index = this.indexOfFirst { it == 'T' }
        this.substring(0 until index)
    } catch (e: StringIndexOutOfBoundsException) {
        this
    }
}

fun String.toKoreanWon(): String {
    return try {
        val strPrice = this.reversed()
        var result = ""
        val size = strPrice.count()
        for (i in 0 until size) {
            val temp = if (i % 3 == 2 && i != size - 1) "," else ""
            result = temp + strPrice[i] + result
        }
        "₩$result"
    } catch (e: StringIndexOutOfBoundsException) {
        this
    }
}