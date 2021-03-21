package dev.bhavindesai.dogstagram.ui.fragments.dogimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dev.bhavindesai.dogstagram.R
import dev.bhavindesai.dogstagram.databinding.FragmentDogImagesBinding
import dev.bhavindesai.dogstagram.databinding.LayoutNoInternetBinding
import dev.bhavindesai.dogstagram.ui.fragments.base.BaseFragment
import dev.bhavindesai.viewmodels.DogImagesViewModel
import kotlinx.coroutines.FlowPreview

class DogImagesFragment : BaseFragment() {

    private val args: DogImagesFragmentArgs by navArgs()
    private val viewModel: DogImagesViewModel by lazyViewModel()

    private lateinit var binding: FragmentDogImagesBinding
    private var noInternetBinding: LayoutNoInternetBinding? = null

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

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    @FlowPreview
    private fun observeData() {
        viewModel.showLoader.observe(viewLifecycleOwner) {
            binding.loader.visibility = if (it) View.VISIBLE else View.GONE
            binding.rvDogGrid.visibility = if (it) View.GONE else View.VISIBLE
        }

        viewModel.showNoInternet.observe(viewLifecycleOwner) {
            if (!binding.viewStubNoInternet.isInflated) {
                binding.viewStubNoInternet.setOnInflateListener { _: ViewStub, view: View ->
                    noInternetBinding = DataBindingUtil.bind(view)

                    noInternetBinding?.root?.visibility = if (it) View.VISIBLE else View.GONE
                    noInternetBinding?.btnRetry?.setOnClickListener {
                        viewModel.fetchDogImages(args.breed, args.subBreed)
                    }
                }

                binding.viewStubNoInternet.viewStub?.inflate()
            } else {
                noInternetBinding?.root?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewModel.listOfDogImages.observe(viewLifecycleOwner) {
            binding.rvDogGrid.apply {
                val spacing = resources.getDimension(R.dimen.grid_dog_images_spacing).toInt()
                adapter = DogImageGridAdapter(requireContext(), 3, it)
                addItemDecoration(GridSpacingItemDecoration(
                    3,
                    spacing,
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