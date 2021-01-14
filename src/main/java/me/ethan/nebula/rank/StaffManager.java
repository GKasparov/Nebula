package me.ethan.nebula.rank;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffManager {

    public void promote(Player player, String rank, String date) {
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("INSERT INTO staffdata (NAME, RANK, DATE) VALUES (?,?,?)");
            ps.setString(1, player.getName());
            ps.setString(2, rank);
            ps.setString(3, date);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getHistory(Player player) {
        List<String> toReturn = new ArrayList<>();
        try {
            PreparedStatement ps = Nebula.getInstance().getMySQL().getConnection()
                    .prepareStatement("SELECT * FROM staffdata");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String prefix = Nebula.getInstance().getRankManager().getPrefix(result.getString("RANK"));
                player.sendMessage(StringUtils.format(prefix + " " + player.getName() + " » &f" + result.getString("DATE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}



