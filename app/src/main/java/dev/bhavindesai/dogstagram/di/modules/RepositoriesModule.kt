package dev.bhavindesai.dogstagram.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.data.utils.InternetUtil
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @[Provides Singleton]
    fun providesDogRepository(
        dogService: DogService,
        internetUtil: InternetUtil
    ) = DogRepository(dogService, internetUtil)
}