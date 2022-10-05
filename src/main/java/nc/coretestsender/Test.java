package nc.coretestsender;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test implements CommandExecutor {
    JavaPlugin plugin;
    public Test(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,  Command command,  String label, String[] args) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            nc.coretestsender.Command test = new nc.coretestsender.Command();
            test.command = "get-player-statistic";
            test.destination = "nc-stats";
            test.source = "tester";
            Map<String, String> data = new HashMap<>();
            data.put("key", "economy_balance");
            test.data = data;
            String json = new Gson().toJson(test);
            out.writeUTF(json);
            System.out.println("Tester sent: " + new Gson().toJson(test));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendPluginMessage(this.plugin, "nc-stats:statisticservice", b.toByteArray());
            Bukkit.getServer().sendPluginMessage(this.plugin, "nc-stats:statisticservice", b.toByteArray());
            plugin.getServer().getMessenger().dispatchIncomingMessage((Player) sender, "nc-stats:statisticservice", b.toByteArray());
        }


        return true;
    }
}
