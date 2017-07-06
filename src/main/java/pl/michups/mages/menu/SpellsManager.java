package pl.michups.mages.menu;

import pl.michups.mages.database.CoresDAO;
import pl.michups.mages.database.SpellsDAO;
import pl.michups.mages.model.Core;
import pl.michups.mages.model.Spell;

import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class SpellsManager extends BaseManager<Spell, SpellsDAO> {

    public SpellsManager() {
        dao = new SpellsDAO();
    }

    @Override
    protected Spell parseNew(Scanner scanner) {
        System.out.print("new incantation: ");
        String incantation = scanner.next();


        return new Spell(incantation);
    }

    @Override
    protected void copyId(Spell from, Spell to) {
        to.setId(from.getId());
    }
}
