package pl.michups.mages.menu;

import pl.michups.mages.database.CoresDAO;
import pl.michups.mages.database.SpellsDAO;
import pl.michups.mages.database.WandsDAO;
import pl.michups.mages.database.WoodsDAO;
import pl.michups.mages.model.Core;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.Wand;
import pl.michups.mages.model.Wood;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class WandsManager extends BaseManager<Wand, WandsDAO> {

    public WandsManager() {
        dao = new WandsDAO();
    }

    @Override
    protected Wand parseNew(Scanner scanner) {

        System.out.print("wood id: ");
        int woodId = scanner.nextInt();
        Wood wood = new WoodsDAO().find(woodId);

        System.out.print("core id: ");
        int coreId = scanner.nextInt();
        Core core = new CoresDAO().find(coreId);

        System.out.print("Production date (ex. 2012-12-20): ");
        String dataStr1 = scanner.next();
        Date date = parseStringToDate(dataStr1);

        return new Wand(wood, core, date);
    }

    @Override
    protected void copyId(Wand from, Wand to) {
        to.setId(from.getId());
    }
}
