package me.ethan.nebula.rank;

import me.ethan.nebula.Nebula;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public void addInheritance(Player player) {
        setInheritance(player.getUniqueId());

    }

    public void setInheritance(UUID uuid) {
        for (String players : Nebula.getInstance().getUsersYML().getConfigurationSection("Players").getKeys(false)) {
            for (String inheritance : Nebula.getInstance().getUsersYML().getStringList("Players." + players + ".inheritance")) {

            }
        }
    }


    public void addInherit(Player player, String inherit) {
        List<String> inherits = getInherit(player);
        inherits.add(inherit);
        Nebula.getInstance().getUsersYML().set("Players." + player.getUniqueId() + ".inheritance", inherits);
        Nebula.getInstance().getUsersYML().save();
    }
    public List<String> getInherit(Player player) {
        if (!Nebula.getInstance().getUsersYML().contains("Players." + player.getUniqueId() + ".inheritance"))
            Nebula.getInstance().getUsersYML().set("Players." + player.getUniqueId() + ".inheritance", new ArrayList<String>());
        return Nebula.getInstance().getUsersYML().getStringList("Players." + player.getUniqueId() + ".inheritance");
    }




    public void addPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(Nebula.getInstance());
        perms.put(player.getUniqueId(), attachment);
        setPermissions(player);

    }

    public void setPermissions(Player player) {
        PermissionAttachment attachment = perms.get(player.getUniqueId());
            for (String permissions : Nebula.getInstance().getUsersYML().getStringList("Players." + player.getUniqueId() + ".permissions")) {
                attachment.setPermission(permissions, true);
            }
        }

    public void addPerm(Player player, String perm) {
        List<String> permissions = getPerms(player);
        permissions.add(perm);
        Nebula.getInstance().getUsersYML().set("Players." + player.getUniqueId() + ".permissions", permissions);
        Nebula.getInstance().getUsersYML().save();
    }
    public List<String> getPerms(Player player) {
        if (!Nebula.getInstance().getUsersYML().contains("Players." + player.getUniqueId() + ".permissions"))
            Nebula.getInstance().getUsersYML().set("Players." + player.getUniqueId() + ".permissions", new ArrayList<String>());
        return Nebula.getInstance().getUsersYML().getStringList("Players." + player.getUniqueId() + ".permissions");
    }
    public void clear() {
        perms.clear();
    }
}




