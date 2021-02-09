package m.woong.remote.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import m.woong.remote.model.wrapper.ResWrapper
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResWrapper.Success(apiCall.invoke())
            } catch (t: Throwable) {
                when(t) {
                    is HttpException -> {
                        ResWrapper.Error(isNetworkError = false, errorCode = t.code(), errorBody = t.response()?.errorBody())
                    }
                    else -> {
                        ResWrapper.Error(true, null, null)
                    }
                }
            }
        }
    }
}