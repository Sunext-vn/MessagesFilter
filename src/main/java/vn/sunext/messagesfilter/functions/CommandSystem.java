package vn.sunext.messagesfilter.functions;

import org.bukkit.command.CommandSender;
import vn.sunext.messagesfilter.MessagesFilter;
import vn.sunext.messagesfilter.constructors.PlaceholderDetail;
import vn.sunext.messagesfilter.managers.PathManager;
import vn.sunext.messagesfilter.utils.Others;

import java.util.List;

public class CommandSystem {

    private final MessagesFilter plugin = MessagesFilter.getInstance();

    public void commandList(CommandSender sender) {
        for (String message : PathManager.COMMANDS_LIST_MESSAGE)
            Others.sendMessage(sender, message);
    }

    public void reload(CommandSender sender) {
        plugin.onReload();

        Others.sendMessage(sender, PathManager.RELOAD_MESSAGE);
    }

    public void addFilter(CommandSender sender, String[] filterMessages) {
        String filterMessage = Others.stringsToString(getCorrectStrings(filterMessages));

        if (isHaveFilter(filterMessage)) {
            Others.sendMessage(sender, PathManager.ALREADY_HAVE_FILTER_MESSAGE, new PlaceholderDetail(filterMessage));

            return;
        }

        plugin.getFileManager().addNewFilterMessage(filterMessage);
        plugin.getPathManager().register();
        plugin.getFilterManager().loadFilterMessage();

        Others.sendMessage(sender, PathManager.ADD_FILTER_MESSAGE, new PlaceholderDetail(filterMessage));
    }

    public void removeFilter(CommandSender sender, Integer number) {
        if (!isFilterExist(number)) {
            Others.sendMessage(sender, PathManager.FILTER_NOT_FOUND_MESSAGE);

            return;
        }

        String filterMessage = plugin.getFilterManager().getFilter_list().get(number);

        plugin.getFileManager().removeFilterMessage(filterMessage);
        plugin.getPathManager().register();
        plugin.getFilterManager().loadFilterMessage();

        Others.sendMessage(sender, PathManager.REMOVE_FILTER_MESSAGE, new PlaceholderDetail(filterMessage));
    }

    public void getFilterList(CommandSender sender) {
        Others.sendMessage(sender, PathManager.FILTER_LIST_MESSAGE);

        boolean changeColor = false;

        for (int i = 0; i < plugin.getFilterManager().getFilter_list().size(); ++i) {
            String filter = plugin.getFilterManager().getFilter_list().get(i);

            if (changeColor) {
                Others.sendMessage(sender, " &c" + i + ". &7" + filter);
                changeColor = false;
            } else {
                Others.sendMessage(sender, " &c" + i + ". &a" + filter);
                changeColor = true;
            }
        }
    }

    private String[] getCorrectStrings(String[] args) {
        String[] result = new String[args.length - 2];

        for (int i = 0; i < args.length; i++) {
            if (i > 1)
                result[i - 2] = args[i];
        }

        return result;
    }

    private Boolean isFilterExist(Integer number) {
        List<String> filterList = plugin.getFilterManager().getFilter_list();

        return number >= filterList.size();
    }

    private Boolean isHaveFilter(String filterMessage) {
        return plugin.getFilterManager().getFilter_list().contains(filterMessage);
    }

}
