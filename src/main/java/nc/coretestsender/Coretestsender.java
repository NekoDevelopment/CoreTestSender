package nc.coretestsender;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public final class Coretestsender extends JavaPlugin implements PluginMessageListener  {

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("test").setExecutor(new Test(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "nc-stats:statisticservice");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "nc-stats:statisticservice", this);
        System.out.println(this.getServer().getMessenger().getIncomingChannels());
        System.out.println(this.getServer().getMessenger().getOutgoingChannels());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @Override
    public void onPluginMessageReceived(String channel,  Player player, byte[] message) {
        if (!channel.contains("statisticservice")) {
            return;
        }

        String json = new String(message);
        if(!json.startsWith("{")) {
            json = json.substring(json.indexOf("{"));
            json = json.trim();
        }
        Command command = new Gson().fromJson(json, Command.class);
        if(command.destination.equalsIgnoreCase("tester")) {
                System.out.println("Tester received: " + json);
        }
    }
}
