package pl.overr.kits.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import pl.overr.kits.KitsPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hikari {

    private final FileConfiguration cfg = KitsPlugin.getKitsPlugin().getConfig();

    private final String mySQL = "MySQL.";
    private final String username = cfg.getString(mySQL + "username");
    private final String password = cfg.getString(mySQL + "password");
    private final String database = cfg.getString(mySQL + "database");
    private final String host = cfg.getString(mySQL + "host");
    private final int port = cfg.getInt(mySQL + "port");


    private final HikariDataSource hikariDataSource;

    public Hikari(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database );
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
        createTable();
    }


    public Connection getConnection(){
        try {
            return this.hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable(){
        try(Connection connection = this.getConnection()){
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ovkits ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "playerUUID VARCHAR(36) NOT NULL,"
            + "kits TEXT)");
            statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }




}
