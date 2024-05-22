package bo.player;


import bo.Constants;
import bo.cave.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Backpack implements Cloneable {
    private List<ResourceType> resources = new ArrayList<>(Constants.BACKPACK_CAPACITY);

    public Backpack() {
        fillDefault();
    }

    public int foodInBackpack() {
        int food = 0;
        for (ResourceType resource : resources) {
            if (resource == ResourceType.FOOD) {
                food++;
            }
        }
        return food;
    }

    public void fillDefault() {
        resources.clear();
        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.LINE);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.FOOD);
        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.OXYGEN);
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
                if (!resources.contains(resource)) {
                    throw new RuntimeException("No resource found. Expected: " + resource);
                }
                resources.remove(resource);
            }
        }
    }

    public boolean isEnough(TileType tile) {
        return tile.resourceNeed() == ResourceType.NOTHING || resources.contains(tile.resourceNeed());
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

    void showResources() {
        System.out.println("Backpack: " + resources.stream().toList());
    }
}
