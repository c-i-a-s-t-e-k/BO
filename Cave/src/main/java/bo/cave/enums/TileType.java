package bo.cave.enums;

import bo.player.ResourceType;

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
            case "R":
                return ROCK;
        }
        throw new IllegalArgumentException("Invalid tile type: " + name);
    }

    public ResourceType resourceNeed() {
        return switch (this){
            case CONSTRICTION_I ,CONSTRICTION_II,CONSTRICTION_III,DESCENSION -> ResourceType.LINE;
            case WATER -> ResourceType.PONTOON;
            case MIRACLE,NOTHING,ROCK -> ResourceType.NOTHING;
        };
    }

    @Override
//    zwraca pokolorowane znaki korzystając z kodów ANSI
    public String toString() {
        return switch (this){
            case ROCK -> "\u001B[38;5;94m" + "R" + "\u001B[0m";
            case MIRACLE -> "\u001B[38;5;145m" + "M" + "\u001B[0m";
            case CONSTRICTION_I -> "\u001b[33m" + "C" + "\u001B[0m";
            case CONSTRICTION_II -> "\u001b[38;5;214m" + "C" + "\u001B[0m";
            case CONSTRICTION_III -> "\u001b[38;5;88m" + "C" + "\u001B[0m";
            case DESCENSION -> "\u001b[38;5;59m" + "D" + "\u001B[0m";
            case WATER -> "\u001b[38;5;26m" + "W" + "\u001B[0m";
            case NOTHING -> " ";
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };

    }
}
