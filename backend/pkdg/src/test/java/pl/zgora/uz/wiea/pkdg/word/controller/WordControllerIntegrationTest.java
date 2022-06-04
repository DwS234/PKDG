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
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static pl.zgora.uz.wiea.pkdg.AssertUtils.assertWord;
import static pl.zgora.uz.wiea.pkdg.TestUtils.*;

@Testcontainers
@ActiveProfiles(PROFILE_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordControllerIntegrationTest {

    private static final String WORDS_PATH = "/api/words/";

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

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    void afterEachTest() {
        wordRepository.deleteAll();
    }

    @Test
    void shouldGetWordsAutocomplete() throws Exception {
        // Given
        val entry = "entry";
        val entry2 = "anotherEntry";
        val definition = "definition";
        val definition2 = "definition2";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val sentence3 = "sentence3";
        val sentence4 = "sentence4";
        createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);
        createWordInDatabase(wordRepository, entry2, definition2, sentence3, sentence4);

        val uri = fromPath(WORDS_PATH + "autocomplete").queryParam("q", "ano").build().toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val autocompleteEntries = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<String>>() {
        });

        // Then
        assertThat(autocompleteEntries).isNotNull().hasSize(1);
        assertThat(autocompleteEntries.get(0)).isEqualTo(entry2);
    }

    @Test
    void shouldGetWordsByEntry() throws Exception {
        // Given
        val entry = "entry";
        val definition = "definition";
        val definition2 = "definition2";
        val sentence1 = "sentence1";
        val sentence2 = "sentence2";
        val sentence3 = "sentence3";
        val sentence4 = "sentence4";
        createWordInDatabase(wordRepository, entry, definition, sentence1, sentence2);
        createWordInDatabase(wordRepository, entry, definition2, sentence3, sentence4);

        val uri = fromPath(WORDS_PATH + "entry/{entry}").buildAndExpand(entry).toUri();
        val request = buildGetRequest(uri);

        // When
        val result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        val words = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Word>>() {
        });

        // Then
        assertThat(words).isNotNull().hasSize(2);
        assertWord(words.get(0), entry, definition, sentence1, sentence2);
        assertWord(words.get(1), entry, definition2, sentence3, sentence4);
    }
}
