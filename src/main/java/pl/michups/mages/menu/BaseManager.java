package pl.michups.mages.menu;

import pl.michups.mages.database.BaseDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Created by michups on 04.07.17.
 */
public abstract class BaseManager<T, D extends BaseDAO<T>> {

    protected D dao;

    public void manage(Scanner scanner) {
        boolean run = true;
        while (run) {
            System.out.print("command ( list, add, find, update, delete, b-go back): ");
            String command = scanner.next();
            switch (command) {
                case "add": {
                    add(scanner);
                    break;
                }
                case "list": {
                    list();
                    break;
                }
                case "find": {
                    find(scanner);
                    break;
                }
                case "delete": {
                    delete(scanner);
                    break;
                }
                case "update": {
                    update(scanner);
                    break;
                }
                case "b": {
                    run=false;
                    break;
                }
            }
        }
    }

    protected abstract T parseNew(Scanner scanner);

    protected abstract void copyId(T from, T to);

    private void update(Scanner scanner) {
        System.out.print("id: ");
        int id = scanner.nextInt();
        T value = dao.find(id);
        System.out.println(value);
        T newValue = parseNew(scanner);
        copyId(value, newValue);
        dao.update(newValue);
    };

    private void delete(Scanner scanner) {
        System.out.print("id: ");
        int id = scanner.nextInt();
        dao.delete(id);
    }

    private void find(Scanner scanner) {
        System.out.print("id: ");
        int id = scanner.nextInt();
        T value = dao.find(id);
        System.out.println(value);
    }

    private void list() {
        List<T> values = dao.findALl();
        for (T v : values) {
            System.out.println(v);
        }
    }

    private void add(Scanner scanner) {
        T newValue = parseNew(scanner);
        dao.insert(newValue);
    }

    public Date parseStringToDate(String dateStr) {

        Date date = null;
        SimpleDateFormat inutUTC = new SimpleDateFormat("yyyy-MM-dd");
        inutUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = inutUTC.parse(dateStr);
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        return date;
    }
}
