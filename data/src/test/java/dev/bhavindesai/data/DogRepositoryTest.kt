package dev.bhavindesai.data

import dev.bhavindesai.data.remote.DogService
import dev.bhavindesai.data.repositories.DogRepository
import dev.bhavindesai.data.utils.*
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors

class DogRepositoryTest {

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Test
    @FlowPreview
    fun `verify internet connectivity check for get dog breeds`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogBreeds() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogBreeds().collect {
            verify(exactly = 1) { internetUtil.isInternetOn() }
        }
    }

    @Test
    @FlowPreview
    fun `verify internet connectivity check for get dog images by breed`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogImagesByBreed() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogImages("breed").collect {
            verify(exactly = 1) { internetUtil.isInternetOn() }
        }
    }

    @Test
    @FlowPreview
    fun `verify internet connectivity check for get dog images by breed and sub-breed`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogImagesByBreedAndSubBreed() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogImages("breed", "sub-breed").collect {
            verify(exactly = 1) { internetUtil.isInternetOn() }
        }
    }

    @Test
    @FlowPreview
    fun `verify not null data is emitted for get dog breeds`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogBreeds() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogBreeds().collect {
            Assert.assertNotNull(it)
            Assert.assertEquals(3, it.size)
        }
    }

    @Test
    @FlowPreview
    fun `verify not null data is emitted for get dog images by breeds`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogImagesByBreed() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogImages("breed").collect {
            Assert.assertNotNull(it)
            Assert.assertEquals(3, it.size)
        }
    }

    @Test
    @FlowPreview
    fun `verify not null data is emitted for get dog images by breeds and sub-breed`() = runBlocking {
        val dogService: DogService = mockk<DogService>().apply { withSomeDogImagesByBreedAndSubBreed() }
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        dogRepository.getDogImages("breed", "sub-breed").collect {
            Assert.assertNotNull(it)
            Assert.assertEquals(3, it.size)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @FlowPreview
    fun `verify error is emitted for get dog breeds when no internet`() = runBlockingTest {
        val dogService: DogService = mockk()
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withNoInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        val exceptionHandler = TestCoroutineExceptionHandler()
        launch(exceptionHandler) { dogRepository.getDogBreeds().collect() }

        Assert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(IllegalStateException::class.java)
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    @FlowPreview
    fun `verify error is emitted for get images by dog breeds when no internet`() = runBlockingTest {
        val dogService: DogService = mockk()
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withNoInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        val exceptionHandler = TestCoroutineExceptionHandler()
        launch(exceptionHandler) { dogRepository.getDogImages("breed").collect() }

        Assert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(IllegalStateException::class.java)
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    @FlowPreview
    fun `verify error is emitted for get images by dog breeds and sub breed when no internet`() = runBlockingTest {
        val dogService: DogService = mockk()
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply { withNoInternetConnection() }
        val dogRepository = DogRepository(dogService, internetUtil)

        val exceptionHandler = TestCoroutineExceptionHandler()
        launch(exceptionHandler) { dogRepository.getDogImages("breed", "sub-breed").collect() }

        Assert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(IllegalStateException::class.java)
        )
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}