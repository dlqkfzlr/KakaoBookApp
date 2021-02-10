package m.woong.remote

import m.woong.network.KakaoSearchApi
import m.woong.remote.enums.KakaoSearchBookTargetType
import m.woong.remote.model.KakaoBookResponse
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