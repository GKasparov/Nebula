package me.ethan.nebula.rank;

import me.ethan.nebula.Nebula;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InheritanceManager {

    public void addInheritance(Player player) {
        setInheritance(player.getUniqueId());

    }
    public void setInheritance(UUID uuid) {
        for (String groups : Nebula.getInstance().getPermissionsYML().getConfigurationSection("Groups").getKeys(false)) {
            for (String inheritance : Nebula.getInstance().getPermissionsYML().getStringList("Groups." + groups + ".inheritance")) {

            }
        }
    }


    public void addInherit(String rank, String inherit) {
        List<String> inherits = getInherit(rank);
        inherits.add(inherit);
        Nebula.getInstance().getPermissionsYML().set("Groups." + rank + ".inheritance", inherits);
        Nebula.getInstance().getPermissionsYML().save();
    }

    public List<String> getInherit(String rank) {
        if (!Nebula.getInstance().getPermissionsYML().contains("Groups." + rank + ".inheritance"))
            Nebula.getInstance().getPermissionsYML().set("Groups." + rank + ".inheritance", new ArrayList<String>());
        return Nebula.getInstance().getPermissionsYML().getStringList("Groups." + rank + ".inheritance");
    }
}


