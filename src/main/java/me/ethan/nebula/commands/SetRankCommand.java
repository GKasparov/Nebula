package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if (args.length == 0) {
                List<String> msg = new ArrayList<>();
                msg.add("setrank <player> <rank>");
                msg.add("setrank force <player> <rank>");
                msg.forEach(message -> console.sendMessage(StringUtils.format(message)));

            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String rank = args[1];
                if (target.hasPermission("nebula.utils.staff")) {
                    target.sendMessage(StringUtils.format(StringUtils.Prefix() + " As you are staff the rank has been added to your inheritance."));
                } else {
                    if (!target.hasPermission("nebula.utils.staff")) {
                        if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                            Nebula.getInstance().getRankManager().setRank(target, rank);
                            Nebula.getInstance().getRankManager().updateRank(target, rank);
                            console.sendMessage(StringUtils.format(StringUtils.Prefix() + "Added &d" + rank + " &fto &d" + target.getName()));
                            target.sendMessage(StringUtils.format(StringUtils.Prefix() + "Your rank has been updated to &d" + rank));
                            target.sendMessage(StringUtils.format("&4&lMake sure to relog for all your perks."));
                        } else if (!Nebula.getInstance().getRankManager().rankCheck(rank)) {
                            console.sendMessage(StringUtils.format(StringUtils.Prefix() + "Could not find &d" + rank + " &fhas it been created?"));
                        }
                        return true;
                    }
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("force")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    String rank = args[2];
                    if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                        Nebula.getInstance().getRankManager().setRank(target, rank);
                        Nebula.getInstance().getRankManager().updateRank(target, rank);
                        console.sendMessage(StringUtils.format(StringUtils.Prefix() + "Added &d" + rank + " &fto &d" + target.getName()));
                        target.sendMessage(StringUtils.format(StringUtils.Prefix() + "Your rank has been updated to &d" + rank));
                        target.sendMessage(StringUtils.format("&4&lMake sure to relog for all your perks."));
                    } else if (!Nebula.getInstance().getRankManager().rankCheck(rank)) {
                        console.sendMessage(StringUtils.format(StringUtils.Prefix() + "Could not find &d" + rank + " &fhas it been created?"));
                    }
                    return true;
                }
            } else {
                System.out.println("Incorrect Syntax. Please use /setrank <PlayerName/force> <Rank/PlayerName> <Rank> Or /setrank for more details");
            }
        }
        return false;
    }
}
