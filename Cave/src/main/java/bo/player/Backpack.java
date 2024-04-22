package bo.player;


import bo.cave.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<ResourceType> resources = new ArrayList<>(8);

    public Backpack(){
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.ENERGY);
        resources.add(ResourceType.ENERGY);
    }

    public void useResource(ResourceType resource,int amount){
        switch (resource) {
            case NOTHING -> {
                return;
            }
            default -> resources.remove(resource);
        }
    }

    public void addResource(ResourceType resource){
        if(resources.size() < 8)resources.add(resource);
    }

    public boolean isEnough(TileType tile){
        return switch (tile.resourceNeed()){
            case NOTHING -> true;
            case ResourceType resource -> resources.contains(resource);
        };
    }


}
