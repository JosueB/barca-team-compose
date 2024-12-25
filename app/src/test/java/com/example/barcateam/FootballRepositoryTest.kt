import com.example.barcateam.network.NetworkResult
import com.example.barcateam.network.api.FootballAPI
import com.example.barcateam.network.model.ApiResponse
import com.example.barcateam.network.model.Birth
import com.example.barcateam.network.model.Paging
import com.example.barcateam.network.model.Parameters
import com.example.barcateam.network.model.Player
import com.example.barcateam.network.model.PlayerResponse
import com.example.barcateam.network.repos.FootballRepository
import com.example.barcateam.network.repos.FootballRepositoryImpl
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

private const val PLAYER_ID_MOCK = 8253L

@OptIn(ExperimentalCoroutinesApi::class)
class FootballRepositoryImplTest {

    private lateinit var apiService: FootballAPI
    private lateinit var footballRepository: FootballRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        apiService = mockk()
        footballRepository = FootballRepositoryImpl(apiService, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(apiService)
    }

    // Reusable mock response body
    private fun createMockResponseBody(): ApiResponse<PlayerResponse> {
        return ApiResponse(
            get = "Sheela",
            parameters = Parameters(
                team = "Connor", playerId = null, season = "Dorinda"
            ),
            errors = listOf(),
            results = 7101,
            paging = Paging(current = 9987, total = 8838),
            response = listOf(
                PlayerResponse(
                    player = Player(
                        id = PLAYER_ID_MOCK,
                        name = "Taya",
                        firstname = "Robbyn",
                        lastname = "Oscar",
                        age = 4720,
                        birth = Birth(date = "Teodoro", place = null, country = "Evan"),
                        nationality = "Solomon",
                        height = null,
                        weight = null,
                        injured = true,
                        photo = "Aracelis"
                    ),
                    statistics = listOf()
                )
            )
        )
    }

    private fun createMockResponse(): Response<ApiResponse<PlayerResponse>> {
        return Response.success(createMockResponseBody())
    }

    @Test
    fun `getBarcaPlayers should return success result`() = runTest {
        val mockResponse = createMockResponse()
        coEvery { apiService.getBarcaPlayers() } returns mockResponse

        val result = footballRepository.getBarcaPlayers()

        assertNotNull(result)
        assertTrue(result is NetworkResult.Success)
        coVerify { apiService.getBarcaPlayers() }
    }

    @Test
    fun `getStatsForPlayer should return success result`() = runTest {
        val mockResponse = createMockResponse()

        coEvery { apiService.getPlayerStats(PLAYER_ID_MOCK) } returns mockResponse

        // Invoke the method
        val result = footballRepository.getStatsForPlayer(PLAYER_ID_MOCK)

        // Assertions
        assertNotNull(result)
        assertTrue(result is NetworkResult.Success)
        coVerify { apiService.getPlayerStats(PLAYER_ID_MOCK) }
    }

    @Test
    fun `getBarcaPlayers should handle API failure`() = runTest {
        // Mock error response
        val exception = RuntimeException("API error")
        coEvery { apiService.getBarcaPlayers() } throws exception

        // Invoke the method
        val result = footballRepository.getBarcaPlayers()

        // Assertions
        assertNotNull(result)
        assertTrue(result is NetworkResult.Error)
        coVerify { apiService.getBarcaPlayers() }
    }

    @Test
    fun `getStatsForPlayer should handle API failure`() = runTest {
        // Mock error response
        val mockPlayerId = 1L
        val exception = RuntimeException("API error")
        coEvery { apiService.getPlayerStats(mockPlayerId) } throws exception

        // Invoke the method
        val result = footballRepository.getStatsForPlayer(mockPlayerId)

        // Assertions
        assertNotNull(result)
        assertTrue(result is NetworkResult.Error)
        coVerify { apiService.getPlayerStats(mockPlayerId) }
    }
}