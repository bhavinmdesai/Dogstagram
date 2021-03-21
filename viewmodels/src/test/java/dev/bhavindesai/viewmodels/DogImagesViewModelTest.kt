package dev.bhavindesai.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.utils.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.util.concurrent.Executors

class DogImagesViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Test
    @FlowPreview
    fun `verify view model states when data is emitted`() {
        withMocks {
            verify(exactly = 1) {
                observerDogImages.onChanged(any())
                observerShowNoInternet.onChanged(false)
                observerShowLoader.onChanged(false)
            }

            viewModel.listOfDogImages.observeForever {
                Assert.assertNotNull(it)
                Assert.assertEquals(3, it.size)
            }

            viewModel.showLoader.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertFalse(it)
            }
        }
    }

    @Test
    @FlowPreview
    fun `verify view model states when there is no internet`() {
        withMocks(fixture = { Fixture(
            internetUtil = mockk<InternetUtil>().apply {
                withNoInternetConnection()
            }
        )}) {
            verify(exactly = 0) {
                observerDogImages.onChanged(any())
            }

            verify(exactly = 1) {
                observerShowNoInternet.onChanged(true)
                observerShowLoader.onChanged(false)
            }

            viewModel.showLoader.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertTrue(it)
            }
        }
    }

    @Test
    @FlowPreview
    fun `verify view model states when data is emitted with no sub-breed`() {
        withMocks(fixture = { Fixture(
            subBreed = null,
        )}) {
            verify(exactly = 1) {
                observerDogImages.onChanged(any())
                observerShowNoInternet.onChanged(false)
                observerShowLoader.onChanged(false)
            }

            viewModel.listOfDogImages.observeForever {
                Assert.assertNotNull(it)
                Assert.assertEquals(3, it.size)
            }

            viewModel.showLoader.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertFalse(it)
            }
        }
    }

    @Test
    @FlowPreview
    fun `verify view model states when there is no internet with no sub-breed`() {
        withMocks(fixture = { Fixture(
            subBreed = null,
            internetUtil = mockk<InternetUtil>().apply {
                withNoInternetConnection()
            }
        )}) {
            verify(exactly = 0) {
                observerDogImages.onChanged(any())
            }

            verify(exactly = 1) {
                observerShowNoInternet.onChanged(true)
                observerShowLoader.onChanged(false)
            }

            viewModel.showLoader.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertTrue(it)
            }
        }
    }

    @FlowPreview
    private fun withMocks(fixture: () -> Fixture = { Fixture() }, test: Fixture.() -> Unit) {
        val f = fixture()
        f.apply(test)
    }

    @FlowPreview
    private data class Fixture(
        val breed: String = "someBreed",
        val subBreed: String? = "someSubBreed",
        val dogService: DogService = mockk<DogService>().apply {
            if (subBreed != null)
                withSomeDogImagesByBreedAndSubBreed()
            else
                withSomeDogImagesByBreed()
        },
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply {
            withInternetConnection()
        },
        val dogRepository: DogRepository = DogRepository(dogService, internetUtil),
        val viewModel: DogImagesViewModel = DogImagesViewModel(dogRepository),
        val observerDogImages: Observer<List<String>> = mockk(),
        val observerShowNoInternet: Observer<Boolean> = mockk(),
        val observerShowLoader: Observer<Boolean> = mockk(),
    ) {
        init {
            viewModel.fetchDogImages(breed, subBreed)

            every { observerDogImages.onChanged(any()) } just runs
            viewModel.listOfDogImages.observeForever(observerDogImages)

            every { observerShowNoInternet.onChanged(any()) } just runs
            viewModel.showNoInternet.observeForever(observerShowNoInternet)

            every { observerShowLoader.onChanged(any()) } just runs
            viewModel.showLoader.observeForever(observerShowLoader)
        }
    }

    @ObsoleteCoroutinesApi @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}