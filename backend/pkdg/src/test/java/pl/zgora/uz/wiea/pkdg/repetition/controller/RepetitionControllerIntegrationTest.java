package pl.zgora.uz.wiea.pkdg.repetition.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zgora.uz.wiea.pkdg.IntegrationTestPostgresqlContainer;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

import static pl.zgora.uz.wiea.pkdg.TestUtils.PROFILE_TEST;

@Testcontainers
@ActiveProfiles(PROFILE_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepetitionControllerIntegrationTest {

    private static final String REPETITIONS_BY_USERNAME_AND_WORD_ID_PATH = "/api/users/{username}/words/{wordId}/repetitions";

    @Container
    private static final PostgreSQLContainer<IntegrationTestPostgresqlContainer> postgreSQLContainer =
            IntegrationTestPostgresqlContainer.getInstance();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RepetitionRepository repetitionRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEachTest() {
        clearDatabase();

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    void afterEachTest() {
        clearDatabase();
    }

    @Test
    void shouldCreateRepetition() {
        // Given
        System.out.println("dupa");
        // When

        // Then
    }

    private void clearDatabase() {
        userRepository.deleteAllInBatch();
        repetitionRepository.deleteAllInBatch();
    }
}
