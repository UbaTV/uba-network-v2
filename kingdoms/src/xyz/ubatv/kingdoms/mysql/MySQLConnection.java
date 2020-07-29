package xyz.ubatv.kingdoms.mysql;

import java.sql.*;

public class MySQLConnection {

    protected Connection connection;

    private final String hostname;
    private final int port;
    private final String user;
    private final String password;
    private final String database;

    public MySQLConnection(String hostname, int port, String user, String password, String database){
        this.connection = null;
        this.hostname = hostname;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException{
        if(checkConnection()){
            return connection;
        }

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" +
                        this.hostname + ":" + this.port + "/" + this.database,
                this.user, this.password);
        return connection;
    }

    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection(){
        return connection;
    }

    public boolean closeConnection() throws SQLException{
        if(connection == null){
            return false;
        }
        connection.close();
        return true;
    }

    public ResultSet querySQL(String query) throws SQLException, ClassNotFoundException{
        if(!checkConnection()){
            openConnection();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public int updateSQL(String query) throws SQLException, ClassNotFoundException{
        if(!checkConnection()){
            openConnection();
        }
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(query);
        return result;
    }
}
