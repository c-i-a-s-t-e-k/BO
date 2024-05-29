package bo.player;


import bo.Constants;
import bo.cave.enums.TileType;

import java.util.*;

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

        for (int i = 0; i < Constants.FOOD_IN_BACKPACK; ++i) {
            resources.add(ResourceType.FOOD);
        }

        resources.add(ResourceType.PONTOON);
        resources.add(ResourceType.OXYGEN);
        resources.add(ResourceType.LINE);

        while (resources.size() > Constants.BACKPACK_CAPACITY) {
            resources.add(new Random().nextBoolean() ? ResourceType.OXYGEN : ResourceType.LINE);
        }

        if (!new HashSet<>(resources).containsAll(EnumSet.of(
                ResourceType.FOOD,
                ResourceType.PONTOON,
                ResourceType.OXYGEN,
                ResourceType.LINE))) {
            throw new RuntimeException("Invalid backpack: " + resources);
        }
    }

    public void useResource(ResourceType resource) {
        if (Objects.requireNonNull(resource) != ResourceType.NOTHING) {
            if (!resources.contains(resource)) {
                throw new RuntimeException("No resource found. Expected: " + resource);
            }
            resources.remove(resource);
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
}
