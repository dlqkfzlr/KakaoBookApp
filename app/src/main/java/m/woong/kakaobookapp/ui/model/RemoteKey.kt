package m.woong.kakaobookapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteKey(
    val isbn: String,
    val prevKey: Int?,
    val nextKey: Int?
): Parcelable
