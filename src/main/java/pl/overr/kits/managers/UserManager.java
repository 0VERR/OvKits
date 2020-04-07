package pl.overr.kits.managers;

import org.bukkit.entity.Player;
import pl.overr.kits.kit.Kit;
import pl.overr.kits.storage.Hikari;
import pl.overr.kits.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UserManager {
    private final Hikari hikari;
    public UserManager(Hikari hikari){
        this.hikari = hikari;
        this.userHashMap = new HashMap<>();
    }

    public HashMap<UUID, User> getUserHashMap() {
        return userHashMap;
    }

    private final HashMap<UUID, User> userHashMap;


    public User getUsers(Player player){
        return userHashMap.get(player.getUniqueId());
    }

    public void addToUsers(Player player){
        if (!userHashMap.containsKey(player.getUniqueId())) {
            User user = new User(new HashMap<>(), player.getUniqueId());
            userHashMap.put(player.getUniqueId(), user);
            insertUser(user);

        }
    }

    public long getTime(Player player,String kitName){
        return userHashMap.get(player.getUniqueId()).getKitTime().getOrDefault(kitName,(long) 0);
    }

    public void addKitToPlayer(Player player, Kit kit){
        userHashMap.get(player.getUniqueId()).getKitTime().put(kit.getKitName(), System.currentTimeMillis() + kit.getCooldown());
    }

    public void insertUser(User user){
        try(Connection connection = this.hikari.getConnection()){
            String UUID = user.getPlayerUUID().toString();
            HashMap<String,Long> hashMap = user.getKitTime();

            Set<Map.Entry<String,Long>> entrySet = hashMap.entrySet();

            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Long> entry : entrySet) {
                stringBuilder.append(entry.getKey()).append(":").append(entry.getValue());
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO ovkits VALUES (?, ?, ?)");
            statement.setInt(1, 0);
            statement.setString(2, UUID );
            statement.setString(3, stringBuilder.toString());
            statement.executeUpdate();
        } catch (SQLException ex){

        }
    }

    public void updateUser(User user){
        try (Connection connection = this.hikari.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE ovkits SET kits = ? WHERE playerUUID = '" + user.getPlayerUUID() + "';" );

            StringBuilder stringBuilder = new StringBuilder();

            Set<Map.Entry<String,Long>> entrySet = user.getKitTime().entrySet();

            for (Map.Entry<String, Long> entry : entrySet) {

                stringBuilder.append(entry.getKey() + ":" + entry.getValue()).append(",");
            }

            statement.setString(1, stringBuilder.toString());
            statement.executeUpdate();
        } catch (SQLException ex){
        }
    }

    public void loadUser(){
        try(Connection connection = this.hikari.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM  ovkits");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String playerUUID = resultSet.getString("playerUUID");
                String kits = resultSet.getString("kits");

                String[] loadKit = kits.split(",");
                HashMap<String,Long> stringLongHashMap = new HashMap<>();

                    for (String string : loadKit) {
                        String[] kit = string.split(":");
                        if (kit.length > 1) {
                            String kitName = kit[0];
                            long time = Long.parseLong(kit[1]);
                            stringLongHashMap.put(kitName, time);
                        }
                    }

                User user = new User(stringLongHashMap,UUID.fromString(playerUUID));
                userHashMap.put(user.getPlayerUUID(),user);
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
