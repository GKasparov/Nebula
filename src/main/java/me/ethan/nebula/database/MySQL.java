package me.ethan.nebula.database;

import me.ethan.nebula.Nebula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private String host = Nebula.getInstance().getConfigYML().getString("MYSQL.HOST");
    private  String  port = Nebula.getInstance().getConfigYML().getString("MYSQL.PORT");
    private String database = Nebula.getInstance().getConfigYML().getString("MYSQL.DATABASE");
    private String user = Nebula.getInstance().getConfigYML().getString("MYSQL.USER");
    private String password = Nebula.getInstance().getConfigYML().getString("MYSQL.PASSWORD");

    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);

    }
    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    user, password);
        }

    }

    public void disconnect(){
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    public Connection getConnection() {
        return connection;
    }

}