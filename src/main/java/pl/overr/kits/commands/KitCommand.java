package pl.overr.kits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.overr.kits.inventories.KitInventory;
import pl.overr.kits.managers.KitManager;
import pl.overr.kits.managers.UserManager;

public class KitCommand implements CommandExecutor {

    private final KitManager kitManager;
    private final UserManager userManager;
    private final KitInventory kitInventory;
    public KitCommand(KitManager kitManager, UserManager userManager, KitInventory kitInventory) {
        this.kitManager = kitManager;
        this.userManager = userManager;
        this.kitInventory = kitInventory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
            Player player = (Player) sender;
            player.openInventory(kitInventory.createKitInventory(player));
            return true;
    }
}

