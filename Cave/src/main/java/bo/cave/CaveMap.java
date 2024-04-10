package bo.cave;

import java.io.File;
import java.util.Map;

public class CaveMap {
    private final int mapSize = 100;
    private final MapTile[][] tiles;

    public void saveMap(String filename) {
        throw new RuntimeException("not implemented yet");
    };

    private CaveMap(){
        tiles = new MapTile[mapSize][mapSize];
        MapTile[] tmpBase = MapTile.getBase();

        for (int i = 0; i < tmpBase.length; i++) {
            tiles[ mapSize/2 - 1 + (i % 3) ][ mapSize/2 - 1 + (i / 3) ] = tmpBase[i];
        }
    }

    public static CaveMap generateCaveMap(String filename){
        CaveMap caveMap = new CaveMap();

        return caveMap;
    }
}
