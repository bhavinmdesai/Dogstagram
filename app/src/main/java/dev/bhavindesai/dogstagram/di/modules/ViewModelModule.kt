package dev.bhavindesai.dogstagram.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import dev.bhavindesai.dogstagram.di.DaggerViewModelFactory
import dev.bhavindesai.viewmodels.DogBreedsViewModel
import dev.bhavindesai.viewmodels.DogImagesViewModel
import kotlin.reflect.KClass

/** Annotation definition for manually creating ViewModel providers / bindings */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @[Binds IntoMap ViewModelKey(DogBreedsViewModel::class)]
    internal abstract fun bindDogBreedsViewModel(viewModel: DogBreedsViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(DogImagesViewModel::class)]
    internal abstract fun bindDogImagesViewModel(viewModel: DogImagesViewModel): ViewModel
}
