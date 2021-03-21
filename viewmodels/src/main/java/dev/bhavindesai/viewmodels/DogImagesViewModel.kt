package dev.bhavindesai.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bhavindesai.data.repositories.DogRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogImagesViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val mldListOfDogImages = MutableLiveData<List<String>>()
    val listOfDogImages: LiveData<List<String>> = mldListOfDogImages

    private val mldShowNoInternet = MutableLiveData<Boolean>()
    val showNoInternet: LiveData<Boolean> = mldShowNoInternet

    private val mldShowLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = mldShowLoader

    @FlowPreview
    fun fetchDogImages(breed:String, subBreed: String?) {
        mldShowLoader.value = true
        mldShowNoInternet.value = false

        viewModelScope.launch {
            dogRepository.getDogImages(breed, subBreed)
                .onStart {
                    mldShowLoader.value = true
                    mldShowNoInternet.value = false
                }
                .catch { mldShowNoInternet.value = true }
                .onCompletion { mldShowLoader.value = false }
                .collect { mldListOfDogImages.value = it }
        }
    }
}