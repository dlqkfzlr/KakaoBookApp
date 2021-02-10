package m.woong.local

import androidx.paging.PagingSource
import m.woong.local.entity.Book
import m.woong.local.entity.RemoteKey

interface LocalDataSource {

    suspend fun saveBooks(books: List<Book>)
    fun getPagedBooks(): PagingSource<Int, Book>
    suspend fun updateBook(book: Book): Int
    suspend fun clearBooks()

    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey?
    suspend fun clearRemoteKeys()
}