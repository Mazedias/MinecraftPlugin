package plugin.minecraftplugin.commands.MoneySystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.minecraftplugin.Database.DatabaseConnection;
import plugin.minecraftplugin.Minecraftplugin;

/*
By Mathias
finished | not tested
09.02.2021
*/

public class AddMoney implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if(player.hasPermission("Admin")) {
            if(args.length < 2) {
                // Player didn't entered a player name and no amount
                player.sendMessage(Minecraftplugin.PREFIX + "Bitte nutze diesen Command: §6/add <player> <amount> §7§o!");
            }else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    String Target_UUID = target.getUniqueId().toString();
                    //-----Execute SQL Query-----//
                    DatabaseConnection getquery = new DatabaseConnection();
                    int amount = Integer.parseInt(args[1]);
                    getquery.AddMoney(Target_UUID, amount);
                    //-----Execute SQL Query-----//
                    player.sendMessage(Minecraftplugin.PREFIX + "Du hast dem Spieler §6" + args[0] + " " + args[1] + "§7§o Mazzotaler geschenkt.");
                }else {
                    player.sendMessage(Minecraftplugin.PREFIX + "Der von dir ausgewählte Spieler §6" + args[0] + "§7§o ist nicht auffindbar.");
                    player.sendMessage(Minecraftplugin.PREFIX + "Bitte nutze diesen Command: §6/add <player> <amount> §7§o!");
                }
            }else {
                player.sendMessage(Minecraftplugin.PREFIX + "Bitte nutze diesen Command: §6/add <player> <amount> §7§o!");
            }
        }else {
            player.sendMessage(Minecraftplugin.PREFIX + "§cFür diesen Command fehlen dir die Rechte!!");
        }
        return true;
    }
}
