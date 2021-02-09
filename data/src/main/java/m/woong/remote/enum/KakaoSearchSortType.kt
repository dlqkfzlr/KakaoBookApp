package m.woong.remote.enum

import com.google.gson.annotations.SerializedName

enum class KakaoSearchSortType {
    @SerializedName("accuracy")
    ACCURACY,

    @SerializedName("recency")
    RECENCY,
}