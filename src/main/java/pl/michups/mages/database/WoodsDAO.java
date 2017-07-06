package pl.michups.mages.database;

import pl.michups.mages.model.Spell;
import pl.michups.mages.model.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michups on 03.07.17.
 */
public class WoodsDAO extends BaseDAO<Wood> {

    private String[] columns = {"name", "toughness"};

    @Override
    public String getTableName() {
        return "woods";
    }

    @Override
    public Wood parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        int toughness = result.getInt(3);

        return new Wood(id, name, toughness);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Wood value) {
        Object[] values = {value.getName(), value.getToughness()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Wood value) {
        return value.getId();
    }
}
