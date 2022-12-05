package vn.sunext.messagesfilter.managers;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import vn.sunext.messagesfilter.MessagesFilter;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager {

    private final MessagesFilter plugin = MessagesFilter.getInstance();

    private final List<String> messageFileName = new ArrayList<>();

    public File config_file = new File(plugin.getDataFolder(), "config.yml");
    public YamlConfiguration config = YamlConfiguration.loadConfiguration(config_file);

    public void register() {
        createFolder("messages");

        registerMessageFiles();
        loadMessagesFiles();
    }

    @SneakyThrows
    private void registerMessageFiles() {
        CodeSource src = getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            while(true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null)
                    break;
                String name = e.getName();
                if (name.startsWith("messages/messages_")) {
                    messageFileName.add(name.replace("messages/", ""));
                }
            }
        }
    }

    @SneakyThrows
    private void loadMessagesFiles() {

        for (String fileName : messageFileName) {
            setYaml("messages", fileName);
        }

    }

    @SneakyThrows
    public void addNewFilterMessage(String filterMessage) {
        List<String> resultFilter = config.getStringList("filter-messages");
        resultFilter.add(filterMessage);

        setFilterMessages(resultFilter);
    }

    @SneakyThrows
    public void removeFilterMessage(String filterMessage) {
        List<String> resultFilter = config.getStringList("filter-messages");
        resultFilter.remove(filterMessage);

        setFilterMessages(resultFilter);
    }

    @SneakyThrows
    private void setFilterMessages(List<String> filterMessages) {
        config.set("filter-messages", filterMessages);

        config.save(config_file);
    }

    @SneakyThrows
    private void setYaml(String folderName, String fileName) {
        String path = "/" + folderName + "/" + fileName;

        File yaml_file = new File(plugin.getDataFolder(), path);

        if (!yaml_file.exists())
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(path)), Paths.get("plugins/MessagesFilter" + path), StandardCopyOption.REPLACE_EXISTING);
    }

    private void createFolder(String folderName) {
        File folder = new File(plugin.getDataFolder(), folderName);
        if (!folder.exists()) folder.mkdirs();
    }

}
