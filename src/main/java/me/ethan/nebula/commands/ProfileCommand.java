package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(player.hasPermission("nebula.commands.profile")) {
                if(args.length == 0) {
                    List<String> msg = new ArrayList<>();
                    Date date = new Date(player.getFirstPlayed());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String firstPlayed = sdf.format(date);
                    msg.add("&b&m------------------------------");
                    msg.add(StringUtils.Prefix() + "Showing your profile.");
                    msg.add("");
                    msg.add("&dIGN » &f" + player.getName());
                    msg.add("&dUUID » &f" + player.getUniqueId().toString());
                    msg.add("&dRank » &f" + Nebula.getInstance().getRankManager().getRank(player));
                    msg.add("&dFirst Join » &f" + firstPlayed);
                    msg.add("&b&m------------------------------");
                    msg.forEach(message -> player.sendMessage(StringUtils.format(message)));
                }


            }


        }
        return false;
    }
}
