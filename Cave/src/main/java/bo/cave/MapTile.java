package bo.cave;

import bo.cave.enums.Direction;
import bo.cave.enums.TileLevel;
import bo.cave.enums.TileStatus;
import bo.cave.enums.TileType;
import org.javatuples.Pair;

import java.util.*;

public class MapTile {
    private TileType type;
    private int depression; // poziom zgłębienia ze zbioru {0, 25, 50, 75, 100 ...}
    private TileLevel level;
    private TileStatus status;
    private final Set<Direction> exits = new HashSet<>();

    private final int pointsForDiscover = 2;

    private MapTile() {
        type = TileType.NOTHING;
        depression = 0;
        level = TileLevel.Base;
        status = TileStatus.CONQUERED;
    }

    public MapTile(String input) {
        String[] split = input.split(" ");

        level = TileLevel.fromString(split[0]);
        type = TileType.fromString(split[1]);
        for (int i = 2; i < split.length; i++) {
            exits.add(Direction.fromString(split[i]));
        }
        this.status = TileStatus.NOT_DISCOVERED;
    }

    boolean isConqured() {
        return status == TileStatus.CONQUERED;
    }

    void conquer() {
        if (status == TileStatus.CONQUERED) throw new RuntimeException("cannot conquer this tile");
        status = TileStatus.CONQUERED;
    }

    public TileLevel getLevel() {
        return level;
    }

    public TileType getType() {
        return type;
    }

    public List<Direction> getExits() {
        return List.copyOf(exits);
    }

    public void rotate() {
        Collection<Direction> directions = getExits();
        exits.clear();
        exits.addAll(directions.stream().map(Direction::next).toList());
    }

    public boolean isMatch(MapTile other, Direction by) {
        if (other == null || other.type == TileType.ROCK) {
            return true;
        } else if (!exits.contains(by)) {
            return !other.exits.contains(by.opposite());
        } else return other.exits.contains(by.opposite());
    }

    public boolean isPath(MapTile other, Direction by) {
        if (other == null || other.type == TileType.ROCK || !exits.contains(by)) {
            return false;
        } else return other.exits.contains(by.opposite());
    }

    public void setDepression(MapTile prevTile) {
        if (this.type == TileType.DESCENSION) {
            this.depression = prevTile.depression + 25;
        } else this.depression = prevTile.depression;

        if (depression % 25 != 0) throw new IllegalArgumentException("Invalid depression");
    }

    //    zwraca tablice 9 elementową reprezentująca baze
//    baza składa się z kafelków Normalnych oraz skał (tylko na środkowym można oddnwiać zasoby)
//    baza wygląda tak:
//   .   _____   .
//     N   N   N
//   ____    _____
//   | R | N | R |
//   ____    _____
//     N   N   N
//   .   _____   .
// ]
    public static MapTile[] getBase() {
        MapTile[] base = {
                new MapTile(), new MapTile(), new MapTile(),
                new MapTile(), new MapTile(), new MapTile(),
                new MapTile(), new MapTile(), new MapTile()
        };
        base[3].type = TileType.ROCK;
        base[5].type = TileType.ROCK;

        base[0].exits.add(Direction.UP);
        base[0].exits.add(Direction.LEFT);
        base[0].exits.add(Direction.RIGHT);

        base[1].exits.add(Direction.DOWN);
        base[1].exits.add(Direction.LEFT);
        base[1].exits.add(Direction.RIGHT);

        base[2].exits.add(Direction.UP);
        base[2].exits.add(Direction.LEFT);
        base[2].exits.add(Direction.RIGHT);

        base[4].exits.add(Direction.UP);
        base[4].exits.add(Direction.DOWN);

        base[6].exits.add(Direction.DOWN);
        base[6].exits.add(Direction.LEFT);
        base[6].exits.add(Direction.RIGHT);

        base[7].exits.add(Direction.UP);
        base[7].exits.add(Direction.LEFT);
        base[7].exits.add(Direction.RIGHT);

        base[8].exits.add(Direction.DOWN);
        base[8].exits.add(Direction.LEFT);
        base[8].exits.add(Direction.RIGHT);
        return base;
    }

    public int achieve() {
        if (status == TileStatus.NOT_DISCOVERED) {
            status = TileStatus.DISCOVERED;
            return pointsForDiscover;
        }
        return 0;
    }

    public void unAchieve(){
        status = TileStatus.NOT_DISCOVERED;
    }

    public String[] getLinesToPrint() {
        String[] lines = new String[3];
        if (this.exits.contains(Direction.UP))
            lines[0] = "┌   ┐";
        else lines[0] = "┌───┐";

        if (this.exits.contains(Direction.DOWN))
            lines[2] = "└   ┘";
        else lines[2] = "└───┘";

        String leftChar = "  ";
        String rightChar = "  ";
        if (!this.exits.contains(Direction.LEFT)) {
            leftChar = "│ ";
        }
        if (!this.exits.contains(Direction.RIGHT)) {
            rightChar = " │";
        }
        lines[1] = leftChar + this.type + rightChar;

//        if(this.level == TileLevel.Base){
//            for (int i = 0; i < 3; i++){
//                lines[i] = "\u001b[48;5;22m" + lines[i] + "\u001b[0m";
//            }
//        }

        return lines;
    }


}
