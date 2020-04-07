package pl.overr.kits.user;

import java.util.HashMap;
import java.util.UUID;

public class User {
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public HashMap<String, Long> getKitTime() {
        return kitTime;
    }

    private final HashMap<String, Long> kitTime;
    private final UUID playerUUID;

    public User(HashMap<String, Long> kitTime, UUID playerUUID) {
        this.kitTime = kitTime;
        this.playerUUID = playerUUID;
}
}
