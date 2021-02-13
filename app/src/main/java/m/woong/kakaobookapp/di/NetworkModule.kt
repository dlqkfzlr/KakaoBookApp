package m.woong.kakaobookapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.kakaobookapp.data.network.KakaoSearchApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com/"

    @Singleton
    @Provides
    fun provideKakaoSearchApi(
        /*okHttpClient: OkHttpClient*/
    ): KakaoSearchApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoSearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient
        = OkHttpClient.Builder()
        .build()
}