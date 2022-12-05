package vn.sunext.messagesfilter.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import vn.sunext.messagesfilter.MessagesFilter;
import vn.sunext.messagesfilter.utils.Others;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FilterManager {

    private final MessagesFilter plugin = MessagesFilter.getInstance();

    private final List<String> filter_list = new ArrayList<>();

    public void register() {
        loadFilterMessage();

        filterMessages();
    }

    public void loadFilterMessage() {
        filter_list.addAll(PathManager.FILTER_LIST);
    }

    public void filterMessages() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.CHAT) {
            public void onPacketSending(PacketEvent event) {
                String message = Others.jsonToString(event.getPacket().getChatComponents().getValues().get(0).getJson());

                if (filter_list.stream().anyMatch(message::equalsIgnoreCase))
                    event.setCancelled(true);
            }
        });
    }

}
