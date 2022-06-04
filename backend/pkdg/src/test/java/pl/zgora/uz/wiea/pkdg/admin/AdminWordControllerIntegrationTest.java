package pl.zgora.uz.wiea.pkdg.admin;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zgora.uz.wiea.pkdg.IntegrationTestPostgresqlContainer;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.Words;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static pl.zgora.uz.wiea.pkdg.AssertUtils.assertWord;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWord;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWordInSentences;
import static pl.zgora.uz.wiea.pkdg.TestUtils.*;

@Testcontainers
@ActiveProfiles(PROFILE_TEST)
@WithMockUser(authorities = {"ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminWordControllerIntegrationTest {

    private static final String ADMIN_API_WORDS_PATH = "/admin-api/words/";
    private static final String ADMIN_API_WORDS_BY_ID_PATH = ADMIN_API_WORDS_PATH + "{wordId}";

    @Container
    private static final PostgreSQLContainer<IntegrationTestPostgresqlContainer> POSTGRESQL_CONTAINER =
            IntegrationTestPostgresqlContainer.getInstance();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordService wordService;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEachTest() {
        wordRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }

    @AfterEach
    void afterEachTest() {
        wordRepository.deleteAll();
    }

    @Test
    void shouldCreateWord() throws Exception {
        // Given
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val wordInSentences = buildWordInSentences(sentence1, sentence2);
        val entry = "entry";
        val definition = "definition";
        val word = buildWord(entry, definition, wordInSentences);

        val uri = fromPath(ADMIN_API_WORDS_PATH).build().toUri();
        val request = buildPostRequest(uri, word);

        // When
        val result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        val createdWord = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Word.class);

        // Then
        assertWord(createdWord, entry, definition, sentence1, sentence2);
    }

    @Test
    void shouldGetAllWords() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);

        val uri = fromPath(ADMIN_API_WORDS_PATH).build().toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val words = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Words.class);

        // Then
        assertThat(words).isNotNull();
        assertThat(words.getTotalSize()).isEqualTo(1);
        assertWord(words.getData().get(0), entry, definition, sentence1, sentence2);
    }

    @Test
    void shouldGetWordById() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val entity = createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);

        val uri = fromPath(ADMIN_API_WORDS_BY_ID_PATH).buildAndExpand(entity.getWordId()).toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val word = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Word.class);

        // Then
        assertWord(word, entry, definition, sentence1, sentence2);
    }

    @Test
    void shouldUpdateWord() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val entity = createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);

        val updatedEntry = "updatedEntry";
        val updatedDefinition = "updatedDefinition";
        val examples = buildWordInSentences(sentence1, sentence2);
        val wordToUpdate = buildWord(updatedEntry, updatedDefinition, examples);

        val uri = fromPath(ADMIN_API_WORDS_BY_ID_PATH).buildAndExpand(entity.getWordId()).toUri();
        val request = buildPutRequest(uri, wordToUpdate);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val updatedWord = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Word.class);

        // Then
        assertWord(updatedWord, updatedEntry, updatedDefinition, sentence1, sentence2);
    }

    @Test
    void shouldDeleteWordById() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val entity = createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);

        val uri = fromPath(ADMIN_API_WORDS_BY_ID_PATH).buildAndExpand(entity.getWordId()).toUri();
        val request = buildDeleteRequest(uri);

        // When
        mockMvc.perform(request).andExpect(status().isNoContent()).andReturn();

        // Then
        assertThat(wordRepository.count()).isEqualTo(0);
    }
}
