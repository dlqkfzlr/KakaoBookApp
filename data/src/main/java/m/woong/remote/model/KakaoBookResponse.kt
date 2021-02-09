package m.woong.remote.model

import com.google.gson.annotations.SerializedName


data class KakaoBookResponse(
    @SerializedName("documents")
    val documents: List<Document>,
    @SerializedName("meta")
    val meta: Meta
) {
    data class Meta(
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("total_count")
        val totalCount: Int
    )
    data class Document(
        @SerializedName("authors")
        val authors: List<String>,
        @SerializedName("contents")
        val contents: String,
        @SerializedName("datetime")
        val datetime: String,
        @SerializedName("isbn")
        val isbn: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("sale_price")
        val salePrice: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("translators")
        val translators: List<String>,
        @SerializedName("url")
        val url: String,
    )
}
