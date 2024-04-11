package bo.cave.enums;

import org.javatuples.Pair;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction fromString(String direction) {
        return switch (direction) {
            case "U" -> UP;
            case "D" -> DOWN;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }

    public Direction next() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }

    public Direction opposite(){
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> throw new IllegalArgumentException("Invalid direction: " + this);
        };
    }

    public Pair<Integer, Integer> getNextPosition(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();

        return switch (this) {
            case UP -> new Pair<>(x - 1, y);
            case DOWN -> new Pair<>(x + 1, y);
            case LEFT -> new Pair<>(x, y - 1);
            case RIGHT -> new Pair<>(x, y + 1);
            default -> throw new IllegalArgumentException("Invalid direction: " + this);
        };
    }
}
