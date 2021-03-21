package dev.bhavindesai.data.repositories

import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.remote.DogBreedsResponse
import dev.bhavindesai.domain.remote.toDomain
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DogRepository(
    private val dogService: DogService,
    private val internetUtil: InternetUtil,
) {

    @FlowPreview
    suspend fun getDogBreeds() = flow {
        rdsDogBreeds.getRemoteData(Unit).collect {
            emit(it.toDomain().toList())
        }
    }

    fun getDogImages(breed: String, subBreed: String?) =
        rdsDogImages.getRemoteData(breed to subBreed)
        .filterNotNull()
        .map { it.message }

    private val rdsDogBreeds = object : RemoteDataSource<Unit, DogBreedsResponse<Map<String, List<String>>>> {
        override fun getRemoteData(requestData: Unit) = flow {
            emit(dogService.getAllDogBreeds())
        }
    }

    private val rdsDogImages = object : RemoteDataSource<Pair<String, String?>, DogBreedsResponse<List<String>>> {
        override fun getRemoteData(requestData: Pair<String, String?>) = flow {
            val subBreed = requestData.second

            if (internetUtil.isInternetOn()) {
                emit(
                    if (subBreed == null) {
                        dogService.getDogImagesByBreed(requestData.first)
                    } else {
                        dogService.getDogImagesByBreedAndSubBreed(
                            requestData.first,
                            subBreed
                        )
                    }
                )
            } else {
                emit(null)
            }
        }
    }
}



