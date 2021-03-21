package dev.bhavindesai.dogstagram.ui.fragments.dogimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.bhavindesai.dogstagram.R
import dev.bhavindesai.dogstagram.databinding.FragmentDogImagesBinding
import dev.bhavindesai.dogstagram.ui.fragments.base.BaseFragment
import dev.bhavindesai.viewmodels.DogImagesViewModel
import kotlinx.coroutines.FlowPreview
import kotlin.math.roundToInt

class DogImagesFragment : BaseFragment() {

    private val args: DogImagesFragmentArgs by navArgs()
    private lateinit var binding: FragmentDogImagesBinding
    private val viewModel: DogImagesViewModel by lazyViewModel()

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchDogImages(args.breed, args.subBreed)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        viewModel.listOfDogImages.observe(viewLifecycleOwner) {
            binding.rvDogGrid.apply {
                adapter = DogImageGridAdapter(it)
                addItemDecoration(GridSpacingItemDecoration(
                    3,
                    resources.getDimension(R.dimen.grid_dog_images_spacing).toInt(),
                    true
                ))
                layoutManager = GridLayoutManager(
                    requireContext(),
                    3,
                )
            }
        }
    }

}