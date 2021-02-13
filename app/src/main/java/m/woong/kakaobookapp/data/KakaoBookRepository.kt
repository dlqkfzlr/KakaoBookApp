package m.woong.kakaobookapp.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.entity.RemoteKey
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.model.KakaoBookResponse
import m.woong.kakaobookapp.data.remote.model.wrapper.ResWrapper
import kotlinx.coroutines.flow.Flow

interface KakaoBookRepository {

    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType?): ResWrapper<KakaoBookResponse>
    fun searchBookStream(query: String, target: KakaoSearchBookTargetType?): Flow<PagingData<Book>>

    suspend fun saveBooks(books: List<Book>)
    fun getBooks(): PagingSource<Int, Book>
    suspend fun updateBook(book: Book): Int
    suspend fun clearBooks()
    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey?
    suspend fun clearRemoteKeys()

}