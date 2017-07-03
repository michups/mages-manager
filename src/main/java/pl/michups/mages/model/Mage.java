package pl.michups.mages.model;

import lombok.*;

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
public class Mage {
    private int id;
    private String name;
    private Wand wand;
    private Mage supervisor;
    private List<Spell> spells;


    public Mage(String name, Wand wand, Mage supervisor, List<Spell> spells) {
        this.name = name;
        this.wand = wand;
        this.supervisor = supervisor;
        this.spells = spells;
    }
}
