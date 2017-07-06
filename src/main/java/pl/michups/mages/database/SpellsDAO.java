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

    public List<Spell> findMageSpells(int id) {
        String sql = "SELECT spells.* FROM spells INNER JOIN mages_spells ON spells.id = mages_spells.spell AND mages_spells.mage= ?";
        Object[] params = {id};
        return executeQuery(sql, params);
    }


    public void insertSpellsForMage(int id, List<Spell> spells) {
        int mageId= id;
        StringBuffer sql = new StringBuffer("INSERT INTO mages_spells");
        sql.append(" (");
        sql.append("mage, spell )");
        sql.append(" VALUES (");
        Object[] values = new Object[spells.size()*2];

        for (int i = 0; i < spells.size(); i++) {
            sql.append(" ?, ? ),(");
            values[i*2] = mageId;
            values[i*2+1] = spells.get(i).getId();
        }
        sql.replace(sql.length() - 2, sql.length(), "");
        execute(sql.toString(), values);
    }

    public void deleteAllSpellsForMage(int mageId) {

        String sql = "DELETE FROM mages_spells  WHERE mage = ?";
        Object[] params = {mageId};
        execute(sql, params);

    }
    public void deleteSpellsForMage(Mage value, List<Spell> spells) {

        for(Spell s : spells) {
            String sql = "DELETE FROM mages_spells  WHERE mage = ? AND spell = ?";
            Object[] params = {value.getId(), s.getId()};
            execute(sql, params);
        }
    }

    public List<Spell> findBookSpells(int id) {
        String sql = "SELECT spells.*  FROM spells INNER JOIN spell_books_spells ON spells.id = spell_books_spells.spell AND spell_books_spells.spell_book=?";
        Object[] params = {id};
        return executeQuery(sql, params);
    }

    public void insertSpellsToSpellBook(int id, List<Spell> spells) {

        int spellBookId= id;
        StringBuffer sql = new StringBuffer("INSERT INTO spell_books_spells");
        sql.append(" (");
        sql.append("spell_book, spell )");
        sql.append(" VALUES (");
        Object[] values = new Object[spells.size()*2];

        for (int i = 0; i < spells.size(); i++) {
            sql.append(" ?, ? ),(");
            values[i*2] = spellBookId;
            values[i*2+1] = spells.get(i).getId();
        }
        sql.replace(sql.length() - 2, sql.length(), "");
        execute(sql.toString(), values);
    }

    public void deleteAllSpellsForSpellBook(int id) {

        String sql = "DELETE FROM spell_books_spells  WHERE spell_book = ?";
        Object[] params = {id};
        execute(sql, params);

    }
    public void deleteSpellsForSpellBook(SpellBook value, List<Spell> spells) {

        for(Spell s : spells) {
            String sql = "DELETE FROM spell_books_spells  WHERE spell_book = ? AND spell = ?";
            Object[] params = {value.getId(), s.getId()};
            execute(sql, params);
        }
    }

    public void delete(int id){

        String sql = "DELETE FROM spell_books_spells  WHERE spell = ?";
        Object[] params = {id};
        execute(sql, params);

        sql = "DELETE FROM mages_spells  WHERE spell = ?";
        Object[] params2 = {id};
        execute(sql, params2);

        super.delete(id);
    }

    public int getLastInsertId() {
        int id = 0;
        String sql = "SELECT LAST_INSERT_ID()";
        try (Connection con = ConnectionFactory.createConnection();
             PreparedStatement statement = con.prepareStatement(sql);) {
            try (ResultSet result = statement.executeQuery();) {
                id = result.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

}
