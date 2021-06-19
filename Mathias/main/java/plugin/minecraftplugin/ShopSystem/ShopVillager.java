package plugin.minecraftplugin.ShopSystem;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
By Mathias
finished | tested
14.02.2021
*/

public class ShopVillager {

    public static final String VILLAGER_NAME = "§e§lServer Shop";

    public ShopVillager(Location setLocation) {
        //-----Spawn Shop Villager-----//
        Villager shopVillager = (Villager) setLocation.getWorld().spawnEntity(setLocation, EntityType.VILLAGER);
        shopVillager.setCustomName(ShopVillager.VILLAGER_NAME);
        shopVillager.setCustomNameVisible(true);
        //-----Spawn Shop Villager-----//

        //------Disable Movement-----//
        shopVillager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 500));
        //------Disable Movement-----//
    }

}
