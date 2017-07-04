package pl.michups.mages.database;

import pl.michups.mages.model.Core;
import pl.michups.mages.model.Wand;
import pl.michups.mages.model.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by michups on 03.07.17.
 */
public class WandDAO  extends BaseDAO<Wand> {

    private String[] columns = {"wood", "core", "production_date"};

    @Override
    public String getTableName() {
        return "wands";
    }

    @Override
    public Wand parseValue(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        int woodId = result.getInt(2);
        int coreId = result.getInt(3);
        Date date = result.getTimestamp(4);

        Wood wood = new WoodDAO().find(woodId);
        Core core = new CoreDAO().find(coreId);

        return new Wand(id, wood, core, date);
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public Object[] getColumnsValues(Wand value) {
        Object[] values = {value.getWood().getId(), value.getCore().getId(), value.getDate()};
        return values;
    }

    @Override
    public int getPrimaryKeyValue(Wand value) {
        return value.getId();
    }
}
