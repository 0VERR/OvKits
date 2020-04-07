package pl.overr.kits.managers;

import pl.overr.kits.kit.Kit;

import java.util.LinkedHashSet;
import java.util.Set;

public class KitManager {
    public Set<Kit> getKitSet() {
        return kitSet;
    }

    private final Set<Kit> kitSet;


    public KitManager(){
        this.kitSet = new LinkedHashSet<>();
    }

    public Kit getKit(String kitName){
        for (Kit kit : kitSet) {
            if (kit.getKitName().equalsIgnoreCase(kitName)) return kit;
        }
        return null;
    }

    public void addToKits(Kit kit){
        kitSet.add(kit);
    }
}
