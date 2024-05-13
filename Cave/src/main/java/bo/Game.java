package bo;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Player;
import bo.player.ResourceType;
import org.javatuples.Pair;

import java.util.*;

import static java.lang.Integer.min;


public class Game {
    CaveMap map;
    Player player;
    GamePath path;
    int turnsLimit = Constants.TURNS_LIMIT;

    public Game(CaveMap map, Player player) {
        this.map = map;
        this.player = player;
        this.path = new GamePath();
    }

    public int calculateCostToBase(Player player) {
        Pair<Integer, Integer> base = map.getBasePosition();

        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        Queue<Pair<Player, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(new Player(player), 0));

        while (!queue.isEmpty()) {
            Pair<Player, Integer> currentPair = queue.poll();
            Player currentPlayer = currentPair.getValue0();
            Integer prevCost = currentPair.getValue1();

            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()) {
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);
                if (neighbour.getPosition().equals(base)) {
                    return prevCost + 1;
                }
                if (!visited.contains(neighbour.getPosition())) {
                    visited.add(neighbour.getPosition());
                    queue.add(new Pair<>(neighbour, prevCost + 1));
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public Pair<Integer, Integer> calculateRandomDestination() {
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        Queue<Pair<Player, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(new Player(player), 0));

        while (!queue.isEmpty()) {
            Pair<Player, Integer> qp = queue.poll();
            Player currentPlayer = qp.getValue0();
            int currentMoveNumber = qp.getValue1() + 1;
            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()) {
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);

                if (!visited.contains(neighbour.getPosition()) && neighbour.energyLeft() > calculateCostToBase(neighbour)) {
                    visited.add(neighbour.getPosition());
                    queue.add(new Pair<>(neighbour, currentMoveNumber));
                    if (currentMoveNumber % Constants.MOVES_PER_TURN == 0) {
                        neighbour.use(ResourceType.FOOD);
                    }
                } else if (neighbour.energyLeft() < calculateCostToBase(neighbour)) {
                    return currentPlayer.getPosition();
                }
            }
        }

        return map.getBasePosition();
    }

    List<Pair<Direction, TileType>> getMoves(Pair<Integer, Integer> destination) {
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        Queue<Pair<Player, List<Pair<Direction, TileType>>>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(new Player(player), new ArrayList<>()));

        while (!queue.isEmpty()) {
            Pair<Player, List<Pair<Direction, TileType>>> currentPair = queue.poll();
            Player currentPlayer = currentPair.getValue0();
            List<Pair<Direction, TileType>> currentList = new ArrayList<>(currentPair.getValue1());

            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()) {
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);
                if (neighbour.getPosition().equals(destination)) {
                    currentList.add(neighMove);
                    return currentList;
                } else if (!visited.contains(neighbour.getPosition())) {
                    visited.add(neighbour.getPosition());
                    List<Pair<Direction, TileType>> currentListCopy = new ArrayList<>(currentList);
                    currentListCopy.add(neighMove);
                    queue.add(new Pair<>(neighbour, new ArrayList<>(currentListCopy)));
                }
            }
        }
        return new ArrayList<>();
    }

    public void startGame() {
        Pair<Integer, Integer> base = map.getBasePosition();
        path.addToPath(player);


        for (int turnsDone = 0; turnsDone < turnsLimit; ++turnsDone) {

            Pair<Integer, Integer> destination = calculateRandomDestination();
            List<Pair<Direction, TileType>> moves = getMoves(destination);

            for (int move_i = 0; move_i < min(Constants.MOVES_PER_TURN, moves.size()); ++move_i) {
                if (player.getPosition().equals(base)) {
                    player.restockBackpack();
                } else if (player.getPosition().equals(destination)) {
                    destination = base;
                    moves = getMoves(destination);
                }

                player.makeMove(moves.get(move_i));
                path.addToPath(player);
            }
            player.use(ResourceType.FOOD);
        }
    }


    public GamePath getAcceptablePath() {
        return path;
    }
}
