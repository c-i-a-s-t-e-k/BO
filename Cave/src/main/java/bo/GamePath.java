package bo;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Backpack;
import bo.player.Player;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePath {
    List<Pair<Integer, Integer>> moveList = new ArrayList<>();
    List<Backpack> statesList = new ArrayList<>();

    public void addToPath(Player player) {
        moveList.add(player.getPosition());
        statesList.add(player.getState());
    }

    public void printPath(CaveMap map){
        for (Pair<Integer, Integer> pair : moveList) {
            System.out.println(pair.getValue0() + " " + pair.getValue1());
//            map.printMap(pair);
//            System.out.println("\n\n\n\n\n");
        }
        System.out.println(moveList.size());
    }
}
