package m.woong

import androidx.paging.PagingData
import androidx.paging.PagingSource
import m.woong.local.entity.Book
import m.woong.local.entity.RemoteKey
import m.woong.remote.enums.KakaoSearchBookTargetType
import m.woong.remote.model.KakaoBookResponse
import m.woong.remote.model.wrapper.ResWrapper
import kotlinx.coroutines.flow.Flow

interface KakaoBookRepository {

    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType?): ResWrapper<KakaoBookResponse>

    suspend fun saveBooks(books: List<Book>)
    fun getBooks(): PagingSource<Int, Book>
    suspend fun updateBook(book: Book): Int
    suspend fun clearBooks()
    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey?
    suspend fun clearRemoteKeys()

}