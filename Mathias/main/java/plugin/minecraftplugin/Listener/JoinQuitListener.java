package plugin.minecraftplugin.Listener;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import plugin.minecraftplugin.Database.DatabaseConnection;

/*
By Mathias
finished | tested
*/

public class JoinQuitListener implements Listener {
    // Player Joins
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Define Player
        Player player = event.getPlayer();
        String player_uuid = player.getUniqueId().toString();
        String playername = player.getName();

        //----------Insert User in Database----------//
        DatabaseConnection getquery = new DatabaseConnection();
        getquery.InsertNewUser(playername, player_uuid);
        //----------Insert User in Database----------//

        // Player executes automaticly this command
        // player.performCommand("spawn");

        // Add an Item to Players Inventory
        // player.getInventory().setItem(8, new ItemStack(Material.COMPASS)); (Important for Navigator)

        // Change Player Name + Join Message
        player.setDisplayName("§9" + player.getName());
        player.setPlayerListName("§c§lA§r " + player.getDisplayName());
        event.setJoinMessage("§a§l+ §7" + player.getDisplayName());
    }

    //Player Quits
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("§c§l- §7" + player.getDisplayName());
    }
}
