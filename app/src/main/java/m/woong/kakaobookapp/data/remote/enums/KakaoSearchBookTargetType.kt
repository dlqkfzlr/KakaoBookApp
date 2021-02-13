package m.woong.kakaobookapp.data.remote.enums

import com.google.gson.annotations.SerializedName

enum class KakaoSearchBookTargetType {
    @SerializedName("title")
    TITLE,

    @SerializedName("isbn")
    ISBN,

    @SerializedName("publisher")
    PUBLISHER,

    @SerializedName("person")
    PERSON,
}