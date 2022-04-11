package pl.zgora.uz.wiea.pkdg.repetition.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zgora.uz.wiea.pkdg.IntegrationTestPostgresqlContainer;
import pl.zgora.uz.wiea.pkdg.exception.model.ApiError;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.repetition.model.RepetitionWithWordBasic;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static pl.zgora.uz.wiea.pkdg.AssertUtils.assertRepetition;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildRepetition;
import static pl.zgora.uz.wiea.pkdg.TestUtils.*;

@Testcontainers
@ActiveProfiles(PROFILE_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepetitionControllerIntegrationTest {

    private static final String REPETITIONS_BY_USERNAME_AND_WORD_ID_PATH = "/api/users/{username}/words/{wordId}/repetitions";
    private static final String REPETITIONS_BY_USERNAME_PATH = "/api/users/{username}/repetitions";
    private static final String REPETITIONS_BY_REPETITION_ID = "/api/repetitions/{repetitionId}";

    @Container
    private static final PostgreSQLContainer<IntegrationTestPostgresqlContainer> postgreSQLContainer =
            IntegrationTestPostgresqlContainer.getInstance();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RepetitionRepository repetitionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordRepository wordRepository;

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
    void shouldCreateRepetition() throws Exception {
        // Given
        val createdUser = createUserInDatabase(userRepository, "username", "password", "email@email.com");
        val createdWord = createWordInDatabase(wordRepository, "entry", "definition", "sentence");

        val nextDate = LocalDate.now().plusDays(1);
        val easiness = 3.5;
        val consecutiveCorrectAnswers = 5;
        val timesSeen = 7;
        val lastIntervalDays = 2;
        val repetition = buildRepetition(nextDate, easiness, consecutiveCorrectAnswers, timesSeen, lastIntervalDays);

        val uri = fromPath(REPETITIONS_BY_USERNAME_AND_WORD_ID_PATH).buildAndExpand(createdUser.getUsername(), createdWord.getWordId()).toUri();
        val request = buildPostRequest(uri, repetition);

        // When
        val result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        val createdRepetition = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Repetition.class);

        // Then
        assertRepetition(createdRepetition, nextDate, easiness, consecutiveCorrectAnswers, timesSeen, lastIntervalDays);
    }

    @Test
    void shouldReturnErrorWhenCreatingRepetitionAndUserWithGivenUsernameDoesNotExist() throws Exception {
        // Given
        val nextDate = LocalDate.now().plusDays(1);
        val easiness = 3.5;
        val consecutiveCorrectAnswers = 5;
        val timesSeen = 7;
        val lastIntervalDays = 2;
        val repetition = buildRepetition(nextDate, easiness, consecutiveCorrectAnswers, timesSeen, lastIntervalDays);

        val uri = fromPath(REPETITIONS_BY_USERNAME_AND_WORD_ID_PATH).buildAndExpand("nonExistentUsername", "notNeededWordId").toUri();
        val request = buildPostRequest(uri, repetition);

        // When
        val result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
        val apiError = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), ApiError.class);

        // Then
        assertThat(apiError).isNotNull();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnErrorWhenCreatingRepetitionAndWordWithGivenWordIdDoesNotExist() throws Exception {
        // Given
        val createdUser = createUserInDatabase(userRepository, "username", "password", "email@email.com");

        val nextDate = LocalDate.now().plusDays(1);
        val easiness = 3.5;
        val consecutiveCorrectAnswers = 5;
        val timesSeen = 7;
        val lastIntervalDays = 2;
        val repetition = buildRepetition(nextDate, easiness, consecutiveCorrectAnswers, timesSeen, lastIntervalDays);

        val uri = fromPath(REPETITIONS_BY_USERNAME_AND_WORD_ID_PATH).buildAndExpand(createdUser.getUsername(), "nonExistentWordId").toUri();
        val request = buildPostRequest(uri, repetition);

        // When
        val result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
        val apiError = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), ApiError.class);

        // Then
        assertThat(apiError).isNotNull();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnRepetitionsForGivenUsername() throws Exception {
        // Given
        val createdUser = createUserInDatabase(userRepository, "username", "password", "email@email.com");

        val createdWord1 = createWordInDatabase(wordRepository, "entry1", "definition1", "sentence1");
        val createdWord2 = createWordInDatabase(wordRepository, "entry2", "definition2", "sentence2");

        val nextDate1 = LocalDate.now().plusDays(1);
        val nextDate2 = LocalDate.now().plusDays(2);
        val easiness1 = 3.5;
        val easiness2 = 5;
        val consecutiveCorrectAnswers1 = 5;
        val consecutiveCorrectAnswers2 = 3;
        val timesSeen1 = 7;
        val timesSeen2 = 3;
        val lastIntervalDays1 = 2;
        val lastIntervalDays2 = 4;
        createRepetitionInDatabase(repetitionRepository, nextDate1, easiness1, consecutiveCorrectAnswers1, timesSeen1, lastIntervalDays1, createdUser, createdWord1);
        createRepetitionInDatabase(repetitionRepository, nextDate2, easiness2, consecutiveCorrectAnswers2, timesSeen2, lastIntervalDays2, createdUser, createdWord2);

        val uri = fromPath(REPETITIONS_BY_USERNAME_PATH).buildAndExpand(createdUser.getUsername()).toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val returnedRepetitions = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<RepetitionWithWordBasic>>() {
        });

        // Then
        assertThat(returnedRepetitions).hasSize(2);
        assertRepetition(returnedRepetitions.get(0).getRepetition(), nextDate1, easiness1, consecutiveCorrectAnswers1, timesSeen1, lastIntervalDays1);
        assertRepetition(returnedRepetitions.get(1).getRepetition(), nextDate2, easiness2, consecutiveCorrectAnswers2, timesSeen2, lastIntervalDays2);
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoRepetitionsForGivenUsername() throws Exception {
        // Given
        val createdUser = createUserInDatabase(userRepository, "username", "password", "email@email.com");

        val createdWord1 = createWordInDatabase(wordRepository, "entry1", "definition1", "sentence1");
        val createdWord2 = createWordInDatabase(wordRepository, "entry2", "definition2", "sentence2");

        val nextDate1 = LocalDate.now().plusDays(1);
        val nextDate2 = LocalDate.now().plusDays(2);
        val easiness1 = 3.5;
        val easiness2 = 5;
        val consecutiveCorrectAnswers1 = 5;
        val consecutiveCorrectAnswers2 = 3;
        val timesSeen1 = 7;
        val timesSeen2 = 3;
        val lastIntervalDays1 = 2;
        val lastIntervalDays2 = 4;
        createRepetitionInDatabase(repetitionRepository, nextDate1, easiness1, consecutiveCorrectAnswers1, timesSeen1, lastIntervalDays1, createdUser, createdWord1);
        createRepetitionInDatabase(repetitionRepository, nextDate2, easiness2, consecutiveCorrectAnswers2, timesSeen2, lastIntervalDays2, createdUser, createdWord2);

        val uri = fromPath(REPETITIONS_BY_USERNAME_PATH).buildAndExpand("nonExistentUsername").toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val returnedRepetitions = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Repetition>>() {
        });

        // Then
        assertThat(returnedRepetitions).isEmpty();
    }

    @Test
    void shouldUpdateRepetition() throws Exception {
        // Given
        val createdUser = createUserInDatabase(userRepository, "username", "password", "email@email.com");

        val createdWord = createWordInDatabase(wordRepository, "entry", "definition", "sentence");

        val nextDate = LocalDate.now().plusDays(1);
        val easiness = 3.5;
        val consecutiveCorrectAnswers = 5;
        val timesSeen = 7;
        val lastIntervalDays = 2;
        val createdRepetition =
                createRepetitionInDatabase(repetitionRepository, nextDate, easiness, consecutiveCorrectAnswers, timesSeen, lastIntervalDays, createdUser, createdWord);

        val updatedNextDate = LocalDate.now().plusDays(2);
        val updatedConsecutiveCorrectAnswers = 8;
        val updatedTimesSeen = 9;
        val updatedLastIntervalDays = 6;
        val repetitionToBeUpdated = buildRepetition(updatedNextDate, easiness, updatedConsecutiveCorrectAnswers, updatedTimesSeen, updatedLastIntervalDays);

        val uri = fromPath(REPETITIONS_BY_REPETITION_ID).buildAndExpand(createdRepetition.getRepetitionId()).toUri();
        val request = buildPutRequest(uri, repetitionToBeUpdated);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val updatedRepetition = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Repetition.class);

        // Then
        assertRepetition(updatedRepetition, updatedNextDate, easiness, updatedConsecutiveCorrectAnswers, updatedTimesSeen, updatedLastIntervalDays);
    }

    @Test
    void shouldReturnErrorWhenUpdatingRepetitionAndRepetitionWasNotFoundForGivenRepetitionId() throws Exception {
        // Given
        val uri = fromPath(REPETITIONS_BY_REPETITION_ID).buildAndExpand("nonExistentRepetitionId").toUri();
        val request = buildPutRequest(uri, new Repetition());

        // When
        val result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
        val apiError = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), ApiError.class);

        // Then
        assertThat(apiError).isNotNull();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void clearDatabase() {
        repetitionRepository.deleteAll();
        wordRepository.deleteAll();
        userRepository.deleteAll();
    }
}
