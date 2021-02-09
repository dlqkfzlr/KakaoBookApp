package m.woong.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    /* @Singleton
    @Provides
    fun provideDb(app: Application) = KakaoDatabase.invoke(app)

    @Singleton
    @Provides
    fun provideBookDao(db: KakaoDatabase) = db.bookDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(db: KakaoDatabase) = db.remoteKeysDao()*/

}