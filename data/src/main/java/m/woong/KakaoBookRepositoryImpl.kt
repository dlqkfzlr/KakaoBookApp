package m.woong

import androidx.lifecycle.MutableLiveData
import m.woong.remote.RemoteDataSource
import m.woong.remote.base.BaseRepository
import m.woong.remote.enum.KakaoSearchBookTargetType
import javax.inject.Inject

class KakaoBookRepositoryImpl @Inject constructor(
    /*private val localDataSource: LocalDataSource,*/
    private val remoteDataSource: RemoteDataSource
): BaseRepository(), KakaoBookRepository {
    var query = MutableLiveData<String>()

    override suspend fun searchBook(
        query: String,
        page: Int,
        target: KakaoSearchBookTargetType
    ) = safeApiCall {
        remoteDataSource.searchBook(query, page, target)
    }
}