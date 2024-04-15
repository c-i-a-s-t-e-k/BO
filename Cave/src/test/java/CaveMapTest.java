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

    @Test
//    mapy z tym samym *seed*'em powinny być takie same
    public void seedTest() {
        CaveMap map1 = CaveMap.generateCaveMap(0);
        CaveMap map2 = CaveMap.generateCaveMap(0);

        for (int i = 0; i < map1.getMapSize(); i++)
            for (int j = 0; j < map1.getMapSize(); j++) {
                Assertions.assertEquals(
                        map1.isPlaced(new Pair<>(i, j)),
                        map2.isPlaced(new Pair<>(i, j))
                );
            }
    }

    @Test
    public void seed2Test() {
        CaveMap map1 = CaveMap.generateCaveMap(0);
        CaveMap map2 = CaveMap.generateCaveMap(12345);

        boolean mapAreSame = true;
        for (int i = 0; i < map1.getMapSize(); i++) {
            for (int j = 0; j < map1.getMapSize(); j++) {
                mapAreSame = map1.isPlaced(new Pair<>(i, j)) && map2.isPlaced(new Pair<>(i, j));
                if (!mapAreSame) break;
            }
            if (!mapAreSame) break;
        }

        Assertions.assertFalse(mapAreSame);
    }

    public void printTest() {
        CaveMap map = CaveMap.generateCaveMap(0);
        map.printMap();
    }

}
