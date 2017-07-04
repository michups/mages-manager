package pl.michups.mages.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by michups on 03.07.17.
 */
public class ConnectionFactory {

    private final static String URL = "jdbc:mysql://localhost:3306/mages";

    private final static String USER = "mage_admin";

    private final static String PASSWORD = "m4g34dm1n";

    private final static String PARAMS = "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";

    private static BasicDataSource ds;

//    public static Connection createConnection() throws SQLException {
//        Connection connection = DriverManager.getConnection(URL + PARAMS, USER, PASSWORD);
//        return connection;
//    }

    public static Connection createConnection() throws SQLException {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setUsername(USER);
            ds.setPassword(PASSWORD);
            ds.setUrl(URL + PARAMS);
        }
        Connection connection = ds.getConnection();
        return connection;
    }
}
