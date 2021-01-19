package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("nebula.commands.promote")) {
                if (args.length == 0) {
                    List<String> msg = new ArrayList<>();
                    msg.add("&b&m------------------------------");
                    msg.add(StringUtils.Prefix() + "Showing rank command usage;");
                    msg.add("&d/promote <player> <rank>");
                    msg.add("&b&m------------------------------");
                    msg.add("&7&oUse this when setting staffs rank to import data to /staffhistory");
                    msg.add("&b&m------------------------------");
                    msg.forEach(message -> player.sendMessage(StringUtils.format(message)));
                    return true;
                }
                if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    String rank = args[1];
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String promotedate = sdf.format(date);
                    if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                        Nebula.getInstance().getRankManager().setRank(player, rank);
                        Nebula.getInstance().getRankManager().updateRank(player, rank);
                        Nebula.getInstance().getStaffManager().promote(player, rank, promotedate);
                        player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Added &d" + rank + " &fto &d" + target.getName()));
                    } else if (!Nebula.getInstance().getRankManager().rankCheck(rank))
                        player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Could not find &d" + rank + " &fhas it been created?"));
                    return true;
                }
            }
        }
        return false;
    }
}