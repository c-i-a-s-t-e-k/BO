import bo.cave.CaveMap;
import bo.cave.MapTile;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Player;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PlayerTest {
    @Test
    public void testPlayer() {
        CaveMap map = CaveMap.generateCaveMap(0);
        Player player = new Player(map);
    }

    @Test
    public void testGetMoves(){
        CaveMap map = CaveMap.generateCaveMap(0);
        Player player = new Player(map);
        Assertions.assertTrue(player.getMoves().contains(new Pair<>( Direction.UP, TileType.NOTHING)));
        Assertions.assertTrue(player.getMoves().contains(new Pair<>( Direction.DOWN, TileType.NOTHING)));
    }

    @Test
    public void testMakeMove(){
        CaveMap map = CaveMap.generateCaveMap(0);
//        map.printMap();
        Player player = new Player(map);
        player.makeMove(new Pair<>( Direction.UP, TileType.NOTHING));
        player.makeMove(new Pair<>( Direction.RIGHT, TileType.NOTHING));
        player.makeMove(new Pair<>( Direction.RIGHT, TileType.MIRACLE));
        player.makeMove(new Pair<>( Direction.UP, TileType.WATER));

        Set<Pair<Direction, TileType>> moves = player.getMoves();
        Assertions.assertTrue(moves.contains(new Pair<>(Direction.UP, TileType.WATER)));
        Assertions.assertTrue(moves.contains(new Pair<>(Direction.DOWN, TileType.MIRACLE)));
        Assertions.assertTrue(moves.contains(new Pair<>(Direction.LEFT, TileType.WATER)));
        Assertions.assertTrue(moves.contains(new Pair<>(Direction.RIGHT, TileType.CONSTRICTION_I)));
    }
}
