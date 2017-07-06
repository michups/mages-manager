package pl.michups.mages.database;

import pl.michups.mages.model.Core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by michups on 03.07.17.
 */
public class CoresDAO extends BaseDAO<Core> {

    private String[] columns = {"name", "power", "consistency"};

    @Override
    public String getTableName() {
        return "cores";
    }

    @Override
    public Core parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        int power = result.getInt(3);
        int consistency = result.getInt(4);

        return new Core(id, name, power, consistency);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Core value) {
        Object[] values = {value.getName(), value.getPower(), value.getConsistency()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Core value) {
        return value.getId();
    }
}
