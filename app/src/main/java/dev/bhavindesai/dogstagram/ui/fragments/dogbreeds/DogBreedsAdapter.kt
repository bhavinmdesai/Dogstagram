package dev.bhavindesai.dogstagram.ui.fragments.dogbreeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.multilevelview.MultiLevelAdapter
import com.multilevelview.MultiLevelRecyclerView
import dev.bhavindesai.dogstagram.R
import dev.bhavindesai.dogstagram.databinding.ListItemDogBreedBinding

class DogBreedsAdapter(
        private val items: MutableList<DogBreedItem>,
        private val multiLevelRecyclerView: MultiLevelRecyclerView
    ) : MultiLevelAdapter(items) {

        var dogBreedClickListener: DogBreedClickListener? = null

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): RecyclerView.ViewHolder = DogBreedViewHolder(
                ListItemDogBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, index: Int) {
            val dogBreedViewHolder = viewHolder as DogBreedViewHolder
            dogBreedViewHolder.bind(items[index], multiLevelRecyclerView, dogBreedClickListener)
        }

        class DogBreedViewHolder(
                private val binding: ListItemDogBreedBinding,
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(breed: DogBreedItem,
                     multiLevelRecyclerView: MultiLevelRecyclerView,
                     dogBreedClickListener: DogBreedClickListener?) {

                binding.ivNext.rotation = if (breed.hasChildren() && breed.children.isNotEmpty()) {
                    -90F
                } else {
                    0F
                }

                val params = binding.txtBreedName.layoutParams as ConstraintLayout.LayoutParams
                if (breed.level == 0) {
                    binding.txtBreedName.text = breed.breedName
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.silver))
                    params.marginStart = binding.root.context.resources.getDimension(R.dimen.breed_start_margin).toInt()
                } else {
                    val subBreed = breed as DogSubBreedItem
                    binding.txtBreedName.text = subBreed.subBreedName
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.alto))
                    params.marginStart = binding.root.context.resources.getDimension(R.dimen.subbreed_start_margin).toInt()
                }

                binding.txtBreedName.setOnClickListener {
                    if (breed.hasChildren() && breed.children.isNotEmpty()) {
                        binding.ivNext.animate().rotation(if (breed.isExpanded) -90F else 90F).start()
                        multiLevelRecyclerView.toggleItemsGroup(adapterPosition)
                    } else {
                        dogBreedClickListener?.onDogBreedClick(breed)
                    }
                }
            }
        }
    }