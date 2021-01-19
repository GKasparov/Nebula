package me.ethan.nebula.commands;

import me.ethan.nebula.Nebula;
import me.ethan.nebula.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("nebula.commands.rank")) {
                if (args.length == 0) {
                    List<String> rankmsg = new ArrayList<>();
                    rankmsg.add("&b&m------------------------------");
                    rankmsg.add(StringUtils.Prefix() + "Showing rank command usage;");
                    rankmsg.add("&d/rank create <name> <prefix> » &fCreate a rank with a prefix.");
                    rankmsg.add("&d/rank set <player> <rank> » &fSets a rank to the player.");
                    rankmsg.add("&d/rank perm add <rank> <node> » &fAdds a permission to the rank.");
                    rankmsg.add("&d/rank inherit add <rank> <parent> » &fAdds an inheritance to the rank.");
                    rankmsg.add("&d/rank delete <name> » &fDeletes a rank.");
                    rankmsg.add("&d/rank list <name> » &fLists all the ranks.");
                    rankmsg.add("&b&m------------------------------");
                    rankmsg.forEach(rank -> player.sendMessage(StringUtils.format(rank)));
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String rank = args[1];
                        String prefix = args[2];
                        if (!Nebula.getInstance().getRankManager().rankCheck(rank)) {
                            player.sendMessage(StringUtils.format(StringUtils.Prefix() + "You have successfully created the rank &d" + rank + "&f with the prefix &r" + prefix + "&f."));
                            Nebula.getInstance().getPermissionManager().addPerm(rank, "this.is.a.perm");
                            Nebula.getInstance().getInheritanceManager().addInherit(rank, "Default");
                            Nebula.getInstance().getRankManager().createRank(rank, prefix);
                        } else if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                            player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Seems like &d" + rank + " &falready exists. try setting the rank to a player or checking the database."));

                        }
                        return true;
                    }
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("set")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        String rank = args[2];
                        if (target.hasPermission("nebula.utils.staff")) {
                            target.sendMessage(StringUtils.format(StringUtils.Prefix() + " As you are staff the rank has been added to your inheritance."));
                            Nebula.getInstance().getUserManager().addInherit(player, rank);
                        } else {
                            if (!target.hasPermission("nebula.utils.staff")) {
                                if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                                    Nebula.getInstance().getRankManager().setRank(target, rank);
                                    Nebula.getInstance().getRankManager().updateRank(target, rank);
                                    player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Added &d" + rank + " &fto &d" + target.getName()));
                                } else if (!Nebula.getInstance().getRankManager().rankCheck(rank)) {
                                    player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Could not find &d" + rank + " &fhas it been created?"));
                                }
                                return true;
                            }
                        }
                    }
                }

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("delete")) {
                        String rank = args[1];
                        if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                            player.sendMessage(StringUtils.Prefix() + "You have successfully deleted &d" + rank);
                            Nebula.getInstance().getRankManager().deleteRank(rank);
                        } else if (!Nebula.getInstance().getRankManager().rankCheck(rank))
                            player.sendMessage(StringUtils.Prefix() + "Unable to delete &d" + rank + " as it does not exist.");
                        return true;
                    }
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        player.sendMessage(StringUtils.format("&b&m------------------------------"));
                        player.sendMessage(StringUtils.format(StringUtils.Prefix() + "Listing all ranks in the database."));
                        player.sendMessage("");
                        Nebula.getInstance().getRankManager().listRanks().forEach(rank -> player.sendMessage(StringUtils.format(rank)));
                        player.sendMessage(StringUtils.format("&b&m------------------------------"));
                        return true;
                    }
                }
                if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("perm")) {
                        if (args[1].equalsIgnoreCase("add")) {
                            String rank = args[2];
                            String perm = args[3];
                            if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                                player.sendMessage(StringUtils.format("Added " + perm + " to " + rank));
                                Nebula.getInstance().getPermissionManager().addPerm(rank, perm);
                            } else if (!Nebula.getInstance().getRankManager().rankCheck(rank))
                                player.sendMessage(StringUtils.format("Could not add " + perm + " to " + rank));
                            return true;
                        }
                    }
                }
                if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("inherit")) {
                        if (args[1].equalsIgnoreCase("add")) {
                            String rank = args[2];
                            String inherit = args[3];
                            if (Nebula.getInstance().getRankManager().rankCheck(rank)) {
                                player.sendMessage(StringUtils.format("Added " + inherit + " to " + rank));
                                Nebula.getInstance().getInheritanceManager().addInherit(rank, inherit);
                            } else if (!Nebula.getInstance().getRankManager().rankCheck(rank))
                                player.sendMessage(StringUtils.format("Could not add " + inherit + " to " + rank));
                            return true;
                        }
                    }
                }
                if (args.length > 4) {
                    player.sendMessage("Incorrect Syntax. Please type /rank for more info");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Insufficient Permissions");
            }
        }

        return false;
    }
}
