package bo.cave.enums;

import org.javatuples.Pair;

import java.util.Objects;

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
            case UP -> new Pair<>(x, y - 1);
            case DOWN -> new Pair<>(x, y + 1);
            case LEFT -> new Pair<>(x - 1, y);
            case RIGHT -> new Pair<>(x + 1, y);
            default -> throw new IllegalArgumentException("Invalid direction: " + this);
        };
    }

    public static Direction secondIsOn(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        if(first.equals(second)) throw new IllegalArgumentException("first and seconds are the same");
        if(Objects.equals(first.getValue0(), second.getValue0())){
            return first.getValue1() < second.getValue1() ? DOWN : UP;
        }else {
            return first.getValue0() < second.getValue0() ? RIGHT : LEFT;
        }
    }
}
