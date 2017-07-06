package pl.michups.mages.database;

import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.Wand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
public class MagesDAO extends BaseDAO<Mage> {

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
        List<Spell> spells = new SpellsDAO().findMageSpells(id);
        Wand wand = new WandsDAO().find(wandId);
        Mage supervisor = new MagesDAO().find(supervisorId);

        return new Mage(id, name, wand, supervisor, spells);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Mage value) {
        Object[] values = {value.getName(), value.getWand().getId(), value.getSupervisor() == null? null : value.getSupervisor().getId()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Mage value) {
        return value.getId();
    }

    @Override
    public void update(Mage value) {

        super.update(value);
        SpellsDAO spellDAO = new SpellsDAO();
        List<Spell> oldSpells = spellDAO.findMageSpells(value.getId());
        List<Spell> newSpells = value.getSpells();

        int mageId = value.getId();

        if(newSpells.size()==0){
            spellDAO.deleteAllSpellsForMage(mageId);
        } else if(!newSpells.containsAll(oldSpells) || newSpells.size()!= oldSpells.size()){

            spellDAO.deleteAllSpellsForMage(mageId);
            spellDAO.insertSpellsForMage(value.getId(), value.getSpells());
        }
    }

    @Override
    public void insert(Mage value) {
        super.insert(value);

        List<Spell> spells = value.getSpells();
        List<Mage> mages = findALl();
        mages.sort((o1, o2) -> o1.getId()-o2.getId());
        int lastId = mages.get(mages.size()-1).getId();
        if (spells.size() != 0) {
            SpellsDAO spellDAO = new SpellsDAO();
            spellDAO.insertSpellsForMage(lastId, spells);
        }
    }

    @Override
    public List<Mage> findALl() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql, new Object[0]);
    }

    @Override
    public void delete(int id) {
        SpellsDAO spellDAO = new SpellsDAO();
        spellDAO.deleteAllSpellsForMage(id);

        super.delete(id);
    }


}
