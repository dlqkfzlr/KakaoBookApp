package m.woong.kakaobookapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import m.woong.kakaobookapp.data.local.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM remote_key WHERE isbn = :isbn")
    suspend fun selectRemoteKeyWithIsbn(isbn: String): RemoteKey?

    @Query("DELETE FROM remote_key")
    suspend fun deleteRemoteKeys()
}