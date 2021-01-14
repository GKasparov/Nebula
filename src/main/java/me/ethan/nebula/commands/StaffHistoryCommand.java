package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffHistoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("nebula.commands.staffhis")) {
                if (args.length == 0) {
                    player.sendMessage(StringUtils.format("&b&m------------------------------"));
                    player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Showing your staff history."));
                    player.sendMessage(StringUtils.format(""));
                    Nebula.getInstance().getStaffManager().getHistory(player).forEach(msg -> player.sendMessage(StringUtils.format(msg)));
                    player.sendMessage(StringUtils.format(""));
                    player.sendMessage(StringUtils.format("&7&oWe are so grateful for the time and effort you have put in &d❤"));
                    player.sendMessage(StringUtils.format("&b&m------------------------------"));

                }
            }
        }


        return false;
    }
}
