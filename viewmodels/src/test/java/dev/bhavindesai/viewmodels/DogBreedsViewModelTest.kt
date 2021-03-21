package dev.bhavindesai.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.remote.Breed
import dev.bhavindesai.utils.withInternetConnection
import dev.bhavindesai.utils.withNoInternetConnection
import dev.bhavindesai.utils.withSomeDogBreeds
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.util.concurrent.Executors

class DogBreedsViewModelTest {

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
                observerDogBreeds.onChanged(any())
                observerShowNoInternet.onChanged(false)
                observerShowLoader.onChanged(false)
            }

            viewModel.listOfDogBreed.observeForever {
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
                observerDogBreeds.onChanged(any())
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
        val dogService: DogService = mockk<DogService>().apply {
            withSomeDogBreeds()
        },
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply {
            withInternetConnection()
        },
        val dogRepository: DogRepository = DogRepository(dogService, internetUtil),
        val viewModel: DogBreedsViewModel = DogBreedsViewModel(dogRepository),
        val observerDogBreeds: Observer<List<Breed>> = mockk(),
        val observerShowNoInternet: Observer<Boolean> = mockk(),
        val observerShowLoader: Observer<Boolean> = mockk(),
    ) {
        init {
            viewModel.fetchDogBreeds()

            every { observerDogBreeds.onChanged(any()) } just runs
            viewModel.listOfDogBreed.observeForever(observerDogBreeds)

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