package m.woong.kakaobookapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bumptech.glide.load.HttpException
import m.woong.kakaobookapp.data.local.LocalDataSource
import m.woong.kakaobookapp.data.local.entity.Book
import m.woong.kakaobookapp.data.local.entity.RemoteKey
import m.woong.kakaobookapp.data.network.KakaoSearchApi.Companion.BOOK_STARTING_PAGE_INDEX
import m.woong.kakaobookapp.data.remote.RemoteDataSource
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.model.KakaoBookResponse
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
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: BOOK_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                remoteKey.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                remoteKey?.nextKey
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }
        }

        try {
            val response = remoteDataSource.searchBook(query, page = page, target = target)
            val books = response.documents.map { document -> documentToBook(document) }
            val endOfPaginationReached = books.count() < state.config.pageSize
            if (loadType == LoadType.REFRESH) {
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
                saveRemoteKeys(remoteKeys = remoteKeys)
                saveBooks(books = books)
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

    // LoadType.PREPEND
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Book>
    ): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { book ->
                localDataSource.getRemoteKeyWithIsbn(book.isbn)
            }
    }

    // LoadType.APPEND
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Book>
    ): RemoteKey? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { book ->
                localDataSource.getRemoteKeyWithIsbn(book.isbn)
            }
    }

    private fun documentToBook(document: KakaoBookResponse.Document): Book = Book(
        document.isbn,
        document.contents,
        document.datetime,
        document.price,
        document.publisher,
        document.thumbnail,
        document.title
    )
}