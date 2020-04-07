package pl.overr.kits.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtil {

    public static ItemStack backItem = new ItemStack(Material.BARRIER);

    public static ItemStack  createBackItem(String string){
        ItemMeta itemMeta = backItem.getItemMeta();
        itemMeta.setDisplayName(FixColor.fixer("&AOdbierz kita&8: " + string));
        backItem.setItemMeta(itemMeta);
        return backItem;
    }
}
