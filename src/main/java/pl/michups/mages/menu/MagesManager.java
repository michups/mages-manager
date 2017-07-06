package pl.michups.mages.menu;

import pl.michups.mages.database.*;
import pl.michups.mages.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class MagesManager extends BaseManager<Mage, MagesDAO> {

    public MagesManager() {
        dao = new MagesDAO();
    }

    @Override
    protected Mage parseNew(Scanner scanner) {
        System.out.print("new name: ");
        String name = scanner.next();

        System.out.print("wand id: ");
        int wandId = scanner.nextInt();
        Wand wand = new WandsDAO().find(wandId);

        System.out.print("supervisor id (0 - for no supervisor): ");
        int supervisorId = scanner.nextInt();
        Mage supervisor = new MagesDAO().find(supervisorId);

        SpellsDAO spellsDAO = new SpellsDAO();
        List<Spell> spells = new ArrayList<>();
        while (true) {
            System.out.print("spell id (0 - end adding spells): ");
            int spellId = scanner.nextInt();
            if (spellId == 0) {
                break;
            }
            Spell spell = spellsDAO.find(spellId);
            if (spell == null) {
                System.out.println("Spell id = " + spellId + " doesn't exist.");
            } else {
                spells.add(spell);
            }
        }
        return new Mage(name, wand, supervisor, spells);
    }

    @Override
    protected void copyId(Mage from, Mage to) {
        to.setId(from.getId());
    }
}
