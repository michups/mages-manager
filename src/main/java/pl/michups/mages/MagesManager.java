package pl.michups.mages;

import pl.michups.mages.database.*;
import pl.michups.mages.model.*;

import java.util.List;
import java.util.Scanner;

/**
 * Created by michups on 03.07.17.
 */
public class MagesManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        boolean run = true;

        while (run) {
            System.out.print("command: ");
            command = scanner.next();

            switch (command) {
                case "core": {
                    List<Core> values =new CoreDAO().findALl();
                    for (Core v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "mage": {
                    List<Mage> values =new MageDAO().findALl();
                    for (Mage v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "spell": {
                    List<Spell> values =new SpellDAO().findALl();
                    for (Spell v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "spellBook": {
                    List<SpellBook> values =new SpellBookDAO().findALl();
                    for (SpellBook v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "wand": {
                    List<Wand> values =new WandDAO().findALl();
                    for (Wand v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "wood": {
                    List<Wood> values =new WoodDAO().findALl();
                    for (Wood v : values) {
                        System.out.println(v);
                    }
                    break;
                }
                case "quit": {
                    run = false;
                    break;
                }
            }
        }
    }
}
