package me.ethan.nebula;

import lombok.Getter;
import me.ethan.nebula.commands.*;
import me.ethan.nebula.config.Config;
import me.ethan.nebula.database.MySQL;
import me.ethan.nebula.database.SQLSetter;
import me.ethan.nebula.events.PlayerEvents;
import me.ethan.nebula.rank.*;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public final class Nebula extends JavaPlugin {

    @Getter
    private static Nebula instance;
    private Config configYML;
    private Config usersYML;
    private Config permissionsYML;

    private RankManager rankManager;
    private StaffManager staffManager;
    private PermissionManager permissionManager;
    private InheritanceManager inheritanceManager;
    private UserManager userManager;


    private MySQL mySQL;
    private SQLSetter sql;

    @Override
    public void onEnable() {
        instance = this;
        getConfigs();
        getCommands();
        getEvents();
        getManagers();

        try {
            mySQL.connect();
        } catch (ClassNotFoundException ignored) {
        } catch (SQLException throwables) {
            Bukkit.getConsoleSender().sendMessage(StringUtils.format(StringUtils.Prefix() + "Database not found"));
        }
        if (mySQL.isConnected()) {
            Bukkit.getConsoleSender().sendMessage(StringUtils.format(StringUtils.Prefix() + "Database connected."));
            sql.createPlayerData();
            sql.createRankData();
            sql.createStaffData();
        }



    }


    @Override
    public void onDisable() {
        instance = null;
        getUserManager().clear();
        getPermissionManager().clear();
        saveConfig();
    }

    void getConfigs() {
        configYML = new Config(this, "config", this.getDataFolder().getAbsolutePath());
        usersYML = new Config(this, "users", this.getDataFolder().getAbsolutePath());
        permissionsYML = new Config(this, "permissions", this.getDataFolder().getAbsolutePath());
    }

    void getCommands() {
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("staffhistory").setExecutor(new StaffHistoryCommand());
        getCommand("user").setExecutor(new UserCommand());
        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("promote").setExecutor(new PromoteCommand());

    }

    void getEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    void getManagers() {
        rankManager = new RankManager();
        mySQL = new MySQL();
        sql = new SQLSetter();
        staffManager = new StaffManager();
        permissionManager = new PermissionManager();
        inheritanceManager = new InheritanceManager();
        userManager = new UserManager();
    }
}



