package bo.cave;

import bo.cave.enums.Directions;
import bo.cave.enums.TileLevel;
import bo.cave.enums.TileStatus;
import bo.cave.enums.TileType;

import java.util.Date;
import java.util.List;

public class MapTile {
    private TileType type;
    private int depression; // poziom zgłębienia ze zbioru {0, 25, 50, 75, 100 ...}
    private TileLevel level;
    private TileStatus status;
    private List<Directions> exits;

    private MapTile(){
        type = TileType.NOTHING;
        depression = 0;
        level =TileLevel.Base;
        status = TileStatus.CONQUERED;
    }

    public MapTile(String input){
        String[] split = input.split(" ");

        level = TileLevel.fromString(split[0]);
        type = TileType.fromString(split[1]);
        for(int i = 2; i < split.length; i++){
            exits.add(Directions.fromString(split[i]));
        }
        this.status = TileStatus.NOT_DISCOVERED;
    }

    public void rotate(){
        for(int i = 1; i < exits.size(); i++){
            exits.set(i, exits.get(i).next());
        }
    }

    public void setDepression(int depression) {
        if(depression % 25 == 0)
            this.depression = depression;
        else throw new IllegalArgumentException("Invalid depression");
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
    public static MapTile[] getBase(){
        MapTile[] base = {
                new MapTile(), new MapTile(), new MapTile(),
                new MapTile(), new MapTile(), new MapTile(),
                new MapTile(), new MapTile(), new MapTile()
        };
        base[3].type = TileType.ROCK;
        base[5].type = TileType.ROCK;

        base[0].exits.add(Directions.UP);
        base[0].exits.add(Directions.LEFT);
        base[0].exits.add(Directions.RIGHT);

        base[1].exits.add(Directions.DOWN);
        base[1].exits.add(Directions.LEFT);
        base[1].exits.add(Directions.RIGHT);

        base[2].exits.add(Directions.UP);
        base[2].exits.add(Directions.LEFT);
        base[2].exits.add(Directions.RIGHT);

        base[4].exits.add(Directions.UP);
        base[4].exits.add(Directions.DOWN);

        base[6].exits.add(Directions.DOWN);
        base[6].exits.add(Directions.LEFT);
        base[6].exits.add(Directions.RIGHT);

        base[7].exits.add(Directions.UP);
        base[7].exits.add(Directions.LEFT);
        base[7].exits.add(Directions.RIGHT);

        base[8].exits.add(Directions.DOWN);
        base[8].exits.add(Directions.LEFT);
        base[8].exits.add(Directions.RIGHT);
        return base;
    }
}
