package m.woong.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import m.woong.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE isbn = :isbn")
    suspend fun selectRemoteKeysWithIsbn(isbn: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}