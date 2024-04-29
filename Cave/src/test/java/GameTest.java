import bo.Game;
import bo.cave.CaveMap;
import bo.player.Player;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    public void testGame() {
        CaveMap map = CaveMap.generateCaveMap(0);
        map.printMap();
        Player player = new Player(map);
        Game game = new Game(map, player);
        game.startGame();
        game.toString();
    }
}
