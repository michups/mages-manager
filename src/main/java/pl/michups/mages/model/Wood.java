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
public class Wood {
    private int id;
    private String name;
    private int toughness;

    public Wood(String name, int toughness) {
        this.name = name;
        this.toughness = toughness;
    }
}
