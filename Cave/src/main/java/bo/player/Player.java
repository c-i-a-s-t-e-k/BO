package bo.player;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;


import java.util.Set;
import java.util.stream.Collectors;

public class Player {
    private Pair<Integer, Integer> position;

    private int achievedPoints;
    private final Backpack state;
    private final CaveMap map;

    public Player(CaveMap caveMap) {
        position = caveMap.getBasePosition();
        achievedPoints = 0;
        state = new Backpack();
        map = caveMap;
    }

    public Player(Player other) {
        position = new Pair<>(other.getPosition().getValue0(), other.getPosition().getValue1());
        achievedPoints = other.getAchievedPoints();
        state = other.getState().clone();
        map = other.map;
    }

    public int energyLeft() {
        return state.foodInBackpack() * 5;
    }

    public void use(ResourceType resource) {
        state.useResource(resource);
    }

    public int getAchievedPoints() {
        return achievedPoints;
    }

    public Set<Pair<Direction, TileType>> getMoves() {
        Set<Pair<Direction, TileType>> possibleMoves = map.getPossibleMoves(position);
        return possibleMoves.stream().filter(pair -> state.isEnough(pair.getValue1())).collect(Collectors.toSet());
    }

    public void makeMove(Pair<Direction, TileType> move) {
        position = move.getValue0().getNextPosition(position);
        state.useResource(move.getValue1().resourceNeed());
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public Backpack getState() {
        return state.clone();
    }

    public void showResoucesInBackpack() {
        state.showResources();
    }

    public void restockBackpack() {
        state.fillDefault();
    }
}
