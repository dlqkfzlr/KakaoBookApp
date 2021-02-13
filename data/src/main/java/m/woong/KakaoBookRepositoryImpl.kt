package m.woong

import androidx.paging.*
import m.woong.local.LocalDataSource
import m.woong.local.entity.Book
import m.woong.local.entity.RemoteKey
import m.woong.remote.RemoteDataSource
import m.woong.remote.base.BaseRepository
import m.woong.remote.enums.KakaoSearchBookTargetType
import kotlinx.coroutines.flow.Flow
import m.woong.network.KakaoSearchApi.Companion.BOOK_PAGING_SIZE
import javax.inject.Inject

class KakaoBookRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): BaseRepository(), KakaoBookRepository {

    override suspend fun searchBook(
        query: String,
        page: Int,
        target: KakaoSearchBookTargetType?
    ) = safeApiCall {
        remoteDataSource.searchBook(query, page, target)
    }

    override suspend fun saveBooks(books: List<Book>) {
        localDataSource.saveBooks(books)
    }

    override fun getBooks(): PagingSource<Int, Book> {
        return localDataSource.getPagedBooks()
    }

    override suspend fun updateBook(book: Book): Int {
        return localDataSource.updateBook(book)
    }

    override suspend fun clearBooks() {
        localDataSource.clearBooks()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        localDataSource.saveRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithIsbn(isbn: String): RemoteKey? {
        return localDataSource.getRemoteKeyWithIsbn(isbn)
    }

    override suspend fun clearRemoteKeys() {
        localDataSource.clearRemoteKeys()
    }
}