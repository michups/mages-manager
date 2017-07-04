package pl.michups.mages.database;

import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.Wand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
public class MageDAO extends BaseDAO<Mage> {

    private String[] columns = {"name", "wand", "supervisor"};

    @Override
    public String getTableName() {
        return "mages";
    }

    @Override
    public Mage parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        int wandId = result.getInt(3);
        int supervisorId = result.getInt(4);
        List<Spell> spells = new SpellDAO().findMageSpells(id);
        Wand wand = new WandDAO().find(wandId);
        Mage supervisor = new MageDAO().find(supervisorId);

        return new Mage(id, name, wand, supervisor, spells);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Mage value) {
        Object[] values = {value.getName(), value.getWand().getId(), value.getSupervisor().getId()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Mage value) {
        return value.getId();
    }

    @Override
    public void update(Mage value) {

        SpellDAO spellDAO = new SpellDAO();
        List<Spell> oldSpells = spellDAO.findMageSpells(value.getId());
        List<Spell> newSpells = value.getSpells();
        if(!newSpells.containsAll(oldSpells) || newSpells.size()!= oldSpells.size()){

            spellDAO.deleteAllSpellsForMage(value);
            spellDAO.insertSpellsForMage(value, value.getSpells());
        }
        super.update(value);
    }

    @Override
    public void insert(Mage value) {
        SpellDAO spellDAO = new SpellDAO();
        spellDAO.insertSpellsForMage(value, value.getSpells());
        super.insert(value);
    }

    @Override
    public List<Mage> findALl() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql, new Object[0]);
    }

    @Override
    public void delete(Mage value) {
        SpellDAO spellDAO = new SpellDAO();
        spellDAO.deleteAllSpellsForMage(value);

        super.delete(getPrimaryKeyValue(value));
    }


}
