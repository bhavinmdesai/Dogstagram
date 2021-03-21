package dev.bhavindesai.dogstagram.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.bhavindesai.dogstagram.ui.fragments.dogbreeds.DogBreedsFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindDogBreedsFragment(): DogBreedsFragment
}
