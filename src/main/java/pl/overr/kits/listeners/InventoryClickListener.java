package pl.overr.kits.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.overr.kits.kit.Kit;
import pl.overr.kits.managers.KitManager;
import pl.overr.kits.managers.UserManager;
import pl.overr.kits.user.User;
import pl.overr.kits.utils.FixColor;
import pl.overr.kits.utils.InventoryUtil;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class InventoryClickListener implements Listener {

    private final UserManager userManager;
    private final KitManager kitManager;

    public InventoryClickListener(UserManager userManager, KitManager kitManager) {
        this.userManager = userManager;
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();

        if (inventory.getName().equalsIgnoreCase(FixColor.fixer("&aKity")) || inventory.getName().equalsIgnoreCase(FixColor.fixer("&EPodglad Kita"))) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            ItemStack clickedItem = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (inventory.getName().equalsIgnoreCase(FixColor.fixer("&aKity"))) {
                for (Kit kit : kitManager.getKitSet()) {
                    if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(FixColor.fixer(kit.getItemInGuiName()))) {
                        Inventory previewKit = Bukkit.createInventory(null, 54, FixColor.fixer("&EPodglad Kita"));
                        for (ItemStack item : kit.getItems()) {
                            previewKit.addItem(item);
                        }

                        previewKit.setItem(53, InventoryUtil.createBackItem(kit.getKitName()));
                        player.openInventory(previewKit);
                    }
                }
            } else {

                if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(InventoryUtil.backItem.getItemMeta().getDisplayName())) {
                    String kitName = clickedItem.getItemMeta().getDisplayName().split(": ")[1];
                    Kit kit = kitManager.getKit(kitName);
                    if (player.hasPermission("OvKit." + kit.getKitName())) {
                        if (userManager.getTime(player, kitName) < System.currentTimeMillis()) {
                            kit.getItems().forEach(item -> {
                                player.getInventory().addItem(item);
                            });
                            User user = userManager.getUsers(player);
                            user.getKitTime().put(kitName, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(kit.getCooldown()));
                        } else player.sendMessage(FixColor.fixer("&cMusisz poczekac!"));
                    } else player.sendMessage(FixColor.fixer("&cNie masz permisji"));
                }

            }
        }
    }
}
