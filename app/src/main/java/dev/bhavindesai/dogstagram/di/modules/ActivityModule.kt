package dev.bhavindesai.dogstagram.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.bhavindesai.dogstagram.ui.main.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
