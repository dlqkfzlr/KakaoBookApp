package m.woong.remote

import m.woong.remote.enum.KakaoSearchBookTargetType
import m.woong.remote.model.KakaoBookResponse

interface RemoteDataSource {

    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType): KakaoBookResponse

}