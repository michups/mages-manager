package pl.michups.mages.model;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SpellBook {
    private int id;
    private String title;
    private Mage author;
    private Date publishDate;
    private List<Spell> spells;

    public SpellBook(String title, Mage author, Date publishDate, List<Spell> spells) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.spells = spells;
    }
}
