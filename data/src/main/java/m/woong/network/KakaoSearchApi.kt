package m.woong.network

import m.woong.remote.base.BaseKakaoResponse
import m.woong.remote.enum.KakaoSearchBookTargetType
import m.woong.remote.enum.KakaoSearchSortType
import m.woong.remote.model.KakaoBookResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchApi {

    @GET("v2/search/$SUB_PATH_BOOK")
    suspend fun searchBook(
        @Header("Authorization") restApiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("sort") sort: KakaoSearchSortType = KakaoSearchSortType.ACCURACY,
        @Query("page") page: Int = BOOK_STARTING_PAGE_INDEX,
        @Query("size") size: Int = BOOK_PAGING_SIZE,
        @Query("target") target: KakaoSearchBookTargetType? = null,
    ): BaseKakaoResponse<KakaoBookResponse>

    companion object {
        const val API_KEY = "AV4A36DcA2EO/8A2IXQSts7NwsU="
        const val SUB_PATH_BOOK = "book"
        const val BOOK_STARTING_PAGE_INDEX = 1
        const val BOOK_PAGING_SIZE = 50
    }
}