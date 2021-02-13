package m.woong.kakaobookapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "book")
data class Book(
    @PrimaryKey @field:SerializedName("isbn")
    val isbn: String,
    @field:SerializedName("contents")
    val contents: String,
    @field:SerializedName("datetime")
    val datetime: String,
    @field:SerializedName("price")
    val price: Int,
    @field:SerializedName("publisher")
    val publisher: String,
    @field:SerializedName("thumbnail")
    val thumbnail: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("is_favorite")
    val isFavorite: Boolean = false
)