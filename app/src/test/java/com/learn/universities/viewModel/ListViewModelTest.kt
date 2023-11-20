import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.universities.model.data.UniversityListData
import com.learn.universities.model.repository.APIService
import com.learn.universities.model.repository.MainRepository
import com.learn.universities.model.repository.Repository
import com.learn.universities.viewModel.FakeRepository
import com.learn.universities.viewModel.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


   @Mock
    private lateinit var repository: MainRepository


    private lateinit var viewModel: ListViewModel

    @Mock
    private lateinit var retrofitService: APIService

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)
        //retrofitService = APIService.getInstance()
        repository = FakeRepository()
        viewModel = ListViewModel(repository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    suspend fun exampleFetchData(): String {
        delay(1000L)
        return "Test"
    }

    @Test
    fun dataFetch() = runTest {
        val data = exampleFetchData()
        assertEquals("Test", data)
    }

    @Test
    fun settingMainDispatcher() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {

            viewModel.getPosts() // Uses testDispatcher, runs its coroutine eagerly
            advanceUntilIdle()
            assertEquals(4, viewModel.message.value)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
     fun fetchData_success() = runTest{

        //val testData = mutableListOf(UniversityListData("Marywood University", "United States"))
       // `when`(repository.downloadData()).thenReturn(testData)
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {


            viewModel.getPosts()
            advanceUntilIdle()
            // val observedData = viewModel.universities.value
            repository.emit(1)
            viewModel.universities.collect { universities ->
                assert(universities[0].name == "Marywood University")
            }
        }finally {
            Dispatchers.resetMain()
        }

    }

    @Test
    fun fetchData() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val data = mutableListOf(UniversityListData("Marywood University", "United States"))

       // viewModel.universities.apply { data }
        val job = launch {
            viewModel.getPosts()
            advanceUntilIdle() // Advance the dispatcher
           // viewModel.universities.collect()
        }

        assertEquals(1, viewModel.universities.value.size) // Can assert initial value

        //repository.downloadData()

        viewModel.universities.collect { universities ->
            assert(universities[0].name == "Marywood University")
        }



        job.cancel() // Cancel the coroutine
    }


   /* @Test
    fun `fetchData updates StateFlow with data`() = runTest {
        val data = listOf(UniversityListData("Marywood University", "United States"))
        whenever(repository.downloadData()).thenReturn(data)

        viewModel.getPosts()

        val collectedData = mutableListOf<String>()
        viewModel.universities.collect {
            collectedData.add(it.toString())
        }

        assert(collectedData.size == 1)

    }*/





}


