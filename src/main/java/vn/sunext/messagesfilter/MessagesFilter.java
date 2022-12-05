package vn.sunext.messagesfilter;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import vn.sunext.messagesfilter.commands.MainCommand;
import vn.sunext.messagesfilter.functions.CommandSystem;
import vn.sunext.messagesfilter.managers.FileManager;
import vn.sunext.messagesfilter.managers.FilterManager;
import vn.sunext.messagesfilter.managers.PathManager;

import java.util.Objects;

@Getter
public final class MessagesFilter extends JavaPlugin {

    private static MessagesFilter plugin;

    private FileManager fileManager;
    private PathManager pathManager;
    private FilterManager filterManager;
    private CommandSystem commandSystem;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        fileManager = new FileManager();
        fileManager.register();

        pathManager = new PathManager();
        pathManager.register();

        filterManager = new FilterManager();
        filterManager.register();

        commandSystem = new CommandSystem();

        Objects.requireNonNull(getCommand("messagesfilter")).setExecutor(new MainCommand());
    }

    public void onReload() {
        onDisable();
        onEnable();
    }

    public static MessagesFilter getInstance() {
        return plugin;
    }
}
