package dev.bhavindesai.dogstagram.ui.fragments.dogbreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bhavindesai.dogstagram.databinding.FragmentDogBreedsBinding
import dev.bhavindesai.dogstagram.ui.fragments.base.BaseFragment
import dev.bhavindesai.viewmodels.DogBreedsViewModel
import kotlinx.coroutines.FlowPreview

class DogBreedsFragment : BaseFragment(), DogBreedClickListener {

    private val viewModel: DogBreedsViewModel by lazyViewModel()
    private lateinit var binding: FragmentDogBreedsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    @FlowPreview
    private fun observeData() {
        viewModel.listOfDogBreed.observe(viewLifecycleOwner) {
            val itemList = mutableListOf<DogBreedItem>()

            itemList.addAll(it.map { breed ->
                val item = DogBreedItem(0, breed.id.invoke())

                breed.subBreeds?.let { subBreeds ->
                    item.addChildren(subBreeds.map { subBreed ->
                        DogSubBreedItem(1, breed.id.invoke(), subBreed.id.invoke())
                    })
                }

                return@map item
            })

            binding.rvList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                openTill(0, 1)
                setToggleItemOnClick(false)
                adapter = DogBreedsAdapter(itemList, this).apply {
                    dogBreedClickListener = this@DogBreedsFragment
                }
            }
        }
    }

    override fun onDogBreedClick(breed: DogBreedItem) {
        findNavController().navigate(DogBreedsFragmentDirections.actionDogBreedsFragmentToDogImagesFragment(
            breed.breedName,
            (breed as? DogSubBreedItem)?.subBreedName
        ))
    }
}