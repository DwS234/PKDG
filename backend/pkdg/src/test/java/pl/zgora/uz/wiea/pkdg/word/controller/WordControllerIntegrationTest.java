package pl.zgora.uz.wiea.pkdg.word.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.val;
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
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.WordInSentence;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWord;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWordInSentences;
import static pl.zgora.uz.wiea.pkdg.TestUtils.*;

@Testcontainers
@ActiveProfiles(PROFILE_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordControllerIntegrationTest {

    private static final String WORDS_PATH = "/api/words/";

    @Container
    private static final PostgreSQLContainer<IntegrationTestPostgresqlContainer> postgreSQLContainer =
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

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
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
        val word = buildWord("wordId", entry, definition, wordInSentences);

        val uri = fromPath(WORDS_PATH).build().toUri();
        val request = buildPostRequest(uri, word);

        // When
        val result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        val createdWord = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), Word.class);

        // Then
        assertThat(createdWord).isNotNull();
        assertThat(createdWord.getEntry()).isEqualTo(entry);
        assertThat(createdWord.getDefinition()).isEqualTo(definition);
        assertThat(createdWord.getExamples().stream().map(WordInSentence::getSentence)
                .collect(toUnmodifiableList())).containsExactly(sentence1, sentence2);
    }

    @Test
    void shouldGetAllWords() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        createWordInDatabase(wordRepository, "wordId", entry, definition, sentence1, sentence2);

        val uri = fromPath(WORDS_PATH).build().toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val returnedWords = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Word>>() {
        });

        // Then
        assertThat(returnedWords).hasSize(1);
        val returnedWord = returnedWords.get(0);
        assertThat(returnedWord.getEntry()).isEqualTo(entry);
        assertThat(returnedWord.getDefinition()).isEqualTo(definition);
        assertThat(returnedWord.getExamples().stream().map(WordInSentence::getSentence)
                .collect(toUnmodifiableList())).containsExactly(sentence1, sentence2);
    }
}
