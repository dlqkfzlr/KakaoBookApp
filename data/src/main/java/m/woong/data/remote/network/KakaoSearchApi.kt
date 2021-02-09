package m.woong.data.remote.network

import m.woong.data.remote.enum.KakaoSearchBookTargetType
import m.woong.data.remote.enum.KakaoSearchSortType
import m.woong.data.remote.model.BaseKakaoResponse
import m.woong.data.remote.model.KakaoBookResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchApi {

    @GET("v3/search/book")
    suspend fun searchBook(
        @Header("Authorization") restApiKey: String,
        @Query("query") query: String,
        @Query("sort") sort: KakaoSearchSortType = KakaoSearchSortType.ACCURACY,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
        @Query("target") target: KakaoSearchBookTargetType? = null,
    ): BaseKakaoResponse<KakaoBookResponse>
}