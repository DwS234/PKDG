package pl.zgora.uz.wiea.pkdg.repetition.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepetitionControllerIntegrationTest {

    private static final String REPETITIONS_BY_USERNAME_PATH = "/api/repetitions/{username}";

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

    // TODO: Tests

    private void clearDatabase() {
        userRepository.deleteAllInBatch();
        repetitionRepository.deleteAllInBatch();
    }
}
