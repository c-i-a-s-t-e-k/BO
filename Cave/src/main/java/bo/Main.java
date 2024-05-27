package bo;

import bo.bee.BeeTrainer;
import bo.cave.CaveMap;
import bo.bee.*;
import bo.neighbors.NextToDestination;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(Constants.SEED);
        map.printMap();

        BeeTrainer beeTrainer = new BeeTrainer(map, new NextToDestination());
        beeTrainer.train();
    }
}
