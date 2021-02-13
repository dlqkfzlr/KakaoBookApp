package m.woong.kakaobookapp.data.remote

import m.woong.kakaobookapp.data.network.KakaoSearchApi
import m.woong.kakaobookapp.data.remote.enums.KakaoSearchBookTargetType
import m.woong.kakaobookapp.data.remote.model.KakaoBookResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: KakaoSearchApi
): RemoteDataSource {

    override suspend fun searchBook(
        query: String,
        page: Int,
        target: KakaoSearchBookTargetType?
    ): KakaoBookResponse {
        return service.searchBook(query = query, page = page, target = target)
    }
}