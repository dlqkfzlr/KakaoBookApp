package m.woong.remote

import m.woong.remote.enums.KakaoSearchBookTargetType
import m.woong.remote.model.KakaoBookResponse

interface RemoteDataSource {

    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType?): KakaoBookResponse

}