package m.woong.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.KakaoBookRepository
import m.woong.KakaoBookRepositoryImpl
import m.woong.local.LocalDataSource
import m.woong.local.LocalDataSourceImpl
import m.woong.remote.RemoteDataSource
import m.woong.remote.RemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindKakaoRepository(
        kakaoBookRepositoryImpl: KakaoBookRepositoryImpl
    ): KakaoBookRepository

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

}