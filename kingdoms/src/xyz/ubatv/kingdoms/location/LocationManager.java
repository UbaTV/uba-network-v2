package xyz.ubatv.kingdoms.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.ubatv.kingdoms.Main;

import java.util.Objects;

public class LocationManager {

    private Main main = Main.getInstance();

    public void setLocation(String locationName, Location location){
        FileConfiguration config = main.locationYML.getConfig();
        config.set(locationName + ".world", Objects.requireNonNull(location.getWorld()).getName());
        config.set(locationName + ".x", location.getBlockX());
        config.set(locationName + ".y", location.getBlockY());
        config.set(locationName + ".z", location.getBlockZ());
        config.set(locationName + ".yaw", location.getYaw());
        config.set(locationName + ".pitch", location.getPitch());
        main.locationYML.unload();
    }

    public Location getLocation(String locationName){
        FileConfiguration config = main.locationYML.getConfig();
        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString(locationName + ".world")));
        double x = config.getDouble(locationName + ".x"),
                y = config.getDouble(locationName + ".y"),
                z = config.getDouble(locationName + ".z");
        float yaw = (float) config.getDouble(locationName + ".yaw"),
                pitch = (float) config.getDouble(locationName + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
