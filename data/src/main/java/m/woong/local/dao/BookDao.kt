package m.woong.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import m.woong.local.entity.Book

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(books: List<Book>)

    @Query("SELECT * FROM book")
    fun selectPagedBooks(): PagingSource<Int, Book>

    @Update
    suspend fun updateBook(book: Book): Int

    @Query("DELETE FROM book")
    suspend fun deleteBooks()

}