package m.woong.kakaobookapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.kakaobookapp.data.network.KakaoSearchApi
import m.woong.kakaobookapp.data.remote.enums.EnumConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        /*val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY*/
        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideKakaoSearchApi(
        okHttpClient: OkHttpClient
    ): KakaoSearchApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .build()
            .create(KakaoSearchApi::class.java)
    }
}

