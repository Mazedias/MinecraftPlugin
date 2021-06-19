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

public class Transfer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            Minecraftplugin.INSTANCE.log("Da du kein Spieler bist kannst du diesen Command nicht ausführen!");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0) {
            // Player didn't entered an amount + player name
            player.sendMessage(Minecraftplugin.PREFIX + "Du kannst kein Geld an dich selber transferieren");
            player.sendMessage(Minecraftplugin.PREFIX + "Benutze diesen Command: §c /transfer <Player> <amount>§7§o!");
        }else if (args.length == 1) {
            // Player didn't entered an amount / player name
            player.sendMessage(Minecraftplugin.PREFIX + "Du musst eine Summe oder einen Namen angeben, die transferiert werden soll.");
            player.sendMessage(Minecraftplugin.PREFIX + "Benutze diesen Command: §c /transfer <Player> <amount>§7§o!");
        }else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                String player_uuid = player.getUniqueId().toString();
                String target_uuid = target.getUniqueId().toString();
                //-----Transfer Money-----//
                DatabaseConnection getquery = new DatabaseConnection();
                int amount = Integer.parseInt(args[1]);
                getquery.TransferMoney(player, player_uuid, target_uuid, amount);
                //-----Transfer Money-----//
                target.sendMessage(Minecraftplugin.PREFIX + "Der spieler §6" + args[0] + "§7§o hat dir §6" + args[1] + "§7§o überwiesen.");
            } else {
                player.sendMessage(Minecraftplugin.PREFIX + "§cDer Spieler §6" + args[0] + "§c konnte nicht gefunden werden!");
                player.sendMessage(Minecraftplugin.PREFIX + "Benutze diesen Command: §c /transfer <Player> <amount>§7§o!");
            }
        }
        return true;
    }
}
