package vn.sunext.messagesfilter.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vn.sunext.messagesfilter.MessagesFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathManager {

    private final MessagesFilter plugin = MessagesFilter.getInstance();

    YamlConfiguration config = plugin.getFileManager().config;

    public static List<String> FILTER_LIST = new ArrayList<>();

    public static Boolean ALLOW_OP = false;

    public static String ADMIN_PERMISSION = "";

    public static String LANG = "";

    public static String PREFIX = "";
    public static String RELOAD_MESSAGE = "";
    public static String ADD_FILTER_MESSAGE = "";
    public static String REMOVE_FILTER_MESSAGE = "";
    public static String ALREADY_HAVE_FILTER_MESSAGE = "";
    public static String FILTER_LIST_MESSAGE = "";
    public static String NO_PERMISSION_MESSAGE = "";
    public static String INVALID_NUMBER_MESSAGE = "";
    public static String FILTER_NOT_FOUND_MESSAGE = "";
    public static List<String> COMMANDS_LIST_MESSAGE = new ArrayList<>();

    public void register() {
        PREFIX = config.getString("prefix");

        LANG = config.getString("lang");

        FILTER_LIST.clear();
        FILTER_LIST = config.getStringList("filter-messages");

        ALLOW_OP = config.getBoolean("options.allow-op");
        ADMIN_PERMISSION = config.getString("permissions.admin");

        loadMessages(LANG);
    }

    private void loadMessages(String lang) {
        String resultLang = lang;

        if (lang.equalsIgnoreCase("vn") && lang.equalsIgnoreCase("en"))
            resultLang = "en";

        File messages_file = new File(plugin.getDataFolder(), "/messages/messages_" + resultLang + ".yml");
        YamlConfiguration messages = YamlConfiguration.loadConfiguration(messages_file);

        RELOAD_MESSAGE = messages.getString("messages.reload");
        ADD_FILTER_MESSAGE = messages.getString("messages.add-filter");
        REMOVE_FILTER_MESSAGE = messages.getString("messages.remove-filter");
        ALREADY_HAVE_FILTER_MESSAGE = messages.getString("messages.already-have-filter");
        FILTER_LIST_MESSAGE = messages.getString("messages.filter-list");
        NO_PERMISSION_MESSAGE = messages.getString("messages.no-permission");
        COMMANDS_LIST_MESSAGE = messages.getStringList("messages.commands");
        INVALID_NUMBER_MESSAGE = messages.getString("messages.invalid-number");
        FILTER_NOT_FOUND_MESSAGE = messages.getString("messages.filter-not-found");
    }

}
