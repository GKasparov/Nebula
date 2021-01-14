package me.ethan.nebula.utils;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.util.List;


public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("Cannot instantiate a utility class.");
    }

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> format(List<String> messages) {

        List<String> toReturn = Lists.newArrayList();

        messages.forEach(message -> toReturn.add(format(message)));

        return toReturn;
    }

    public static String NoPerm() {

        return format("&dNebula » &fYou seem to lack this permission. Believe this is an error? Contact an admin.");
    }

    public static String Prefix() {
        return format("&dNebula » &f");
    }


}