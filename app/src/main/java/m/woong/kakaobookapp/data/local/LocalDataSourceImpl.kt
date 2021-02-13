package m.woong.kakaobookapp.data.local

import android.util.Log
import androidx.paging.PagingSource
import m.woong.kakaobookapp.data.local.dao.BookDao
import m.woong.kakaobookapp.data.local.dao.RemoteKeyDao
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.entity.RemoteKey
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val bookDao: BookDao,
    private val remoteKeyDao: RemoteKeyDao
): LocalDataSource {

    override suspend fun saveBooks(books: List<Book>) {
        bookDao.insertAllBooks(books)
    }

    override fun getPagedBooks(): PagingSource<Int, Book> {
        return bookDao.selectPagedBooks()
    }

    override suspend fun updateBook(book: Book): Int {
        return bookDao.updateBook(book)
    }

    override suspend fun clearBooks() {
        bookDao.deleteBooks()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>): List<Long> {
        return remoteKeyDao.insertRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey? {
        return remoteKeyDao.selectRemoteKeyWithIsbn(isbn)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeyDao.deleteRemoteKeys()
    }
}