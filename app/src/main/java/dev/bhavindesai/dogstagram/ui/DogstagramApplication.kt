package dev.bhavindesai.dogstagram.ui

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.dogstagram.di.DaggerAppComponent
import javax.inject.Inject

class DogstagramApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
        InternetUtil.init(this)

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector() = androidInjector
}