package plugin.minecraftplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.minecraftplugin.Minecraftplugin;
import plugin.minecraftplugin.utils.FileConfig;
import plugin.minecraftplugin.utils.LocationUtils;

/*
Diese Datei ist nicht von @Mazedias, Sie ist ein Mitschrieb von einem Tutorial als Lernzweck
finished | tested
*/

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        // Load File Config
        FileConfig spawn = new FileConfig("locatons.yml");
        if(label.equalsIgnoreCase("setspawn")) {
            // Create new Spawn with Player Location
            spawn.set("spawn", LocationUtils.log2Str(player.getLocation()));
            spawn.saveConfig();
            player.sendMessage(Minecraftplugin.PREFIX + "§aSpawn saved.");
            player.sendMessage(Minecraftplugin.PREFIX + "§cDu hast keine Rechte für diesen Befehl.");
            return true;
        }

        if(spawn.contains("spawn")) {
            LocationUtils.teleport(player, LocationUtils.str2Loc(spawn.getString("spawn")));
        } else {
            player.sendMessage(Minecraftplugin.PREFIX + "§cEs wurde noch kein Spawnpunkt gesetzt.");
        }

        return true;
    }
}
