package pl.zgora.uz.wiea.pkdg.word.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Words {

    private List<Word> data;
    private long totalSize;
}
