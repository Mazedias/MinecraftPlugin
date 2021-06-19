package plugin.minecraftplugin.Listener.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import plugin.minecraftplugin.Minecraftplugin;
import plugin.minecraftplugin.utils.ItemBuilder;
import java.util.Objects;

/*
Diese Datei ist nicht von @Mazedias, Sie ist ein Mitschrieb von einem Tutorial als Lernzweck
finished | tested
*/

public class Navigator implements Listener {
    public final Inventory navigatorInventory;
    public Navigator(int rows) {
        this.navigatorInventory = Bukkit.createInventory(null, rows * 9, "§5Navigator");

        //-----Add a new Item to the Navigator Inventory-----//
        this.navigatorInventory.setItem(4, new ItemBuilder(Material.NETHER_BRICK)
                .displayname("§ePlate PVP")
                .lore("", "§zEin lustiger Modi")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build());
        //-----Add a new Item to the Navigator Inventory-----//
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.COMPASS) {
            event.getPlayer().openInventory(this.navigatorInventory);
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onItemClick(InventoryClickEvent event) {
        // Is the used inventory the shop inventory?
        if(Objects.equals(event.getClickedInventory(), this.navigatorInventory)) {
            event.setCancelled(true);

            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() == Material.NETHER_BRICK) {
                event.getWhoClicked().sendMessage(Minecraftplugin.PREFIX + "Du hast etwas gekauft!");
            }

        }
    }
}
