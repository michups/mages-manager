package pl.michups.mages.model;

import lombok.*;

/**
 * Created by michups on 03.07.17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Spell {
    private int id;
    private String incantation;

    public Spell(String incantation) {
        this.incantation = incantation;
    }
}
