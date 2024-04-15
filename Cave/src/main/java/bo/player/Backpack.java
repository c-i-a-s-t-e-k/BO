package bo.player;


import bo.cave.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<ResourceType> resources = new ArrayList<>(8);
    private static int maxOxygenLevel;
    private static int maxPontoonLevel;
    private static int maxEnergyLevel;
    private static int maxLineLevel;
    private int oxygenLevel;
    private int pontoonLevel;
    private int energyLevel;
    private int lineLevel;

    public Backpack(){
        resetResources();
    }

    public void useResource(ResourceType resource,int amount){
        switch (resource){
            case LINE -> lineLevel -= amount;
            case ENERGY -> energyLevel -= amount;
            case OXYGEN -> oxygenLevel -= amount;
            case PONTOON -> pontoonLevel -= amount;
            case null, default -> throw new IllegalArgumentException("Invalid resource");
        }
        energyLevel -= 1;
    }

    public void resetResources(){
        oxygenLevel = maxOxygenLevel;
        lineLevel = maxLineLevel;
        pontoonLevel = maxPontoonLevel;
        energyLevel = maxEnergyLevel;
    }

    public boolean isEnough(TileType tile){
        return switch (tile.resourceNeed()){
            case NOTHING -> true;
            case LINE -> lineLevel > 0;
            case OXYGEN -> oxygenLevel > 0;
            case ENERGY -> energyLevel > 0;
            case PONTOON -> pontoonLevel > 0;
        };
    }


}
