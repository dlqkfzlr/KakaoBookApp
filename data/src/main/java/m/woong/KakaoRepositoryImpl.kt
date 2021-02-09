package m.woong

import androidx.lifecycle.MutableLiveData

class KakaoRepositoryImpl: KakaoBookRepository {
    var query = MutableLiveData<String>()

    override fun searchBook() {
        TODO("Not yet implemented")
    }
}