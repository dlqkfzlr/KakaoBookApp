package m.woong.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey
    @field:SerializedName("isbn")
    val isbn: String,
    @field:SerializedName("prev_key")
    val prevKey: Int?,
    @field:SerializedName("next_key")
    val nextKey: Int?
)
