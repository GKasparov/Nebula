package me.ethan.nebula.rank;

import me.ethan.nebula.Nebula;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManager {

    private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();


    public void addPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(Nebula.getInstance());
        perms.put(player.getUniqueId(), attachment);
        setPermissions(player.getUniqueId());

    }

    public void setPermissions(UUID uuid) {
        PermissionAttachment attachment = perms.get(uuid);
        for (String groups : Nebula.getInstance().getPermissionsYML().getConfigurationSection("Groups").getKeys(false)) {
            for (String permissions : Nebula.getInstance().getPermissionsYML().getStringList("Groups." + groups + ".permissions")) {
                attachment.setPermission(permissions, true);
            }
        }
    }

    public void addPerm(String rank, String perm) {
        List<String> permissions = getPerms(rank);
        permissions.add(perm);
        Nebula.getInstance().getPermissionsYML().set("Groups." + rank + ".permissions", permissions);
        Nebula.getInstance().getPermissionsYML().save();
    }
    public List<String> getPerms(String rank) {
        if (!Nebula.getInstance().getPermissionsYML().contains("Groups." + rank + ".permissions"))
            Nebula.getInstance().getPermissionsYML().set("Groups." + rank + ".permissions", new ArrayList<String>());
        return Nebula.getInstance().getPermissionsYML().getStringList("Groups." + rank + ".permissions");
    }

    public void clear() {
        perms.clear();
    }
}