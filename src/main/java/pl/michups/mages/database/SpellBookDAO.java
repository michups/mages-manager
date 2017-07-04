package pl.michups.mages.database;

import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.SpellBook;
import pl.michups.mages.model.Wand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
public class SpellBookDAO extends BaseDAO<SpellBook> {

    private String[] columns = {"title", "author", "publish_date"};

    @Override
    public String getTableName() {
        return "spell_books";
    }

    @Override
    public SpellBook parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String title = result.getString(2);
        int authorId = result.getInt(3);
        Date date = result.getDate(4);

        List<Spell> spells = new SpellDAO().findBookSpells(id);
        Mage author = new MageDAO().find(authorId);

        return new SpellBook(id, title, author, date, spells);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(SpellBook value) {
        Object[] values = {value.getTitle(), value.getAuthor().getId(), value.getPublishDate()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(SpellBook value) {
        return value.getId();
    }

    @Override
    public void update(SpellBook value) {

        SpellDAO spellDAO = new SpellDAO();
        List<Spell> oldSpells = spellDAO.findBookSpells(value.getId());
        List<Spell> newSpells = value.getSpells();
        if(!newSpells.containsAll(oldSpells) || newSpells.size()!= oldSpells.size()){

            spellDAO.deleteAllSpellsForSpellBook(value);
            spellDAO.insertSpellsToSpellBook(value, value.getSpells());
        }
        super.update(value);
    }

    @Override
    public void insert(SpellBook value) {
        SpellDAO spellDAO = new SpellDAO();
        spellDAO.insertSpellsToSpellBook(value, value.getSpells());
        super.insert(value);
    }

    @Override
    public List<SpellBook> findALl() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql, new Object[0]);
    }

    @Override
    public void delete(SpellBook value) {
        SpellDAO spellDAO = new SpellDAO();
        spellDAO.deleteAllSpellsForSpellBook(value);

        super.delete(getPrimaryKeyValue(value));
    }


}
