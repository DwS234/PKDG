package pl.zgora.uz.wiea.pkdg.word.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Word {

    private String id;

    private String entry;

    private String definition;

    private List<WordInSentence> examples = new ArrayList<>();
}
