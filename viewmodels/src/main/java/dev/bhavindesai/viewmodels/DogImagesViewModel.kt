package dev.bhavindesai.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bhavindesai.data.repositories.DogRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogImagesViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val mldListOfDogImages = MutableLiveData<List<String>>()
    val listOfDogImages: LiveData<List<String>> = mldListOfDogImages

    @FlowPreview
    fun fetchDogImages(breed:String, subBreed: String?) {
        viewModelScope.launch {
            dogRepository.getDogImages(breed, subBreed).collect {
                mldListOfDogImages.value = it
            }
        }
    }
}