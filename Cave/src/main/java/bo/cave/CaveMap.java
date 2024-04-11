package bo.cave;

import bo.cave.enums.Direction;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CaveMap {
    private static final String pathToTilesBase =
            Thread.currentThread().getContextClassLoader().getResource("tiles.txt").getFile();
    ;
    private final int mapSize = 100;
    private final MapTile[][] tiles;
    private final Map<Pair<Integer, Integer>, Set<Direction>> edges;

    public void saveMap(String filename) {
        throw new RuntimeException("not implemented yet");
    }

    ;

    private CaveMap() {
        tiles = new MapTile[mapSize][mapSize];
        MapTile[] tmpBase = MapTile.getBase();

        for (int i = 0; i < tmpBase.length; i++) {
            tiles[mapSize / 2 - 1 + (i % 3)][mapSize / 2 - 1 + (i / 3)] = tmpBase[i];
        }
        edges = getMapEdges();
    }

    //    funkcja zwraca liste <x, y><lista>, gdzie x, y to wspórzędne do których można dołożyć kafeliki, a
//    lista zawiera *Directions* kierunki kafelka do których można dołożyć
    private Map<Pair<Integer, Integer>, Set<Direction>> getMapEdges() {
        Map<Pair<Integer, Integer>, Set<Direction>> mapEdges = new HashMap<>();
        if (isNotPlaced(getBasePosition())) return mapEdges;
        Queue<Pair<Integer, Integer>> que = new LinkedList<>();
        que.add(getBasePosition());

        while (!que.isEmpty()) {
            Pair<Integer, Integer> currentPosition = que.poll();
            mapEdges.putIfAbsent(currentPosition, new HashSet<>());
            for (Direction direction : getTile(currentPosition).getExits()) {
                Pair<Integer, Integer> possibleEdgePosition = direction.getNextPosition(currentPosition);

                if (isNotPlaced(possibleEdgePosition)) {
                    mapEdges.get(currentPosition).add(direction);
                } else que.add(possibleEdgePosition);
            }
            if (mapEdges.get(currentPosition).isEmpty()) mapEdges.remove(currentPosition);
        }

        return mapEdges;
    }

    private static List<MapTile>[] loadTilesFromFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(pathToTilesBase));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found" + e);
        }
        List<MapTile>[] tileByLevelList = new List[]{
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        };

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.charAt(0) == '#') continue;
            MapTile tmpTile = new MapTile(line);
            tileByLevelList[tmpTile.getLevel().toInt() - 1].add(tmpTile);
        }
        return tileByLevelList;
    }

    public Pair<Integer, Integer> getBasePosition() {
        return new Pair<>(mapSize / 2, mapSize / 2);
    }

    private boolean isNotPlaced(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        return tiles[x][y] == null || tiles[x][y].getExits().isEmpty();
    }

    private MapTile getTile(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        return tiles[x][y];
    }

    private boolean isInBounds(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        return (0 <= x && x < mapSize) && (0 <= y && y < mapSize);
    }

    private Collection<Pair<Integer, Integer>> getSurrounded(Pair<Integer, Integer> position) {
        Collection<Pair<Integer, Integer>> result = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (isInBounds(direction.getNextPosition(position))) result.add(direction.getNextPosition(position));
        }
        return result;
    }

    private boolean tryToConnect(Pair<Integer, Integer> from, Direction at, MapTile tile) {
        Pair<Integer, Integer> to = at.getNextPosition(from);
        if (!isNotPlaced(to))
            throw new IllegalArgumentException("cannot try to place on already placed position " + to);

        for (Pair<Integer, Integer> pos : getSurrounded(to)) {
            for (int i = 0; i < 4; i++) {
                boolean canBeConnected = tile.canBeConnected();
                if (canBeConnected) break;
            }
            if (canBeConnected) break;
        }
        throw new RuntimeException("not implement yet");
    }

    public static CaveMap generateCaveMap(long seed) {
        return generateCaveMap(seed, 9);
    }

    public static CaveMap generateCaveMap(long seed, int removedTiles) {
        Random generator = new Random(seed);
        CaveMap caveMap = new CaveMap();

        List<MapTile>[] tileByLevelList = loadTilesFromFile();

        for (List<MapTile> tileList : tileByLevelList) {
            Collections.shuffle(tileList, generator);
            for (MapTile tile : tileList.subList(removedTiles, tileList.size())) {
                List<Pair<Integer, Integer>> possibleAddCollection = new ArrayList<>(caveMap.edges.keySet());
                Collections.shuffle(possibleAddCollection, generator);
                for (Pair<Integer, Integer> from : possibleAddCollection) {
                    for (Direction direction : caveMap.edges.get(from)) {
                        caveMap.tryToConnect(from, direction, tile);
                    }
                }
            }
        }

        return caveMap;
    }
}
