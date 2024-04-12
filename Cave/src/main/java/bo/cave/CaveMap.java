package bo.cave;

import bo.cave.enums.Direction;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CaveMap {
    private final int mapSize = 100;
    private final MapTile[][] tiles;
    private final Map<Pair<Integer, Integer>, Set<Direction>> edges;

    public void saveMap(String filename) {
        throw new RuntimeException("not implemented yet");
    }

    private CaveMap() {
        tiles = new MapTile[mapSize][mapSize];
        MapTile[] tmpBase = MapTile.getBase();

        for (int i = 0; i < tmpBase.length; i++) {
            tiles[mapSize / 2 - 1 + (i % 3)][mapSize / 2 - 1 + (i / 3)] = tmpBase[i];
        }
        edges = new HashMap<>();
        setMapEdges();
    }

    //    funkcja zwraca liste <x, y><lista>, gdzie x, y to wspórzędne do których można dołożyć kafeliki, a
//    lista zawiera *Directions* kierunki kafelka do których można dołożyć
    private void setMapEdges() {
        if (!isPlaced(getBasePosition())) return;
        Queue<Pair<Integer, Integer>> que = new LinkedList<>();
        que.add(getBasePosition());
        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        while (!que.isEmpty()) {
            Pair<Integer, Integer> currentPosition = que.poll();
            if (visited.contains(currentPosition)) continue;
            visited.add(currentPosition);
            que.addAll(setTileEdges(currentPosition));
        }
    }
    private List<Pair<Integer, Integer>> setTileEdges(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> possibleEdgePositionList = new ArrayList<>();
        edges.putIfAbsent(position, new HashSet<>());
        for (Direction direction : getTile(position).getExits()) {
            Pair<Integer, Integer> possibleEdgePosition = direction.getNextPosition(position);

            if (!isPlaced(possibleEdgePosition)) {
                edges.get(position).add(direction);
            } else possibleEdgePositionList.add(possibleEdgePosition);
        }
        if (edges.get(position).isEmpty()) edges.remove(position);

        return possibleEdgePositionList;
    }

    private static List<MapTile>[] loadTilesFromFile(String pathToTilesBase) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(pathToTilesBase));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found" + e);
        }
        ArrayList<MapTile>[] tileByLevelList = new ArrayList[]{
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

    public boolean isPlaced(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        return !(tiles[x][y] == null || tiles[x][y].getExits().isEmpty());
    }

    private MapTile getTile(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        return tiles[x][y];
    }

    public int getMapSize() {
        return mapSize;
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
        if (isPlaced(to))
            return false;

        boolean canBeConnected = false;
        for (Pair<Integer, Integer> pos : getSurrounded(to)) {
            for (int i = 0; i < 4; i++) {
                for (Direction direction : tile.getExits()) {
                    canBeConnected = tile.canBeConnected(getTile(pos), direction);
                    if (canBeConnected) break;
                }
                if (canBeConnected) break;
                tile.rotate();
            }
            if (canBeConnected) break;
        }
        if (canBeConnected) {
            tile.setDepression(getTile(from));
            tiles[to.getValue0()][to.getValue1()] = tile;
            setTileEdges(to);
            return true;
        } else return false;
    }

    public static CaveMap generateCaveMap(long seed) {
        return generateCaveMap(
                seed,
                9,
                Thread.currentThread().getContextClassLoader().getResource("tiles.txt").getFile());
    }

    public static CaveMap generateCaveMap(long seed, int removedTiles, String pathToTilesBase) {
        Random generator = new Random(seed);
        CaveMap caveMap = new CaveMap();

        List<MapTile>[] tileByLevelList = loadTilesFromFile(pathToTilesBase);

        for (List<MapTile> tileList : tileByLevelList) {
            Collections.shuffle(tileList, generator);
            for (MapTile tile : tileList.subList(removedTiles, tileList.size())) {
                boolean tileAdded = false;
                List<Pair<Integer, Integer>> possibleAddCollection = new ArrayList<>(caveMap.edges.keySet());
                Collections.shuffle(possibleAddCollection, generator);
                for (Pair<Integer, Integer> from : possibleAddCollection) {
                    for (Direction direction : caveMap.edges.get(from)) {
                        tileAdded = caveMap.tryToConnect(from, direction, tile);
                        if (tileAdded) break;
                    }
                    if (tileAdded) break;
                }
            }
        }

        return caveMap;
    }
}
