package bo;

import bo.bee.BeeTrainer;
import bo.cave.CaveMap;
import bo.neighbors.Neighbour;
import bo.neighbors.CombinedNeighbour;
import bo.neighbors.ShuffledPathNeighbour;
import bo.neighbors.NextToDestination;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(Constants.SEED);
        map.printMap(map.getBasePosition());

        Neighbour neighbourStrategy = new CombinedNeighbour();
        BeeTrainer beeTrainer = new BeeTrainer(map, neighbourStrategy);
        beeTrainer.train();
    }
}
