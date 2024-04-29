package bo;

import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Backpack;
import bo.player.Player;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class GamePath {
    List<Pair<Integer, Integer>> moveList = new ArrayList<>();
    List<Backpack> statesList = new ArrayList<>();

    public void addToPath(Player player) {
        moveList.add(player.getPosition());
        statesList.add(player.getState());
    }
}
