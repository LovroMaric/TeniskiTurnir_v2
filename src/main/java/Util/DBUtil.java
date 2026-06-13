package Util;

import Model.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getConnection() throws SQLException {

        AppConfig config = ConfigLoader.getConfig();

        return DriverManager.getConnection(
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }
}