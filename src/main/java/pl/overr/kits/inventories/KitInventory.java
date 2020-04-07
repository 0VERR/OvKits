package pl.overr.kits.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.overr.kits.kit.Kit;
import pl.overr.kits.managers.KitManager;
import pl.overr.kits.managers.UserManager;
import pl.overr.kits.user.User;
import pl.overr.kits.utils.FixColor;
import pl.overr.kits.utils.FixData;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class KitInventory {


    private final UserManager userManager;
    private final KitManager kitManager;

    public KitInventory(UserManager userManager, KitManager kitManager) {
        this.userManager = userManager;
        this.kitManager = kitManager;
    }


    public Inventory createKitInventory(Player player){
        final Inventory kitInventory = Bukkit.createInventory(null,27, FixColor.fixer("&aKity"));

        User user = userManager.getUsers(player);

        for (Kit kit : kitManager.getKitSet()){
            ItemStack itemStack = kit.getItemInGui();
            boolean canTake = userManager.getTime(player,kit.getKitName()) < System.currentTimeMillis();

            List<String> loreFromConfig = kit.getLoreInGui();
            List<String> lore = new LinkedList<>(loreFromConfig);

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(FixColor.fixer(kit.getItemInGuiName()));
            String take;
            String when;
            if (canTake){
                take = FixColor.fixer("&eMozesz wziac&8: &aTAK");
                lore.add(take);
            } else {
                take = FixColor.fixer("&eMozesz wziac&8: &cNIE");
                when = FixColor.fixer("&eBedziesz mogl wziac&8: &a" + FixData.getData(user.getKitTime().get(kit.getKitName())));
                lore.add(take);
                lore.add(when);
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            kitInventory.setItem(kit.getSlotInGui(), itemStack);

        }
        return kitInventory;
    }
}
