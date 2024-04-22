package bo.player;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;


import java.util.Set;
import java.util.stream.Collectors;

public class Player {
    private Pair<Integer,Integer> position;

    private int achievedPoints;
    private Backpack state;
    private CaveMap map;

    public Player(CaveMap caveMap){
        position = caveMap.getBasePosition();
        achievedPoints = 0;
        state = new Backpack();
        map = caveMap;
    }

    public Set<Pair<Direction, TileType>> getMoves(){
       Set<Pair<Direction, TileType>> possibleMoves = map.getPossibleMoves(position);
        return possibleMoves.stream().filter(pair -> state.isEnough(pair.getValue1())).collect(Collectors.toSet());
    }

    public void makeMove(Pair<Direction, TileType> move){
        position = move.getValue0().getNextPosition(position);
        state.useResource(move.getValue1().resourceNeed(), 1);//kiedyś można zrobić żeby zawsze brało jakąś inną wartość
        achievedPoints += map.achievePosition(position);
    }
}
