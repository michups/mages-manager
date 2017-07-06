package pl.michups.mages.database;

import pl.michups.mages.model.Mage;
import pl.michups.mages.model.Spell;
import pl.michups.mages.model.SpellBook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by michups on 03.07.17.
 */
public class SpellBooksDAO extends BaseDAO<SpellBook> {

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

        List<Spell> spells = new SpellsDAO().findBookSpells(id);
        Mage author = new MagesDAO().find(authorId);

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

        SpellsDAO spellDAO = new SpellsDAO();
        List<Spell> oldSpells = spellDAO.findBookSpells(value.getId());
        List<Spell> newSpells = value.getSpells();
        if(newSpells.size()==0){
            spellDAO.deleteAllSpellsForSpellBook(value.getId());
        } else if(!newSpells.containsAll(oldSpells) || newSpells.size()!= oldSpells.size()){

            spellDAO.deleteAllSpellsForSpellBook(value.getId());
            spellDAO.insertSpellsToSpellBook(value.getId(), value.getSpells());
        }
        super.update(value);
    }

    @Override
    public void insert(SpellBook value) {
        super.insert(value);
        List<Spell> spells = value.getSpells();

        List<SpellBook> spellBooks = findALl();
        spellBooks.sort((o1, o2) -> o1.getId()-o2.getId());
        int lastId = spellBooks.get(spellBooks.size()-1).getId();
        if (spells.size() != 0) {
            SpellsDAO spellDAO = new SpellsDAO();
            spellDAO.insertSpellsToSpellBook(lastId, spells);
        }
    }

    @Override
    public List<SpellBook> findALl() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql, new Object[0]);
    }

    @Override
    public void delete(int id) {
        SpellsDAO spellDAO = new SpellsDAO();
        spellDAO.deleteAllSpellsForSpellBook(id);

        super.delete(id);
    }


}
