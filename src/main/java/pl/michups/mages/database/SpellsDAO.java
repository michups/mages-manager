package pl.michups.mages.database;

import pl.michups.mages.model.Core;
import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.SpellBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
public class SpellsDAO extends BaseDAO<Spell> {

    private String[] columns = {"incantation"};

    @Override
    public String getTableName() {
        return "spells";
    }

    private String tableMagesSpellsName = "mages_spells";
    private String columnMagesSpellsName = "mage";
    private String tableSpellBooksSpellsName = "spell_books_spells";
    private String columnSpellBooksSpellsName = "spell_book";

    @Override
    public Spell parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String incantation = result.getString(2);

        return new Spell(id, incantation);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Spell value) {
        Object[] values = {value.getIncantation()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Spell value) {
        return value.getId();
    }

    public List<Spell> findSpells(int id, String tableName, String compareColumnName) {
        StringBuffer sql = new StringBuffer("SELECT spells.* FROM spells INNER JOIN ");
        sql.append(tableName);
        sql.append("  ON spells.id = ");
        sql.append(tableName);
        sql.append(".spell AND ");
        sql.append(tableName);
        sql.append(".");
        sql.append(compareColumnName);
        sql.append("= ?");

        Object[] params = {id};
        return executeQuery(sql.toString(), params);
    }


    public List<Spell> findMageSpells(int id) {
        return findSpells(id, tableMagesSpellsName, columnMagesSpellsName);
    }

    public List<Spell> findBookSpells(int id) {
        return findSpells(id, tableSpellBooksSpellsName, columnSpellBooksSpellsName);
    }

    public void insertSpells(int id, List<Spell> spells, String tableName, String columnName) {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(tableName);
        sql.append(" (");
        sql.append(columnName);
        sql.append(", spell )");
        sql.append(" VALUES (");
        Object[] values = new Object[spells.size() * 2];

        for (int i = 0; i < spells.size(); i++) {
            sql.append(" ?, ? ),(");
            values[i * 2] = id;
            values[i * 2 + 1] = spells.get(i).getId();
        }
        sql.replace(sql.length() - 2, sql.length(), "");
        execute(sql.toString(), values);
    }

    public void insertSpellsForMage(int id, List<Spell> spells) {
        insertSpells(id, spells, tableMagesSpellsName, columnMagesSpellsName);
    }

    public void insertSpellsForSpellBook(int id, List<Spell> spells) {
        insertSpells(id, spells, tableSpellBooksSpellsName, columnSpellBooksSpellsName);
    }

    public void deleteAllSpellsForMage(int mageId) {

        String sql = "DELETE FROM mages_spells  WHERE mage = ?";
        Object[] params = {mageId};
        execute(sql, params);

    }

    public void deleteAllSpellsForSpellBook(int id) {

        String sql = "DELETE FROM spell_books_spells  WHERE spell_book = ?";
        Object[] params = {id};
        execute(sql, params);

    }
//    public void deleteSpellsForMage(Mage value, List<Spell> spells) {
//
//        for(Spell s : spells) {
//            String sql = "DELETE FROM mages_spells  WHERE mage = ? AND spell = ?";
//            Object[] params = {value.getId(), s.getId()};
//            execute(sql, params);
//        }
//    }
//
//    public void deleteSpellsForSpellBook(SpellBook value, List<Spell> spells) {
//
//        for(Spell s : spells) {
//            String sql = "DELETE FROM spell_books_spells  WHERE spell_book = ? AND spell = ?";
//            Object[] params = {value.getId(), s.getId()};
//            execute(sql, params);
//        }
//    }

    public void delete(int id) {

        String sql = "DELETE FROM spell_books_spells  WHERE spell = ?";
        Object[] params = {id};
        execute(sql, params);

        sql = "DELETE FROM mages_spells  WHERE spell = ?";
        Object[] params2 = {id};
        execute(sql, params2);

        super.delete(id);
    }


}
