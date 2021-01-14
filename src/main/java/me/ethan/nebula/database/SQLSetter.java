package me.ethan.nebula.database;

import me.ethan.nebula.Nebula;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLSetter {


    public void createPlayerData() {
        PreparedStatement ps;
        try {
            ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS playerdata " + "(NAME VARCHAR(100) NOT NULL, UUID VARCHAR(100) NOT NULL, RANK TEXT NOT NULL, ISSTAFF BOOLEAN, PRIMARY KEY(NAME))");
            ps.executeUpdate();


        } catch (SQLException e) {
        }
    }


    public void createRankData() {
        PreparedStatement ps;
        try {
            ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS ranks " + "(NAME VARCHAR(100) NOT NULL, PREFIX VARCHAR(100), PRIMARY KEY(NAME))");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createStaffData() {
        PreparedStatement ps;
        try {
            ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS staffdata " + "(NAME VARCHAR(100) NOT NULL, RANK TEXT NOT NULL, DATE TEXT NOT NULL)");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);

            if (exists(uuid) != true) {
                PreparedStatement insert = Nebula.getInstance().getMySQL().getConnection()
                        .prepareStatement("INSERT INTO playerdata (UUID,NAME,RANK,ISSTAFF) VALUES (?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setString(3, "Member");
                insert.setBoolean(4, false);


                insert.executeUpdate();

            }
        } catch (SQLException e) {

        }
    }


        public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet result = ps.executeQuery();
            if (result.next()) {

                return true;
            }

        } catch (SQLException e) {
        }
        return false;
    }


}