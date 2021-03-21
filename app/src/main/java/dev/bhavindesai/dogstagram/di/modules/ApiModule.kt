package dev.bhavindesai.dogstagram.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.remote.DogService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @[Provides Singleton]
    fun providesDogService(retrofit: Retrofit) = retrofit.create(DogService::class.java)
}
