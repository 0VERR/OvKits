package pl.overr.kits.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class Kit {
    private final String kitName;
    private final ItemStack itemInGui;
    private final String nameInGui;
    private final List<String> loreInGui;

    public int getSlotInGui() {
        return slotInGui;
    }

    private final int slotInGui;
    public String getItemInGuiName() {
        return itemInGuiName;
    }

    private final String itemInGuiName;
    public String getKitName() {
        return kitName;
    }

    public ItemStack getItemInGui() {
        return itemInGui;
    }

    public String getNameInGui() {
        return nameInGui;
    }

    public List<String> getLoreInGui() {
        return loreInGui;
    }

    public Set<ItemStack> getItems() {
        return items;
    }

    public long getCooldown() {
        return cooldown;
    }

    private final Set<ItemStack> items;
    private final long cooldown;

    public Kit(String kitName, ItemStack itemInGui, String nameInGui, List<String> loreInGui, int slotInGui, String itemInGuiName, Set<ItemStack> items, long cooldown) {
        this.kitName = kitName;
        this.itemInGui = itemInGui;
        this.nameInGui = nameInGui;
        this.loreInGui = loreInGui;
        this.slotInGui = slotInGui;
        this.itemInGuiName = itemInGuiName;
        this.items = items;
        this.cooldown = cooldown;
    }
}
