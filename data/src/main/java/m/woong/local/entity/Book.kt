package m.woong.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "isbn")
    val isbn: String,
    @ColumnInfo(name = "contents")
    val contents: String,
    @ColumnInfo(name = "datetime")
    val datetime: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "title")
    val title: String
)