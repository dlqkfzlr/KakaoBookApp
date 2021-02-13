package m.woong.kakaobookapp.data

import androidx.paging.*
import m.woong.kakaobookapp.data.local.LocalDataSource
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.entity.RemoteKey
import m.woong.kakaobookapp.data.remote.RemoteDataSource
import m.woong.kakaobookapp.data.remote.base.BaseRepository
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import kotlinx.coroutines.flow.Flow
import m.woong.kakaobookapp.paging.BookSearchRemoteMediator
import m.woong.kakaobookapp.data.network.KakaoSearchApi.Companion.BOOK_PAGING_SIZE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
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

    override fun searchBookStream(
        query: String,
        target: KakaoSearchBookTargetType?
    ): Flow<PagingData<Book>> {
        val pagingSourceFactory = { localDataSource.getPagedBooks() }
        return Pager(
            config = PagingConfig(pageSize = BOOK_PAGING_SIZE, enablePlaceholders = false),
            remoteMediator = BookSearchRemoteMediator(query, target, remoteDataSource, localDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).flow
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