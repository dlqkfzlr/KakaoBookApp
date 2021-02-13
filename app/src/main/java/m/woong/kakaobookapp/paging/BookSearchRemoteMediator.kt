package m.woong.kakaobookapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bumptech.glide.load.HttpException
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.LocalDataSource
import m.woong.kakaobookapp.data.local.entity.RemoteKey
import m.woong.kakaobookapp.data.network.KakaoSearchApi.Companion.BOOK_STARTING_PAGE_INDEX
import m.woong.kakaobookapp.data.remote.RemoteDataSource
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class BookSearchRemoteMediator(
    private val query: String,
    private val target: KakaoSearchBookTargetType?,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RemoteMediator<Int, Book>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Book>): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: BOOK_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                remoteKey?.nextKey ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }
        }

        try {
            val response = remoteDataSource.searchBook(query, page = page, target = target)
            val books = response.documents.map { it ->
                Book(it.isbn, it.contents, it.datetime, it.price, it.publisher, it.thumbnail, it.title)
            }
            val endOfPaginationReached = books.isEmpty()
            if (loadType == LoadType.REFRESH){
                with(localDataSource) {
                    clearRemoteKeys()
                    clearBooks()
                }
            }
            val prevKey = if (page == BOOK_STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val remoteKeys = books.map { it ->
                RemoteKey(it.isbn, prevKey, nextKey)
            }
            with(localDataSource) {
                saveBooks(books = books)
                saveRemoteKeys(remoteKeys = remoteKeys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    // LoadType.REFRESH
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Book>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.isbn?.let { bookIsbn ->
                localDataSource.getRemoteKeyWithIsbn(bookIsbn)
            }
        }
    }

    // LoadType.APPEND
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Book>
    ): RemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { book ->
                localDataSource.getRemoteKeyWithIsbn(book.isbn)
            }
    }
}