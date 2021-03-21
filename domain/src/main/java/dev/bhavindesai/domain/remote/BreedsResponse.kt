package dev.bhavindesai.domain.remote

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

inline class BreedId(val v: String) {
    operator fun invoke() = v
}

inline class SubBreedId(val v: String) {
    operator fun invoke() = v
}

data class FullBreedId(val parentId: BreedId, val subId: SubBreedId?)

data class Breed(
    val id: BreedId,
    val subBreeds: List<SubBreed>?
) {
    val fullId = FullBreedId(id, null)
}

data class SubBreed(val parentId: BreedId, val id: SubBreedId) {
    val fullId = FullBreedId(parentId, id)
}

fun DogBreedsResponse.toDomain() =
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