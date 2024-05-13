package bo;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Backpack;
import bo.player.Player;
import bo.player.ResourceType;
import org.javatuples.Pair;

import javax.swing.text.Position;
import java.util.*;

public class Game {
    CaveMap map;
    Player player;
    GamePath path;
    int turnsLimit = 20;

    public Game(CaveMap map, Player player) {
        this.map = map;
        this.player = player;
        this.path = new GamePath();
    }

    public int calculateCostToBase(Player player) {
        Pair<Integer, Integer> base = map.getBasePosition();
        int cost = 0;

        //BFS

        return cost;
    }

    public Pair<Integer, Integer> calculateRandomDestination() {
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        Queue<Player> queue = new ArrayDeque<>();
        queue.add(new Player(player));
        // choose random destination cell within energyToGetBack

        while (!queue.isEmpty()){
            Player currentPlayer = queue.poll();
            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()){
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);
                if (!visited.contains(neighbour.getPosition()) && neighbour.energyLeft() >= calculateCostToBase(neighbour)){
                    visited.add(neighbour.getPosition());
                    queue.add(neighbour);
                }
                else if(neighbour.energyLeft() < calculateCostToBase(neighbour)){
                    return currentPlayer.getPosition();
                }
            }
        }

        return map.getBasePosition();
    }

    List<Pair<Direction, TileType>> getMoves(Pair<Integer, Integer> destination){
        // BFS
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        Queue<Pair<Player, List<Pair<Direction, TileType>>>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(new Player(player), new ArrayList<>()));
        // choose random destination cell within energyToGetBack

        while (!queue.isEmpty()){
            Pair<Player, List<Pair<Direction, TileType>>> currentPair = queue.poll();
            Player currentPlayer = currentPair.getValue0();
            List<Pair<Direction, TileType>> currentList = new ArrayList<>(currentPair.getValue1());

            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()){
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);
                if (neighbour.getPosition().equals(destination)){
                    return currentList;
                }
                if (!visited.contains(neighbour.getPosition())) {
                    visited.add(neighbour.getPosition());
                    currentList.add(neighMove);
                    queue.add(new Pair<>(neighbour, currentList));
                }
            }
        }
        return new ArrayList<>();
    }

    public void startGame() {
        Pair<Integer, Integer> base = map.getBasePosition();
        path.addToPath(player);
        int turnsDone = 0;
//        int score = 0;
        Pair<Integer, Integer> destination;

        while (turnsDone < turnsLimit){
            destination = calculateRandomDestination();
            List<Pair<Direction, TileType>> moves = getMoves(destination);

            for (Pair<Direction, TileType> move : moves){
                player.use(ResourceType.FOOD);
                player.makeMove(move);
                path.addToPath(player);
                turnsDone++;
            }

            // return to base
            if (destination.equals(base)) {
                player.restockBackpack();
            }
        }
    }


    public GamePath getAcceptablePath() {
        return path;
    }

}
