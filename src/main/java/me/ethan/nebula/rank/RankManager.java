package me.ethan.nebula.rank;

import me.ethan.nebula.Nebula;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankManager {

    Map<Player, String> ranks = new HashMap<>();



    public void createRank(String rank, String prefix) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM ranks WHERE NAME=?");
            ps.setString(1, rank);
            ResultSet rs = ps.executeQuery();
            rs.next();

            if (rankCheck(rank) != true) {
                PreparedStatement insert = Nebula.getInstance().getMySQL().getConnection()
                        .prepareStatement("INSERT INTO ranks (NAME, PREFIX) VALUES (?,?)");
                insert.setString(1, rank);
                insert.setString(2, prefix);
                insert.executeUpdate();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void setRank(Player player, String rank) {
        ranks.put(player, rank);
    }


    public void updateRank(Player player, String rank) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("UPDATE playerdata SET RANK=? WHERE UUID=?");
            ps.setString(1, rank);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }



    public String getRank(Player player) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT RANK FROM playerdata WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                setRank(player, result.getString("RANK"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranks.get(player);
    }

    public String getPrefix(String rank) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT PREFIX FROM ranks WHERE NAME=?");
            ps.setString(1, rank);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return result.getString("PREFIX");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void deleteRank(String rank) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("DELETE FROM ranks WHERE NAME=?");
            ps.setString(1, rank);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<String> listRanks() {
        List<String> toReturn = new ArrayList<>();
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM ranks");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                toReturn.add(result.getString("NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public boolean rankCheck(String rank) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM ranks WHERE NAME=?");
            ps.setString(1, rank);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return true;
            }

        } catch (SQLException e) {
        }
        return false;

    }



}