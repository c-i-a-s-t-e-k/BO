import bo.cave.MapTile;
import bo.cave.enums.Direction;
import bo.cave.enums.TileLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapTileTest {
    @Test
    public void constructorMapTileTest() {
        MapTile mapTile = new MapTile("I W U D");

        Assertions.assertEquals(TileLevel.I, mapTile.getLevel());
        Assertions.assertTrue(
                mapTile.getExits().contains(Direction.DOWN) && mapTile.getExits().contains(Direction.UP)
        );
    }
    @Test
    public void constructorMapTileTest2() {
        MapTile mapTile = new MapTile("I W U R");

        Assertions.assertEquals(TileLevel.I, mapTile.getLevel());
        Assertions.assertTrue(
                mapTile.getExits().contains(Direction.RIGHT) && mapTile.getExits().contains(Direction.UP)
        );
    }

    @Test
    public void rotateTileTest() {
        MapTile mapTile = new MapTile("I W U D");
        mapTile.rotate();
        Assertions.assertFalse(
                mapTile.getExits().contains(Direction.DOWN) && mapTile.getExits().contains(Direction.UP)
        );
        Assertions.assertTrue(
                mapTile.getExits().contains(Direction.LEFT) && mapTile.getExits().contains(Direction.RIGHT)
        );

        MapTile mapTile2 = new MapTile("I W U R");
        mapTile2.rotate();
        Assertions.assertTrue(mapTile2.getExits().contains(Direction.RIGHT));
        Assertions.assertTrue(mapTile2.getExits().contains(Direction.DOWN));
        Assertions.assertFalse(mapTile2.getExits().contains(Direction.UP));
    }

    @Test public void getBaseTest(){
        MapTile[] mapTile = MapTile.getBase();
        int tilesWithExits = 0;
        for (int i = 0; i < mapTile.length; i++){
            if(!mapTile[i].getExits().isEmpty()){tilesWithExits++;}
        }
        Assertions.assertEquals(7, tilesWithExits);
    }

    @Test public void canBeConectedTest(){
        MapTile tile = new MapTile("I W U D");
        MapTile tileToConect = new MapTile("I W U R");

        Assertions.assertTrue(tile.canBeConnected(tileToConect, Direction.DOWN));
        Assertions.assertFalse(tile.canBeConnected(tileToConect, Direction.UP));
        Assertions.assertFalse(tile.canBeConnected(tileToConect, Direction.LEFT));
    }
}
