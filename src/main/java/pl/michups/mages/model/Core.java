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
public class Core {
    private int id;
    private String name;
    private int power;
    private int consistency;

    public Core(String name, int power, int consistency) {
        this.name = name;
        this.power = power;
        this.consistency = consistency;
    }
}
