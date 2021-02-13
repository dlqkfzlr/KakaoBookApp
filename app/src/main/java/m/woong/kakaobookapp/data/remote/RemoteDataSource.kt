package m.woong.kakaobookapp.data.remote

import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.model.KakaoBookResponse

interface RemoteDataSource {

    suspend fun searchBook(query: String, page: Int, target: KakaoSearchBookTargetType?): KakaoBookResponse

}