import bo.cave.MapTile;
import bo.cave.enums.Direction;
import bo.cave.enums.TileLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertTrue(tile.isMatch(tileToConect, Direction.DOWN));
        Assertions.assertFalse(tile.isMatch(tileToConect, Direction.UP));
        Assertions.assertFalse(tile.isMatch(tileToConect, Direction.LEFT));
        Assertions.assertTrue(tile.isMatch(tileToConect, Direction.RIGHT));
    }

    @Test public void isPathTest(){
        MapTile tile = new MapTile("I W U D");
        MapTile tileToConect = new MapTile("I W U R");

        Assertions.assertTrue(tile.isPath(tileToConect, Direction.DOWN));
        Assertions.assertFalse(tile.isPath(tileToConect, Direction.UP));
        Assertions.assertFalse(tile.isPath(tileToConect, Direction.LEFT));
    }

    public void printingTile() {
        MapTile tile = new MapTile("I W U D");
        for (String string : tile.getLinesToPrint()){
            System.out.println(string);
        }

        MapTile tile2 = new MapTile("I C3 L D");
        for (String string : tile2.getLinesToPrint()){
            System.out.println(string);
        }

        MapTile tile3 = new MapTile("I R L D");
        for (String string : tile3.getLinesToPrint()){
            System.out.println(string);
        }
    }
}
