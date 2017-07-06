package pl.michups.mages.menu;

import pl.michups.mages.database.CoresDAO;
import pl.michups.mages.database.WandsDAO;
import pl.michups.mages.database.WoodsDAO;
import pl.michups.mages.model.Core;
import pl.michups.mages.model.Wand;
import pl.michups.mages.model.Wood;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class WoodsManager extends BaseManager<Wood, WoodsDAO> {

    public WoodsManager() {
        dao = new WoodsDAO();
    }

    @Override
    protected Wood parseNew(Scanner scanner) {

        System.out.print("name : ");
        String name = scanner.next();

        System.out.print("toughness : ");
        int toughness = scanner.nextInt();

        return new Wood(name, toughness);
    }

    @Override
    protected void copyId(Wood from, Wood to) {
        to.setId(from.getId());
    }
}
