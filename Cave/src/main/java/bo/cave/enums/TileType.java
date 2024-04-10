package bo.cave.enums;

public enum TileType {
    WATER,
    MIRACLE,
    CONSTRICTION_I,
    CONSTRICTION_II,
    CONSTRICTION_III,
    NOTHING,
    DESCENSION,
    ROCK; // nie można na niego wejść


    public static TileType fromString(String name) {
        switch (name) {
            case "W":
                return WATER;
            case "M":
                return MIRACLE;
            case "C1":
                return CONSTRICTION_I;
            case "C2":
                return CONSTRICTION_II;
            case "C3":
                return CONSTRICTION_III;
            case "N":
                return NOTHING;
            case "D":
                return DESCENSION;
        }
        throw new IllegalArgumentException("Invalid tile type: " + name);
    }
}
