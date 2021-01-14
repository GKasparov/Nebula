package me.ethan.nebula.events;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Nebula.getInstance().getSql().createPlayer(player.getUniqueId(), player);
        if (Nebula.getInstance().getSql().exists(player.getUniqueId())) {
            try {
                player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Your Rank data has loaded."));

                Nebula.getInstance().getPermissionManager().addPermissions(player);
                Nebula.getInstance().getInheritanceManager().addInheritance(player);
                Nebula.getInstance().getUserManager().addInheritance(player);
                Nebula.getInstance().getUserManager().addPermissions(player);
            } catch (Exception ex) {
             Bukkit.getConsoleSender().sendMessage("1 - 5% chance to throw an assemble error, this is due to using the PlayerJoinEvent and no PreJoinEvent.");
            }
        }


    }
    @EventHandler
    public void PlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Nebula.getInstance().getUserManager().clear();
        Nebula.getInstance().getPermissionManager().clear();

    }
}