package m.woong.kakaobookapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.kakaobookapp.data.local.db.KakaoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideKakaoDb(app: Application) = KakaoDatabase.invoke(app)

    @Singleton
    @Provides
    fun provideBookDao(kakaoDb: KakaoDatabase) = kakaoDb.bookDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(kakaoDb: KakaoDatabase) = kakaoDb.remoteKeyDao()

}