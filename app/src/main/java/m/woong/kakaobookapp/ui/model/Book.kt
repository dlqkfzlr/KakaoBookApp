package m.woong.kakaobookapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val isbn: String,
    val contents: String,
    val datetime: String,
    val price: Int,
    val publisher: String,
    val thumbnail: String,
    val title: String,
    var isFavorite: Boolean
): Parcelable
