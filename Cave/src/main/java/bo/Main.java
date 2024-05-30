package bo;

import bo.bee.BeeTrainer;
import bo.cave.CaveMap;
import bo.neighbors.ShuffledPathNeighbour;
import bo.neighbors.Neighbour;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(Constants.SEED);
        map.printMap(map.getBasePosition());

        Neighbour neighbourStrategy = new ShuffledPathNeighbour();
        BeeTrainer beeTrainer = new BeeTrainer(map, neighbourStrategy);
        beeTrainer.train();
    }
}
