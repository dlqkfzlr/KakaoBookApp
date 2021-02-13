package m.woong.kakaobookapp.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import m.woong.kakaobookapp.data.KakaoBookRepository
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType.*
import m.woong.kakaobookapp.ui.model.Book
import m.woong.kakaobookapp.data.local.entity.Book as dbBook
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: KakaoBookRepository
) : ViewModel() {

    // Two-way DataBinding
    val queryData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private var currentQueryValue: String? = null
    private var currentTargetValue: KakaoSearchBookTargetType? = TITLE
    private var currentBookList: Flow<PagingData<Book>>? = null


    val bookList: LiveData<PagingData<Book>?> = searchBooks()
        .asLiveData(Dispatchers.Main)


    private var _targetType = MutableLiveData<KakaoSearchBookTargetType>()
    private val targetType: LiveData<KakaoSearchBookTargetType>
        get() = _targetType

    private var _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    /*private var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList*/

    fun selectTargetType(position: Int) {
        val type = when (position) {
            0 -> TITLE
            1 -> ISBN
            2 -> PUBLISHER
            else -> PERSON
        }
        _targetType.postValue(type)
    }

    fun searchBooks(): Flow<PagingData<Book>?> {
        Log.d(TAG, "searchBooks 호출")
        return if (!queryData.value.isNullOrBlank()) {
            fetchBookStream(queryData.value!!, targetType.value)
        } else {
            flowOf(null)
        }
    }

    private fun fetchBookStream(
        query: String,
        target: KakaoSearchBookTargetType?
    ): Flow<PagingData<Book>> {
        val lastResult = currentBookList
        if (lastResult != null && query == currentQueryValue) {
            return lastResult
        }
        currentQueryValue = query
        return repository.searchBookStream(query, target)
            .map { pagingData -> pagedBookMapper(pagingData) }
            .cachedIn(viewModelScope)
    }

    /*private suspend fun fetchBooks(query: String, type: KakaoSearchBookTargetType?) {
        val response = repository.searchBook(query, 1, type)
        Log.d(TAG, "Search시작 query:$query, type:$type")
        when (response) {
            is ResWrapper.Success -> {
                val books = response.value.documents
                    .map {
                        Book(
                            isbn = it.isbn,
                            contents = it.contents,
                            datetime = it.datetime,
                            price = it.price,
                            publisher = it.publisher,
                            thumbnail = it.thumbnail,
                            title = it.title,
                            isFavorite = false
                        )
                    }
                _bookList.value = books
            }
            is ResWrapper.Error -> {
                Log.d(TAG, "Error response:${response}")
            }
        }
    }*/

    private fun pagedBookMapper(pagingData: PagingData<dbBook>): PagingData<Book> =
        pagingData.map {
            Book(
                it.isbn,
                it.contents,
                it.datetime,
                it.price,
                it.publisher,
                it.thumbnail,
                it.title,
                it.isFavorite
            )
        }

companion object {
    private val TAG = MainViewModel::class.java.simpleName
}
}