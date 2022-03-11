package pl.zgora.uz.wiea.pkdg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWord;
import static pl.zgora.uz.wiea.pkdg.DataFactory.buildWordInSentences;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToEntity;

public class TestUtils {

    public static final String PROFILE_TEST = "test";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static RequestBuilder buildPostRequest(URI uri, Object body) {
        return MockMvcRequestBuilders.post(uri).content(OBJECT_MAPPER.writeValueAsString(body))
                .accept(APPLICATION_JSON).contentType(APPLICATION_JSON);
    }

    @SneakyThrows
    public static RequestBuilder buildGetRequest(URI uri) {
        return MockMvcRequestBuilders.get(uri).accept(APPLICATION_JSON);
    }

    public static UserEntity createUserInDatabase(UserRepository userRepository, String username, String password,
                                                  String email) {
        val entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        return userRepository.save(entity);
    }

    public static WordEntity createWordInDatabase(WordRepository wordRepository, String wordId, String entry,
                                                  String definition, String... exampleSentences) {
        val examples = buildWordInSentences(exampleSentences);
        val word = buildWord(wordId, entry, definition, examples);
        val wordEntity = convertToEntity(word);
        wordEntity.setWordId(wordId);
        wordEntity.getExamples().forEach(e -> e.setWord(wordEntity));
        return wordRepository.save(wordEntity);
    }
}
