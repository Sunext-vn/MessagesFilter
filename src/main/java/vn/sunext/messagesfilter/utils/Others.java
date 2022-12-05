package vn.sunext.messagesfilter.utils;

import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import vn.sunext.messagesfilter.constructors.PlaceholderDetail;
import vn.sunext.messagesfilter.managers.PathManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Others {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F\\d]{6}");

    public static String color(String text) {
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> text) {
        List<String> result = new ArrayList<>();

        for (String s : text) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                String color = s.substring(matcher.start(), matcher.end());
                s = s.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(s);
            }
            result.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return result;
    }

    public static Boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static String stringsToString(String[] strings) {
        StringBuilder result = new StringBuilder();

        int i = 1;

        for (String str : strings) {
            result.append(str);

            if (i != strings.length)
                result.append(" ");

            i++;
        }

        return result.toString();
    }

    public static Boolean canUseCommand(CommandSender sender) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (PathManager.ALLOW_OP)
            if (player.isOp())
                return true;
            else
                return player.hasPermission(PathManager.ADMIN_PERMISSION);

        return player.hasPermission(PathManager.ADMIN_PERMISSION);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(color(PathManager.PREFIX + message));
    }

    public static void sendMessage(CommandSender sender, String message, PlaceholderDetail placeholderDetail) {
        String result = message.replace("{FILTER}", placeholderDetail.getFilterName());

        sendMessage(sender, result);
    }

    public static String jsonToString(String json) {
        JSONObject jsonObject = new JSONObject(json);

        if (jsonObject.getJSONArray("extra") == null)
            return "";

        JSONArray array = jsonObject.getJSONArray("extra");

        StringBuilder resultString = new StringBuilder();

        for (Object object : array) {
            JSONObject object1 = new JSONObject(object.toString());

            resultString.append(object1.getString("text"));
        }

        return resultString.toString();
    }

}
