package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("nebula.commands.user")) {
                if (args.length == 0) {
                    List<String> rankmsg = new ArrayList<>();
                    rankmsg.add("&b&m------------------------------");
                    rankmsg.add(StringUtils.Prefix() + "Showing rank command usage;");
                    rankmsg.add("&d/user inherit add <player> <rank> » &fAdds inherit to user.");
                    rankmsg.add("&d/user perm add <player> <perm> » &fAdds inherit to user.");
                    rankmsg.add("&b&m------------------------------");
                    rankmsg.forEach(rank -> player.sendMessage(StringUtils.format(rank)));
                }
                if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("inherit")) {
                        if (args[1].equalsIgnoreCase("add")) {
                            Player target = Bukkit.getPlayer(args[2]);
                            String rank = args[3];
                                player.sendMessage(StringUtils.format("Added " + rank + " to " + target.getName()));
                                Nebula.getInstance().getUserManager().addInherit(target, rank);
                            return true;
                        }
                    }
                }
            }
            if (args.length == 4) {
                if (args[0].equalsIgnoreCase("perm")) {
                    if (args[1].equalsIgnoreCase("add")) {
                        Player target = Bukkit.getPlayer(args[2]);
                        String perm = args[3];
                            player.sendMessage(StringUtils.format("Added " + perm + " to " + target.getName()));
                            Nebula.getInstance().getUserManager().addPerm(target, perm);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}