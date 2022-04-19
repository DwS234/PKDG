package pl.zgora.uz.wiea.pkdg.word.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Words {

    private final List<Word> data;
    private final long totalSize;
}
