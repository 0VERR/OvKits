package pl.overr.kits.kit;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.overr.kits.KitsPlugin;
import pl.overr.kits.managers.KitManager;
import pl.overr.kits.utils.FixColor;

import java.util.*;
import java.util.stream.Collectors;

public class KitLoader {

    private final FileConfiguration cfg = KitsPlugin.getKitsPlugin().getConfig();
    private final KitManager kitManager;

    public KitLoader(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    public void loadKits(){
        for (String kit : cfg.getConfigurationSection("Kits").getKeys(false)){
            String kitName = FixColor.fixer(cfg.getString("Kits." + kit + ".kitname"));
            long cooldown = cfg.getLong("Kits." + kit + ".cooldown");
            String itemInGui = cfg.getString("Kits." + kit + ".itemInGui");
            String itemInGuiName = cfg.getString("Kits." + kit + ".itemGuiName");
            List<String> loreInGuiPre = cfg.getStringList("Kits." + kit + ".loreInGui").stream()
                    .map(FixColor::fixer)
                    .collect(Collectors.toCollection(LinkedList::new));
            int slotInGui = cfg.getInt("Kits." + kit  + ".slotInGui");
             Set<ItemStack> items = new LinkedHashSet<>();
            for (String item : cfg.getConfigurationSection("Kits." + kit + ".items").getKeys(false)) {
                String material = cfg.getString("Kits." + kit + ".items." + item + ".material");
                String name = cfg.getString("Kits." + kit + ".items." + item + ".name");
                List<String> loreList = cfg.getStringList("Kits." + kit + ".items." + item + ".lore");
                int amount = cfg.getInt("Kits." + kit + ".items." + item + ".amount");
                List<String> enchantList = cfg.getStringList("Kits." + kit + ".items." + item + ".enchant");
                int durability = cfg.getInt("Kits." + kit + ".items." + item + ".durability");


                List<String> lore = loreList.stream()
                        .map(FixColor::fixer)
                        .collect(Collectors.toCollection(LinkedList::new));

                ItemStack itemStack = new ItemStack(Material.getMaterial(material), amount, (short) durability);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(FixColor.fixer(name));
                if (lore.size() > 0) itemMeta.setLore(lore);

                itemStack.setItemMeta(itemMeta);


                if (enchantList.size() > 0) {
                    for (String enchant : enchantList) {
                        String[] enchantArray = enchant.split(":");
                        String enchantment = enchantArray[0];
                        int enchantMultipler = Integer.parseInt(enchantArray[1]);
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultipler);
                    }
                }
                items.add(itemStack);

            }

            System.out.println("KitName:" + kitName);
            System.out.println("Cooldown:" + cooldown);
            for (ItemStack item : items) {
                System.out.println("Type " + item.getType());
                System.out.println("Name: " + item.getItemMeta().getDisplayName());
                System.out.println("Amount: " + item.getAmount());
            }

            Kit kit1 = new Kit(kitName,new ItemStack(Material.getMaterial(itemInGui)), kitName,loreInGuiPre, kit, slotInGui, itemInGuiName, items,cooldown);

            kitManager.addToKits(kit1);
        }



    }
}
