package m.woong.kakaobookapp.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import m.woong.kakaobookapp.data.KakaoBookRepository
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType.*
import m.woong.kakaobookapp.ui.model.Book
import m.woong.kakaobookapp.utils.Event
import javax.inject.Inject
import m.woong.kakaobookapp.data.local.entity.Book as DbBook

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: KakaoBookRepository
) : ViewModel() {

    // Two-way DataBinding
    val queryData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private var _targetType = MutableLiveData<KakaoSearchBookTargetType>()
    val targetType: LiveData<KakaoSearchBookTargetType>
        get() = _targetType

    private var _bookList = MutableLiveData<PagingData<Book>>()
    val bookList: LiveData<PagingData<Book>>
        get() = _bookList

    private var currentQueryValue: String? = null
    private var currentBookList: Flow<PagingData<Book>>? = null

    private var searchJob: Job? = null

    private var _dbUpdateSuccess = MutableLiveData<Event<Boolean>>()
    val dbUpdateSuccess: LiveData<Event<Boolean>>
    get() = _dbUpdateSuccess

    override fun onCleared() {
        super.onCleared()
        searchJob = null
    }

    fun selectTargetType(position: Int) {
        val type = when (position) {
            0 -> TITLE
            1 -> ISBN
            2 -> PUBLISHER
            else -> PERSON
        }
        _targetType.postValue(type)
    }

    fun searchBooks() {
        if (!queryData.value.isNullOrBlank()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    fetchBookStream(
                        queryData.value!!,
                        targetType.value
                    ).collectLatest { _bookList.postValue(it) }
                }
            }
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

    fun updateFavorite(book: Book) {
        book.isFavorite = !book.isFavorite
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repository.updateBook(uiBookToDbBook(book))
                _dbUpdateSuccess.postValue(Event(result == 1))
            }
        }
    }

    private fun pagedBookMapper(pagingData: PagingData<DbBook>): PagingData<Book> =
        pagingData.map { dbBookToUiBook(it) }

    private fun dbBookToUiBook(dbBook: DbBook): Book = Book(
        dbBook.isbn,
        dbBook.contents,
        dbBook.datetime,
        dbBook.price,
        dbBook.publisher,
        dbBook.thumbnail,
        dbBook.title,
        dbBook.isFavorite
    )

    private fun uiBookToDbBook(book: Book): DbBook = DbBook(
        book.isbn,
        book.contents,
        book.datetime,
        book.price,
        book.publisher,
        book.thumbnail,
        book.title,
        book.isFavorite
    )

}