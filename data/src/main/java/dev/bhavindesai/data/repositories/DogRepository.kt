package dev.bhavindesai.data.repositories

import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.remote.Breed
import dev.bhavindesai.domain.remote.DogBreedsResponse
import dev.bhavindesai.domain.remote.toDomain
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

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

    private val rdsDogBreeds = object : RemoteDataSource<Unit, DogBreedsResponse> {
        override fun getRemoteData(requestData: Unit) = flow {
            emit(dogService.getAllDogBreeds())
        }
    }
}



