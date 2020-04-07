package pl.overr.kits;

import org.bukkit.plugin.java.JavaPlugin;
import pl.overr.kits.commands.KitCommand;
import pl.overr.kits.inventories.KitInventory;
import pl.overr.kits.kit.KitLoader;
import pl.overr.kits.listeners.InventoryClickListener;
import pl.overr.kits.listeners.PlayerJoinListener;
import pl.overr.kits.managers.KitManager;
import pl.overr.kits.managers.UserManager;
import pl.overr.kits.storage.Hikari;

public class KitsPlugin extends JavaPlugin {


    public static KitsPlugin getKitsPlugin() {
        return kitsPlugin;
    }

    private static KitsPlugin kitsPlugin;

    private KitLoader kitLoader;
    private UserManager userManager;
    private KitManager kitManager;
    private KitInventory kitInventory;
    private Hikari hikari;
    @Override
    public void onEnable() {
        kitsPlugin = this;
        saveDefaultConfig();
        this.hikari = new Hikari();
        this.kitManager = new KitManager();
        this.userManager = new UserManager(hikari);
        this.kitInventory = new KitInventory(userManager,kitManager);
        this.kitLoader = new KitLoader(kitManager);
        kitLoader.loadKits();
        userManager.loadUser();



        getCommand("kit").setExecutor(new KitCommand(kitManager, userManager, kitInventory));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(userManager), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(userManager,kitManager), this);
    }

    @Override
    public void onDisable(){
        userManager.getUserHashMap().values().forEach(user ->{
            userManager.updateUser(user);
        });

    }
}
