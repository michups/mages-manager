package pl.michups.mages;

import pl.michups.mages.menu.*;

import java.util.Scanner;

/**
 * Created by michups on 04.07.17.
 */
public class MainManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        CoresManager coresManager = new CoresManager();
        MagesManager magesManager = new MagesManager();
        SpellBooksManager spellBooksManager = new SpellBooksManager();
        SpellsManager spellsManager = new SpellsManager();
        WandsManager wandsManager = new WandsManager();
        WoodsManager woodsManager = new WoodsManager();
        boolean run = true;

        while (run) {
            System.out.print("Choose element (cores, mages, spells, spellbooks, wands, wood, q-quit): ");
            command = scanner.next();

            switch (command) {
                case "cores": {
                    coresManager.manage(scanner);
                    break;
                }
                case "mages": {
                    magesManager.manage(scanner);
                    break;
                }
                case "spells": {
                    spellsManager.manage(scanner);
                    break;
                }
                case "spellbooks": {
                    spellBooksManager.manage(scanner);
                    break;
                }
                case "wands": {
                    wandsManager.manage(scanner);
                    break;
                }
                case "wood": {
                    woodsManager.manage(scanner);
                    break;
                }

                case "q": {
                    run = false;
                    break;
                }
            }
        }
    }

}
