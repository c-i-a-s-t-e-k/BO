package bo;

import bo.cave.CaveMap;
import bo.neighbors.Neighbor;
import bo.neighbors.NextToDestination;
import bo.player.Player;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(12345678);
        Player player = new Player(map);
        Game game = new Game(map, player);
        int points = game.startGame();
        game.getAcceptablePath().printPath(map);
        System.out.println(points);
        Neighbor neighbor = new NextToDestination();
        System.out.println("*********************************************************************");
        neighbor.getNeighbor(game).getAcceptablePath().printPath(map);;

    }
}
