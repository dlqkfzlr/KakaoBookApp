package m.woong.kakaobookapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import m.woong.KakaoBookRepository
import m.woong.kakaobookapp.ui.model.Book
import m.woong.remote.enums.KakaoSearchBookTargetType
import m.woong.remote.enums.KakaoSearchBookTargetType.*
import m.woong.remote.model.wrapper.ResWrapper
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: KakaoBookRepository
) : ViewModel() {

    // two-way databinding
    val queryData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private var currentBookList: Flow<PagingData<Book>>? = null

    private var _targetType = MutableLiveData<KakaoSearchBookTargetType>()
    private val targetType: LiveData<KakaoSearchBookTargetType>
        get() = _targetType

    private var _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    fun selectTargetType(position: Int){
        val type = when(position){
            0 -> TITLE
            1 -> ISBN
            2 -> PUBLISHER
            else -> PERSON
        }
        _targetType.postValue(type)
    }

    fun searchBooks() {
        Log.d(TAG, "searchBooks 호출")
        viewModelScope.launch {
            if (!queryData.value.isNullOrBlank()){
                fetchBooks(queryData.value!!, targetType.value)
            } else {
                Log.d(TAG, "Search중단 queryData:${queryData.value}")
            }
        }
    }

    private suspend fun fetchBooks(query: String, type: KakaoSearchBookTargetType?) {
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
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}