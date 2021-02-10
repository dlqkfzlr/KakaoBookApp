package m.woong.local

import androidx.paging.PagingSource
import m.woong.local.dao.BookDao
import m.woong.local.dao.RemoteKeyDao
import m.woong.local.entity.Book
import m.woong.local.entity.RemoteKey
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

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        remoteKeyDao.insertRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey? {
        return remoteKeyDao.selectRemoteKeyWithIsbn(isbn)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeyDao.deleteRemoteKeys()
    }
}