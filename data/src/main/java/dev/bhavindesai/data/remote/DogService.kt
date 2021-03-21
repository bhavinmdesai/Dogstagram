package dev.bhavindesai.data.remote

import dev.bhavindesai.domain.remote.DogBreedsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogService {

    @GET("breeds/list/all")
    suspend fun getAllDogBreeds() : DogBreedsResponse<Map<String, List<String>>>

    @GET("breed/{breedName}/images")
    suspend fun getDogImagesByBreed(
        @Path("breedName")
        breed: String
    ): DogBreedsResponse<List<String>>


    @GET("breed/{breedName}/{subBreedName}/images")
    suspend fun getDogImagesByBreedAndSubBreed(
        @Path("breedName")
        breed: String,

        @Path("subBreedName")
        subBreed: String
    ): DogBreedsResponse<List<String>>

}