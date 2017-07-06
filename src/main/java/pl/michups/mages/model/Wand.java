package pl.michups.mages.model;

import lombok.*;

import java.util.Date;

/**
 * Created by michups on 03.07.17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Wand {
    private int id;
    private Wood wood;
    private Core core;
    private Date productionDate;

    public Wand(Wood wood, Core core, Date productionDate) {
        this.wood = wood;
        this.core = core;
        this.productionDate = productionDate;
    }
}
