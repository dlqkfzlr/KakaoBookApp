package m.woong.kakaobookapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.kakaobookapp.data.KakaoBookRepository
import m.woong.kakaobookapp.data.KakaoBookRepositoryImpl
import m.woong.kakaobookapp.data.local.LocalDataSource
import m.woong.kakaobookapp.data.local.LocalDataSourceImpl
import m.woong.kakaobookapp.data.remote.RemoteDataSource
import m.woong.kakaobookapp.data.remote.RemoteDataSourceImpl

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