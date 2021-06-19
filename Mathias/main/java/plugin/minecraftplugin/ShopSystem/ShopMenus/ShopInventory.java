package plugin.minecraftplugin.ShopSystem.ShopMenus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import plugin.minecraftplugin.Database.DatabaseConnection;
import plugin.minecraftplugin.Minecraftplugin;
import plugin.minecraftplugin.ShopSystem.ShopVillager;
import plugin.minecraftplugin.utils.ItemBuilder;
import java.util.Objects;

/*
By Mathias
not finished | not tested
!ENTER FINISH DATE!
Todo
    - Enter Item prices into Database
    - If some Items are stackt (32 Stone for 1 Mazzotaler): Enter Amount in Database + Write Funktion to get the Amount
    - Develope an Idea how the Special Offers Menu should work
*/

public class ShopInventory implements Listener {

    //private final Inventory shopInventory; // Main ShopInventory
    public final Inventory shopInventory;
    public final Inventory sellMenu;
    public final Inventory buyMenu;
    public final Inventory special_Offers;

    public ShopInventory() {
        //--Buy Items--//
        Material[] buy_Items = {Material.STONE, Material.SPONGE, Material.MOSSY_COBBLESTONE, Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.STONE_BRICKS,
                               Material.CRACKED_STONE_BRICKS, Material.NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS, Material.CHISELED_NETHER_BRICKS, Material.END_STONE, Material.END_STONE_BRICKS,
                               Material.TERRACOTTA, Material.ORANGE_DYE, Material.MAGENTA_DYE, Material.LIGHT_BLUE_DYE, Material.YELLOW_DYE, Material.LIME_DYE, Material.CYAN_DYE, Material.PURPLE_DYE,
                               Material.BLUE_DYE, Material.GREEN_DYE, Material.RED_DYE, Material.SAND, Material.GRAVEL, Material.SEA_LANTERN, Material.BLACKSTONE, Material.POLISHED_BLACKSTONE,
                               Material.POLISHED_BLACKSTONE_BRICKS, Material.CRYING_OBSIDIAN, Material.BEDROCK, Material.VINE, Material.EMERALD};
        //--Sell Items--//
        Material[] sell_Items = {Material.COBBLESTONE, Material.STONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE, Material.DIRT, Material.BLACKSTONE, Material.OAK_SAPLING, Material.SPRUCE_SAPLING,
                               Material.SUGAR_CANE, Material.HONEY_BLOCK, Material.HONEYCOMB_BLOCK, Material.REDSTONE, Material.WHEAT_SEEDS, Material.ROTTEN_FLESH};

        //----------Main Menu----------//
        this.shopInventory = Bukkit.createInventory(null, 9, "§1ShopMenu");
        this.shopInventory.setItem(2, new ItemBuilder(Material.GREEN_STAINED_GLASS)
                .displayname("§2Buy Menu")
                .lore("Buy Items").flag(ItemFlag.HIDE_ATTRIBUTES)
                .build());
        this.shopInventory.setItem(4, new ItemBuilder(Material.NETHER_STAR)
                .displayname("§g§l§oSpecial Offers")
                .build());
        this.shopInventory.setItem(6, new ItemBuilder(Material.RED_STAINED_GLASS)
                .displayname("§2Sell Menu")
                .lore("Sell Items").flag(ItemFlag.HIDE_ATTRIBUTES)
                .build());

        //----------Sell Menu----------//
        this.sellMenu = Bukkit.createInventory(null, 36, "§cSell Menu");
        for (int i = 0; i < sell_Items.length; i++) {
            this.sellMenu.setItem(i, new ItemBuilder(sell_Items[i]).build());
        }

        //----------Buy Menu----------//
        this.buyMenu = Bukkit.createInventory(null, 36, "§2Sell Menu");
        for (int i = 0; i < buy_Items.length; i++) {
            this.buyMenu.setItem(i, new ItemBuilder(buy_Items[i]).build());
        }

        //----------Special Offers----------//
        this.special_Offers = Bukkit.createInventory(null, 18, "§g§l§oSpecial Offers");
        this.special_Offers.setItem(4, new ItemBuilder(Material.DIAMOND)
                .displayname("§7Coming Soon...")
                .build());
    }

    @EventHandler
    public void ShopInteract(PlayerInteractEntityEvent event) {
        if(!(event.getRightClicked() instanceof Villager)) return;
        Villager clicked_villager = (Villager) event.getRightClicked();
        if(clicked_villager.getCustomName().equals(ShopVillager.VILLAGER_NAME)) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            //-----Open Shop Inventory-----//
            player.openInventory(this.shopInventory);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        Inventory clicked_Inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        String player_uuid = player.getUniqueId().toString();

        if(Objects.equals(clicked_Inventory, this.shopInventory)) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS) {
                player.sendMessage(Minecraftplugin.PREFIX + "Du wirst zum BuyMenu weitergeleitet...");
                player.openInventory(this.buyMenu);
            }else if(event.getCurrentItem().getType() == Material.RED_STAINED_GLASS) {
                player.sendMessage(Minecraftplugin.PREFIX + "Du wirst zum SellMenu weitergeleitet...");
                player.openInventory(this.sellMenu);
            }else if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.sendMessage(Minecraftplugin.PREFIX + "Du wirst zu SpecialOffers weitergeleitet...");
                player.openInventory(this.special_Offers);
            }
        }else if(Objects.equals(clicked_Inventory, this.sellMenu)) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            Material clicked_Material = event.getCurrentItem().getType();

            if (player.getInventory().contains(clicked_Material)) {
                String get_price = clicked_Material.toString();
                DatabaseConnection getquery = new DatabaseConnection();
                int itemprice = getquery.GetPrice(get_price, 2);
                getquery.AddMoney(player_uuid, itemprice);
                player.getInventory().removeItem(new ItemStack(clicked_Material, 1));

                player.sendMessage(Minecraftplugin.PREFIX + "Du hast etwas verkauft.");
            }else {
                player.sendMessage(Minecraftplugin.PREFIX + "§4Du besitzt dieses Item nicht!");
            }

        }else if(Objects.equals(clicked_Inventory, this.buyMenu)) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            Material clicked_Material = event.getCurrentItem().getType();

            String get_price = clicked_Material.toString();
            System.out.println(get_price);
            DatabaseConnection getquery = new DatabaseConnection();
            int itemprice = getquery.GetPrice(get_price, 1);
            int PlayerMoney = getquery.GetPlayerMoney(player_uuid);
            if (PlayerMoney >= itemprice) {
                getquery.RemoveMoney(player_uuid, itemprice);
                player.getInventory().addItem(new ItemStack(clicked_Material, 1));

                player.sendMessage(Minecraftplugin.PREFIX + "Du hast etwas gekauft.");
            }else {
                player.sendMessage(Minecraftplugin.PREFIX + "§4Du hast nicht genug Mazzotaler um dieses Item zu kaufen!");
            }


        }else if(Objects.equals(clicked_Inventory, this.special_Offers)) {
            player.sendMessage(Minecraftplugin.PREFIX + "Special Offers coming soon...");
            event.setCancelled(true);
        }else{
            System.out.println("Ich erkenne das Inventory nicht wieder.");
        }
    }
}
