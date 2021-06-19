package plugin.minecraftplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.minecraftplugin.Listener.JoinQuitListener;
import plugin.minecraftplugin.Listener.inventory.Navigator;
import plugin.minecraftplugin.ShopSystem.ShopMenus.ShopInventory;
import plugin.minecraftplugin.ShopSystem.ShopVillagerUsage;
import plugin.minecraftplugin.commands.MoneySystem.AddMoney;
import plugin.minecraftplugin.commands.MoneySystem.Transfer;
import plugin.minecraftplugin.commands.HealCommand;
import plugin.minecraftplugin.commands.SpawnCommand;

/*
Minecraft Plugin by @Mathias
Functions:
    - Money System
    - Shop System
Plugin Status: in developement
Version: Alpha 1.0

Finish Date of Alpha 1.0 version: ----------
*/

public final class Minecraftplugin extends JavaPlugin {

    // General Message Prefix
    public static String PREFIX = "§aMazzoBot §7§o";

    // Making Minecraftplugin global available
    public static Minecraftplugin INSTANCE;
    public Minecraftplugin() {
        INSTANCE = this;
    }


    //----------Start / Stop Message----------//
    @Override
    public void onEnable() {
        this.register(); //Load added Commands
        log("Plugin enabled.");
    }
    @Override
    public void onDisable() {
        log("Plugin disabled.");
    }

    public void log(String text) {
        // Message Sender
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }
    //----------Start / Stop Message----------//


    //----------Register all Commands/Listener----------//
    public void register() {
        ShopVillagerUsage shopHandler = new ShopVillagerUsage();

        // Register Listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new Navigator(3), this);
        pluginManager.registerEvents(shopHandler, this);
        pluginManager.registerEvents(new ShopInventory(), this);

        //Register Commands
        Bukkit.getPluginCommand("heal").setExecutor(new HealCommand());
        Bukkit.getPluginCommand("spawn").setExecutor(new SpawnCommand());
        Bukkit.getPluginCommand("transfer").setExecutor(new Transfer());
        Bukkit.getPluginCommand("add").setExecutor(new AddMoney());
        Bukkit.getPluginCommand("spawnshop").setExecutor(shopHandler);
    }
}
