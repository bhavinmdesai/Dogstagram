package dev.bhavindesai.dogstagram.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.dogstagram.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @[Provides Singleton]
    fun providesInternetUtil(application: Application): InternetUtil = InternetUtil.apply { init(application) }

    @[Provides Singleton]
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun providesOkHttp(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
}
