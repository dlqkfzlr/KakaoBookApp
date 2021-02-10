package m.woong.network

import m.woong.remote.enums.KakaoSearchBookTargetType
import m.woong.remote.enums.KakaoSearchSortType
import m.woong.remote.model.KakaoBookResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchApi {

    @GET(SUB_PATH_BOOK)
    suspend fun searchBook(
        @Header("Authorization") restApiKey: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("sort") sort: KakaoSearchSortType = KakaoSearchSortType.ACCURACY,
        @Query("page") page: Int = BOOK_STARTING_PAGE_INDEX,
        @Query("size") size: Int = BOOK_PAGING_SIZE,
        @Query("target") target: KakaoSearchBookTargetType? = null,
    ): KakaoBookResponse

    companion object {
        const val REST_API_KEY = "5f5adf3b28b25871cc11ba3dcf7375fe"
        const val SUB_PATH_BOOK = "v3/search/book"
        const val BOOK_STARTING_PAGE_INDEX = 1
        const val BOOK_PAGING_SIZE = 50
    }
}