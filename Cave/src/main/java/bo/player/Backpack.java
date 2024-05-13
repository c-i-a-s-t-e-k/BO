package bo.player;


import bo.cave.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class Backpack implements Cloneable{
    private List<ResourceType> resources = new ArrayList<>(8);

    public Backpack() {
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
    }

    public int foodInBackpack(){
        int food = 0;
        for(ResourceType resource : resources){
            if(resource == ResourceType.FOOD){food++;}
        }
        return food;
    }

    public void fillDefault(){
        resources.clear();
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
    }

    public void useResource(ResourceType resource) {
        switch (resource) {
            case NOTHING -> {
                return;
            }
            default -> {
                if(!resources.contains(resource)) throw new RuntimeException("No resource found. Expected: " + resource);
                resources.remove(resource);
            }
        }
    }

    public void addResource(ResourceType resource) {
        if (resources.size() < 8) resources.add(resource);
    }

    public boolean isEnough(TileType tile) {
        if (tile.resourceNeed() == ResourceType.NOTHING) return true;
        return  resources.contains(tile.resourceNeed());
    }

    @Override
    public Backpack clone() {
        try {
            Backpack cloned = (Backpack) super.clone();

            cloned.resources = new ArrayList<>(this.resources);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
