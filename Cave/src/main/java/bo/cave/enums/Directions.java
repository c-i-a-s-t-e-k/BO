package bo.cave.enums;

public enum Directions {
    UP, DOWN, LEFT, RIGHT;

    public static Directions fromString(String direction) {
        switch (direction) {
            case "U":
                return UP;
            case "D":
                return DOWN;
            case "L":
                return LEFT;
            case "R":
                return RIGHT;
        }
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }

    public Directions next() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
        }
        throw new IllegalArgumentException("Invalid direction: " + this);
    }
}
