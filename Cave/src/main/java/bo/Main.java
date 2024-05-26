package bo;

import bo.cave.CaveMap;
import bo.neighbors.Neighbor;
import bo.neighbors.NextToDestination;
import bo.player.Player;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(12345678);

        Player player = new Player(map);
        Game game = new Game(map, player);
        int points = game.startGame();
        System.out.println("Original score is: " + points);

        System.out.println("*********************************************************************");

        Neighbor neighbor = new NextToDestination();
        try {
            Game neighbourGame = neighbor.getNeighbor(game);
            int neighPoints = neighbourGame.startGame();
            System.out.println("Neighbour score is: " + neighPoints);
        } catch (RuntimeException e){
            System.out.println("Not enough resources: "+ e);
        }
    }
}
