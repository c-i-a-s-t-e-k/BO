package bo;

import bo.bee.BeeTrainer;
import bo.cave.CaveMap;
import bo.neighbors.NextToDestination;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(Constants.SEED);
        map.printMap(map.getBasePosition());

        BeeTrainer beeTrainer = new BeeTrainer(map, new NextToDestination());
        beeTrainer.train();
    }
}
