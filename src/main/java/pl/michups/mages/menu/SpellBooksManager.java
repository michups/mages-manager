package pl.michups.mages.menu;

import pl.michups.mages.database.MagesDAO;
import pl.michups.mages.database.SpellBooksDAO;
import pl.michups.mages.database.SpellsDAO;
import pl.michups.mages.database.WandsDAO;
import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.SpellBook;
import pl.michups.mages.model.Wand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class SpellBooksManager extends BaseManager<SpellBook, SpellBooksDAO> {

    public SpellBooksManager() {
        dao = new SpellBooksDAO();
    }

    @Override
    protected SpellBook parseNew(Scanner scanner) {
        System.out.print("new title: ");
        String title = scanner.next();

        System.out.print("author mage id: ");
        int authorId = scanner.nextInt();
        Mage author = new MagesDAO().find(authorId);

        System.out.print("publish date (ex. 2012-12-20): ");
        String dataStr1 = scanner.next();
        Date date= parseStringToDate(dataStr1);

        SpellsDAO spellsDAO = new SpellsDAO();
        List<Spell> spells = new ArrayList<>();
        while (true){
            System.out.print("spell id (0 - ends adding spells): ");
            int spellId = scanner.nextInt();
            if(spellId == 0){
                break;
            }
            Spell spell = spellsDAO.find(spellId);
            if(spell==null){
                System.out.println("Spell id = "+spellId+" doesn't exist." );
            }
            else {
                spells.add(spell);
            }
        }
        return new SpellBook(title, author, date, spells);
    }

    @Override
    protected void copyId(SpellBook from, SpellBook to) {
        to.setId(from.getId());
    }
}
