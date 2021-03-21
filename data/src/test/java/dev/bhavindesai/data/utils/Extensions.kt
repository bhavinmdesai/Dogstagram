package dev.bhavindesai.data.utils

import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.remote.DogBreedsResponse
import io.mockk.coEvery
import io.mockk.every

fun InternetUtil.withInternetConnection() {
    every { isInternetOn() } returns true
}

fun InternetUtil.withNoInternetConnection() {
    every { isInternetOn() } returns false
}

fun DogService.withSomeDogBreeds() {
    coEvery { getAllDogBreeds() } returns DogBreedsResponse(
        status = "true",
        message = mapOf(
            "appenzeller" to emptyList(),
            "australian" to listOf("shepherd"),
            "basenji" to emptyList(),
        )
    )
}

fun DogService.withSomeDogImagesByBreed() {
    coEvery { getDogImagesByBreed(any()) } returns DogBreedsResponse(
        status = "true",
        message = listOf("url_a", "url_b", "url_c")
    )
}

fun DogService.withSomeDogImagesByBreedAndSubBreed() {
    coEvery { getDogImagesByBreedAndSubBreed(any(), any()) } returns DogBreedsResponse(
        status = "true",
        message = listOf("url_a", "url_b", "url_c")
    )
}