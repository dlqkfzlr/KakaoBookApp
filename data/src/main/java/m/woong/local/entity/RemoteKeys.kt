package m.woong.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(name = "isbn")
    val isbn: String,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?
)
