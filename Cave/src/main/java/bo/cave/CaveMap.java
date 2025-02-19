package bo.cave;

import bo.Constants;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CaveMap {
    private final int mapSize = Constants.MAP_SIZE;
    private final MapTile[][] tiles;
    private final Set<Pair<Integer, Integer>> edges;

    public void saveMap(String filename) {
        throw new RuntimeException("not implemented yet");
    }

    private CaveMap() {
        tiles = new MapTile[mapSize][mapSize];
        MapTile[] tmpBase = MapTile.getBase();

        for (int i = 0; i < tmpBase.length; i++) {
            tiles[mapSize / 2 - 1 + (i % 3)][mapSize / 2 - 1 + (i / 3)] = tmpBase[i];
        }
        edges = new HashSet<>();
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
        for (Direction direction : getTile(position).getExits()) {
            Pair<Integer, Integer> possibleEdgePosition = direction.getNextPosition(position);

            if (!isPlaced(possibleEdgePosition)) {
                edges.add(possibleEdgePosition);
            } else possibleEdgePositionList.add(possibleEdgePosition);
        }

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

    private boolean tryToConnect(Pair<Integer, Integer> place, MapTile tile) {
        if (isPlaced(place))
            return false;

        boolean canBeConnected = false;
        for (int i = 0; i < 4; i++) {
            for (Direction direction : Direction.values()) {
                canBeConnected = tile.isMatch(getTile(direction.getNextPosition(place)), direction);
                if (!canBeConnected) break;
            }
            if (canBeConnected) break;
            tile.rotate();
        }
        if (canBeConnected) {
            for (Direction direction : Direction.values()) {
                MapTile possibleOrigin = getTile(direction.getNextPosition(place));
                canBeConnected = tile.isPath(possibleOrigin, direction);
                if (canBeConnected) {
                    tile.setDepression(possibleOrigin);
                    break;
                }
            }
            tiles[place.getValue0()][place.getValue1()] = tile;
            edges.remove(place);
            setTileEdges(place);
            return true;
        } else return false;
    }

    public static CaveMap generateCaveMap(long seed) {
        return generateCaveMap(
                seed,
                Constants.REMOVE_TILES,
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("tiles.txt")).getFile());
    }

    public static CaveMap generateCaveMap(long seed, int removedTiles, String pathToTilesBase) {
        Random generator = new Random(seed);
        CaveMap caveMap = new CaveMap();

        List<MapTile>[] tileByLevelList = loadTilesFromFile(pathToTilesBase);

        for (List<MapTile> tileList : tileByLevelList) {
            Collections.shuffle(tileList, generator);
            for (MapTile tile : tileList.subList(removedTiles, tileList.size())) {
                boolean tileAdded;
                List<Pair<Integer, Integer>> possibleAddCollection = new ArrayList<>(caveMap.edges);
                Collections.shuffle(possibleAddCollection, generator);
                for (Pair<Integer, Integer> possiblePlace : possibleAddCollection) {
                    tileAdded = caveMap.tryToConnect(possiblePlace, tile);
                    if (tileAdded) break;
                }
            }
        }

        return caveMap;
    }

    public Set<Pair<Direction, TileType>> getPossibleMoves(Pair<Integer, Integer> position) {
        Set<Pair<Direction, TileType>> moves = new HashSet<>();
        for (Direction direction : getTile(position).getExits()) {
            Pair<Integer, Integer> possibleEdgePosition = direction.getNextPosition(position);
            if (isPlaced(possibleEdgePosition)) {
                moves.add(new Pair<>(direction, getTile(possibleEdgePosition).getType()));
            }
        }
        return moves;
    }

    public void unAchieveAllPositions() {
       for(int i = 0; i < mapSize; i++){
           for(int j = 0; j < mapSize; j++){
               Pair<Integer, Integer> position = new Pair<>(i, i);
               if(isPlaced(position)){
                   MapTile tile = getTile(position);
                   if (tile == null) throw new RuntimeException();
                   tile.unAchieve();
               }
           }
       }
    }

    public void printMap() {
        printMap(null);
    }

    public void printMap(Pair<Integer, Integer> pos) {
        int border = 1;

        int start_x = Math.max(Collections.min(edges).getValue0() - border, 0);
        int end_x = Math.min(Collections.max(edges).getValue0() + border, mapSize);

        int start_y = Math.max(Collections.min(edges.stream().map(Pair::getValue1).toList()) - border, 0);
        int end_y = Math.min(Collections.max(edges.stream().map(Pair::getValue1).toList()) + border, mapSize);

        List<String> lines = new ArrayList<>();
        int linesLength = 0;
        for (int i = start_y; i < end_y; i++) {
            Collections.addAll(lines, "", "", "");
            linesLength += 3;
            for (int j = start_x; j < end_x; j++) {
                if (getTile(new Pair<>(j, i)) != null) {
                    String color = "";
                    String end_color = "";
                    if (pos != null && j == pos.getValue0() && i == pos.getValue1()) {
                        color = "\033[48;5;210m";
                        end_color = "\033[0m";
                    }
                    String[] tileString = getTile(new Pair<>(j, i)).getLinesToPrint();
                    for (int k = linesLength - 3; k < linesLength; k++) {
                        lines.set(k, lines.get(k) + color + tileString[k - linesLength + 3] + end_color);
                    }
                } else for (int k = linesLength - 3; k < linesLength; k++) {
                    lines.set(k, lines.get(k) + "     ");
                }
            }
        }
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public int score(Pair<Integer, Integer> position) {
        return (!getTile(position).isConqured()) ? getTile(position).getType().getPoints() : 0;
    }
}
