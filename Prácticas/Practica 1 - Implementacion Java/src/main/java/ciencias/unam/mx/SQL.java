package ciencias.unam.mx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class SQL {

    private static final String url = "jdbc:mariadb://localhost/";
    private String databaseName;
    private Properties userInfo;
    private Connection connection;
    private Statement statement;

    public SQL(String user, String password) {
        this.userInfo = new Properties();
        userInfo.put("user", user);
        userInfo.put("password", password);
    }

    public boolean connect() {
        try {
            this.connection = DriverManager.getConnection(url, userInfo);
            this.statement = connection.createStatement();
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    public void createDatabase(String databaseName) {
        try {
            this.databaseName = databaseName;
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
            statement.executeUpdate("USE " + databaseName);
        } catch (SQLException sqle) {
            System.out.println("Algo salio mal.");
        }
    }

    public boolean selectDatabase(String databaseName) {
        try {
            this.databaseName = databaseName;
            this.connection = DriverManager.getConnection(url + databaseName, userInfo);
            statement.executeUpdate("USE " + databaseName);
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    public void createTable(String table, List<String> fields) {
        StringBuilder sql = new StringBuilder(String.format("CREATE TABLE %s (", table));
        for (String field: fields) {
            sql.append(field);
        }
        sql.append(")");
        System.out.println(sql.toString());
        try {
            statement.executeUpdate(sql.toString());
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exec(String instruction) {
        try {
            statement.executeUpdate(instruction);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printTable(String table) {
        DBTablePrinter.printTable(connection, table);
    }

    public String getTableInfo(String fields, String table) {
        StringBuilder s = new StringBuilder();
        try {
            ResultSet resultSet = statement.executeQuery(String.format("SELECT %s FROM %s", fields, table));
            ResultSetMetaData metadata = resultSet.getMetaData();
            while (resultSet.next()) {
                s.append(resultSet.getString(1));
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    s.append(resultSet.getString(i) + " ");
                }
                s.append("\n");
            }
        } catch (SQLException sqle) {
            System.out.println("Algo salio mal.");
        }
        return s.toString();
    }

    public String getDatabases() {
        StringBuilder s = new StringBuilder();
        try {
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            ResultSetMetaData metadata = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    s.append(resultSet.getString(i) + "\n");
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Algo salio mal.");
        }
        return s.toString();
    }
}
