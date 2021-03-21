package dev.bhavindesai.viewmodels

import androidx.lifecycle.*
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.domain.remote.Breed
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedsViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val mldListOfDogBreed = MutableLiveData<List<Breed>>()
    val listOfDogBreed: LiveData<List<Breed>> = mldListOfDogBreed

    private val mldShowNoInternet = MutableLiveData<Boolean>()
    val showNoInternet: LiveData<Boolean> = mldShowNoInternet

    private val mldShowLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = mldShowLoader

    @FlowPreview
    fun fetchDogBreeds() {
        viewModelScope.launch {
            dogRepository
                .getDogBreeds()
                .onStart {
                    mldShowLoader.value = true
                    mldShowNoInternet.value = false
                }
                .catch { mldShowNoInternet.value = true }
                .onCompletion { mldShowLoader.value = false }
                .collect { mldListOfDogBreed.value = it }
        }
    }
}