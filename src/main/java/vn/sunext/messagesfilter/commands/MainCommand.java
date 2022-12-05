package vn.sunext.messagesfilter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import vn.sunext.messagesfilter.MessagesFilter;
import vn.sunext.messagesfilter.managers.PathManager;
import vn.sunext.messagesfilter.utils.Others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand implements TabExecutor {

    private final MessagesFilter plugin = MessagesFilter.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Others.canUseCommand(sender)) {
            Others.sendMessage(sender, PathManager.NO_PERMISSION_MESSAGE);

            return false;
        }

        switch (args.length) {
            case 0:
                plugin.getCommandSystem().commandList(sender);
                break;
            case 1:
                switch (args[0]) {
                    case "reload":
                        plugin.getCommandSystem().reload(sender);
                        break;
                    case "list":
                        plugin.getCommandSystem().getFilterList(sender);
                        break;
                    case "add":
                    case "remove":
                        plugin.getCommandSystem().commandList(sender);
                        break;
                }
                break;
            case 2:
                if ("remove".equals(args[0])) {
                    if (Others.isInteger(args[1])) {
                        Others.sendMessage(sender, PathManager.INVALID_NUMBER_MESSAGE);

                        break;
                    }

                    Integer number = Integer.parseInt(args[1]);

                    plugin.getCommandSystem().removeFilter(sender, number);
                    break;
                }
        }

        if (args.length > 1) {
            if ("add".equals(args[0])) {
                plugin.getCommandSystem().addFilter(sender, args);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();

        List<String> commands = Arrays.asList("reload", "add", "remove", "list");

        List<String> filterNumber = new ArrayList<>();

        for (int i = 0; i < plugin.getFilterManager().getFilter_list().size(); i++)
            filterNumber.add(String.valueOf(i));

        switch (args.length) {
            case 1:
                commands.forEach(s -> {
                    if (s.startsWith(args[0].toLowerCase()))
                        result.add(s);
                });
                break;
            case 2:
                if ("remove".equals(args[0])) {
                    filterNumber.forEach(s -> {
                        if (s.startsWith(args[1].toLowerCase()))
                            result.add(s);
                    });
                }
        }

        if (!result.isEmpty())
            return result;

        return null;
    }

}
