package dev.bhavindesai.dogstagram.ui.fragments.dogbreeds

import com.multilevelview.models.RecyclerViewItem

open class DogBreedItem(
        level:Int = 0,
        val breedName: String,
) : RecyclerViewItem(level)

class DogSubBreedItem(
        level:Int = 0,
        breedName: String,
        val subBreedName: String
) : DogBreedItem(level, breedName)