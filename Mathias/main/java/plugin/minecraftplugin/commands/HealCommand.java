package plugin.minecraftplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.minecraftplugin.Minecraftplugin;

/*
By Mathias
finished | tested
*/

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Minecraftplugin.INSTANCE.log("Du bist kein Spieler!");
            return true;
        }

        // Define Player
        Player player = (Player) sender;

        //Heal
        if(player.hasPermission("Admin")) {
            if(args.length == 0) {
                player.setHealth(20d);
                player.setFoodLevel(20);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.2f, 1.2f);
                player.sendMessage(Minecraftplugin.PREFIX + "Du wurdest geheilt.");
            } else if (args.length == 1) { // Heal other Players
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setHealth(20d);
                    target.setFoodLevel(20);
                    target.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.2f, 1.2f);
                    target.sendMessage(Minecraftplugin.PREFIX + player.getDisplayName() + " hat dich geheilt.");
                    player.sendMessage(Minecraftplugin.PREFIX + "Du hast geheilt.");
                } else {
                    player.sendMessage(Minecraftplugin.PREFIX + "§cDer Spieler §6" + args[0] + "§c konnte nicht gefunden werden.");
                }
            } else {
                player.sendMessage(Minecraftplugin.PREFIX + "§cBitte nutze §6/heal <PLAYER> §c!");
            }
        }

        return true;
    }
}
