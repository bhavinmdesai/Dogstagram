package dev.bhavindesai.dogstagram.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.bhavindesai.dogstagram.ui.fragments.dogbreeds.DogBreedsFragment
import dev.bhavindesai.dogstagram.ui.fragments.dogimages.DogImagesFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindDogBreedsFragment(): DogBreedsFragment

    @ContributesAndroidInjector
    abstract fun bindDogImagesFragment(): DogImagesFragment
}
