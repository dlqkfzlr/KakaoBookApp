package m.woong.kakaobookapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import m.woong.kakaobookapp.data.local.dao.BookDao
import m.woong.kakaobookapp.data.local.dao.RemoteKeyDao
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.entity.RemoteKey

@Database(
    entities = [Book::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class KakaoDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object{
        val DATABASE_NAME = "kakao.db"
        @Volatile private var instance : KakaoDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            KakaoDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    }
}