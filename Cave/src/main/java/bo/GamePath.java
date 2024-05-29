package bo;

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

    public boolean isEmpty() {
        return moveList.isEmpty();
    }
}
