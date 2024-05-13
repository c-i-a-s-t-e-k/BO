package bo;

import bo.cave.CaveMap;
import bo.player.Player;

public class Main {
    public static void main(String[] args) {
        CaveMap map = CaveMap.generateCaveMap(12345678);
        Player player = new Player(map);
        Game game = new Game(map, player);
        game.startGame();
        game.getAcceptablePath().printPath(map);
    }
}
