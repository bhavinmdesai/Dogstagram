package dev.bhavindesai.domain.remote

data class DogBreedsResponse<T>(
    val message: T,
    val status: String
)

inline class BreedId(val v: String) {
    operator fun invoke() = v
}

inline class SubBreedId(val v: String) {
    operator fun invoke() = v
}

data class Breed(
    val id: BreedId,
    val subBreeds: List<SubBreed>?
)

data class SubBreed(
    val parentId: BreedId,
    val id: SubBreedId
)

fun DogBreedsResponse<Map<String, List<String>>>.toDomain() =
    message
        .entries
        .map { (breed, subBreeds) ->
            val breedId = BreedId(breed)
            val subs = subBreeds
                .map {
                    SubBreed(
                        id = SubBreedId(it),
                        parentId = breedId
                    )
                }
                .let {
                    if (it.isEmpty()) null
                    else it
                }
            Breed(
                id = breedId,
                subBreeds = subs
            )
        }.toSet()