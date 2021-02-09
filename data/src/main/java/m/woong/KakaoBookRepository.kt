package m.woong

import m.woong.remote.enum.KakaoSearchBookTargetType
import m.woong.remote.model.KakaoBookResponse
import m.woong.remote.model.wrapper.ResWrapper
import okhttp3.Response

interface KakaoBookRepository {
    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType): ResWrapper<KakaoBookResponse>
}