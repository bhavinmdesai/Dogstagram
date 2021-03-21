package dev.bhavindesai.dogstagram.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dev.bhavindesai.dogstagram.di.modules.*
import dev.bhavindesai.dogstagram.ui.DogstagramApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityModule::class,
        AndroidInjectionModule::class,
        ApiModule::class,
        FragmentModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        RepositoriesModule::class,
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: DogstagramApplication)
}
