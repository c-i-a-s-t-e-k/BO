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
            case DESCENSION -> ResourceType.LINE;
            case WATER -> ResourceType.OXYGEN;
            case MIRACLE,NOTHING,CONSTRICTION_I, CONSTRICTION_II, CONSTRICTION_III -> ResourceType.NOTHING;
            default -> throw new IllegalArgumentException("Invalid tile type to need");
        };
    }

    @Override
//    zwraca pokolorowane znaki korzystając z kodów ANSI
    public String toString() {
        return switch (this){
            case ROCK -> "\u001B[38;5;94m" + "R" + "\u001B[39m";
            case MIRACLE -> "\u001B[38;5;145m" + "M" + "\u001B[39m";
            case CONSTRICTION_I -> "\u001b[33m" + "C" + "\u001B[39m";
            case CONSTRICTION_II -> "\u001b[38;5;214m" + "C" + "\u001B[39m";
            case CONSTRICTION_III -> "\u001b[38;5;88m" + "C" + "\u001B[39m";
            case DESCENSION -> "\u001b[38;5;59m" + "D" + "\u001B[39m";
            case WATER -> "\u001b[38;5;26m" + "W" + "\u001B[39m";
            case NOTHING -> " ";
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
    public int getPoints(){
        return switch (this){
            case MIRACLE, CONSTRICTION_I, DESCENSION -> 2;
            case CONSTRICTION_II, WATER -> 3;
            case CONSTRICTION_III -> 4;
            case NOTHING -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
    public int energyToStep(){
        return switch (this){
            case MIRACLE, DESCENSION, WATER, NOTHING -> 1;
            case CONSTRICTION_I -> 2;
            case CONSTRICTION_II -> 3;
            case CONSTRICTION_III -> 4;
            case ROCK -> 1000000000;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public int energyToScore(){
        return switch (this){
            case CONSTRICTION_I, CONSTRICTION_II, CONSTRICTION_III, NOTHING -> 0;
            case MIRACLE, WATER, DESCENSION -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
