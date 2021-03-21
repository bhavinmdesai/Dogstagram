package dev.bhavindesai.data.remote

import dev.bhavindesai.domain.remote.DogBreedsResponse
import retrofit2.http.GET

interface DogService {

    @GET("breeds/list/all")
    suspend fun getAllDogBreeds() : DogBreedsResponse

}