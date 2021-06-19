package plugin.minecraftplugin.ShopSystem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import plugin.minecraftplugin.Minecraftplugin;

/*
By Mathias
finished | tested
15.02.2021
*/

public class ShopVillagerUsage implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            Minecraftplugin.INSTANCE.log("You are not a player!");
            return true;
        }

        Player player = (Player) sender;
        if(player.hasPermission("Admin")) {
            if(args.length == 0) {
                //----Spawn the Villager----//
                new ShopVillager(player.getLocation());
                //----Spawn the Villager----//
                player.sendMessage(Minecraftplugin.PREFIX + "You successfully created a new Shop Villager.");
            }else {
                player.sendMessage(Minecraftplugin.PREFIX + "Please use §c/setshop §7§o.");
            }
        }else {
            player.sendMessage(Minecraftplugin.PREFIX + "§cYou cannot execute this command!");
        }
        return true;
    }

    @EventHandler
    public void avoidShopDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Villager)) return;
        Villager damaged_villager = (Villager) event.getEntity();
        if(!damaged_villager.getCustomName().equals(ShopVillager.VILLAGER_NAME)) return;
        //-----Disable Damage-----//
        event.setCancelled(true);
        //-----Disable Damage-----//

        //Allow Admins to remove the Shop Villager
        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if(player.hasPermission("Admin")) {
            if(player.getItemInHand().getType() == Material.WOODEN_AXE) {
                damaged_villager.setHealth(0);
                player.sendMessage(Minecraftplugin.PREFIX + "§cDu hast den ShopVillager getötet!");
            }
        }
    }
}
