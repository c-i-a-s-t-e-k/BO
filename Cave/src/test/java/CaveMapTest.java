import bo.cave.CaveMap;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CaveMapTest {

    @Test
    public void placedTilesNumTest1() {
        CaveMap map = CaveMap.generateCaveMap(0);
        int placedTilesNum = 0;
        for (int i = 0; i < map.getMapSize(); i++)
            for (int j = 0; j < map.getMapSize(); j++) {
                if (map.isPlaced(new Pair<Integer, Integer>(i, j))) {
                    placedTilesNum++;
                }
            }
        Assertions.assertEquals(4 * 11 + 7, placedTilesNum);
    }

    @Test
    public void placedTilesNumTest2() {
        CaveMap map = CaveMap.generateCaveMap(
                0,
                0,
                Thread.currentThread().getContextClassLoader().getResource("tiles5perlvl.txt").getFile()
        );
        int tilePerLvl = 5;
        int baseTileNum = 7;
        int placedTilesNum = 0;
        for (int i = 0; i < map.getMapSize(); i++)
            for (int j = 0; j < map.getMapSize(); j++) {
                if (map.isPlaced(new Pair<Integer, Integer>(i, j))) {
                    placedTilesNum++;
                }
            }

        Assertions.assertEquals(4 * tilePerLvl + baseTileNum, placedTilesNum);
    }

}
