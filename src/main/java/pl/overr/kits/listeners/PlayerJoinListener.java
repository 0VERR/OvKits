package pl.overr.kits.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.overr.kits.managers.UserManager;
import pl.overr.kits.user.User;

import java.util.HashMap;

public class PlayerJoinListener implements Listener {


    private final UserManager userManager;

    public PlayerJoinListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onJoinListener(PlayerJoinEvent event){
        userManager.addToUsers(event.getPlayer());
    }
}
