package bo.cave.enums;

public enum TileLevel {
    Base,
    I,
    II,
    III,
    IV;


    public static TileLevel fromString(String string){
        switch (string){
            case "Base":
                return TileLevel.Base;
            case "I":
                return TileLevel.I;
            case "II":
                return TileLevel.II;
            case "III":
                return TileLevel.III;
            case "IV":
                return TileLevel.IV;
        }
        throw new IllegalArgumentException("Unknown tile level: " + string);
    }
}



