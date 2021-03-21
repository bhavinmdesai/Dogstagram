package dev.bhavindesai.viewmodels

import androidx.lifecycle.*
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.domain.remote.Breed
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedsViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val mldListOfDogBreed = MutableLiveData<List<Breed>>()
    val listOfDogBreed: LiveData<List<Breed>> = mldListOfDogBreed

    @FlowPreview
    fun fetchDogBreeds() {
        viewModelScope.launch {
            dogRepository.getDogBreeds().collect {
                mldListOfDogBreed.value = it
            }
        }
    }
}