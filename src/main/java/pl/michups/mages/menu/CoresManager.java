package pl.michups.mages.menu;

import pl.michups.mages.database.CoresDAO;
import pl.michups.mages.database.MagesDAO;
import pl.michups.mages.database.SpellsDAO;
import pl.michups.mages.database.WandsDAO;
import pl.michups.mages.model.Core;
import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.Wand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class CoresManager extends BaseManager<Core, CoresDAO> {

    public CoresManager() {
        dao = new CoresDAO();
    }

    @Override
    protected Core parseNew(Scanner scanner) {
        System.out.print("new name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("power : ");
        int power = scanner.nextInt();

        System.out.print("consistancy : ");
        int consistancy = scanner.nextInt();

        return new Core(name, power, consistancy);
    }

    @Override
    protected void copyId(Core from, Core to) {
        to.setId(from.getId());
    }
}
