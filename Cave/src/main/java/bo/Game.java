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
    public CaveMap map;
    public Player player;
    GamePath path;

    private Set<Pair<Integer,Integer>> destinations = new HashSet<>();

    public Game(CaveMap map, Player player) {
        this.map = map;
        this.map.unAchieveAllPositions();
        this.player = player;
        this.path = new GamePath();
    }

    public Game(Game game){
        this.map = game.map;
        this.map.unAchieveAllPositions();
        this.path = new GamePath();
        this.player =new Player(map);
        this.destinations = new HashSet<>();
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

            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()){
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);
                if (neighbour.getPosition().equals(base)){
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
        List<Pair<Integer, Integer>> solutions = new ArrayList<>();
        // choose random destination cell within energyToGetBack

        while (!queue.isEmpty()){
            Pair<Player, Integer> qp = queue.poll();
            Player currentPlayer = qp.getValue0();
            int currentMoveNumber = qp.getValue1() + 1;
            boolean isCorner = true;
            for (Pair<Direction, TileType> neighMove : currentPlayer.getMoves()){
                Player neighbour = new Player(currentPlayer);
                neighbour.makeMove(neighMove);

                if (!visited.contains(neighbour.getPosition()) && neighbour.energyLeft() > calculateCostToBase(neighbour)){
                    visited.add(neighbour.getPosition());
                    queue.add(new Pair<>(neighbour, currentMoveNumber));
                    if (currentMoveNumber % Constants.MOVES_PER_TURN == 0) {
                        neighbour.use(ResourceType.FOOD);
                    }
                    isCorner = false;
                }
                else if (neighbour.energyLeft() < calculateCostToBase(neighbour)){
                    solutions.add(currentPlayer.getPosition());
                }
            }
            if (isCorner) solutions.add(currentPlayer.getPosition());
        }
        if (solutions.isEmpty()) return map.getBasePosition();
        return solutions.get(new Random().nextInt(solutions.size()));
    }

    public List<Pair<Direction, TileType>> getMoves(Pair<Integer, Integer> destination){
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
                    currentList.add(neighMove);
                    return currentList;
                }
                else if (!visited.contains(neighbour.getPosition())) {
                    visited.add(neighbour.getPosition());
                    List<Pair<Direction, TileType>> currentListCopy = new ArrayList<>(currentList);
                    currentListCopy.add(neighMove);
                    queue.add(new Pair<>(neighbour, new ArrayList<>(currentListCopy)));
                }
            }
        }
        return new ArrayList<>();
    }

    public int startGame() {
        Pair<Integer, Integer> base = map.getBasePosition();
        path.addToPath(player);
        int turnsDone = 0;
        Pair<Integer, Integer> destination;
        boolean comeToBase = false;

        destination = calculateRandomDestination();
        this.destinations.add(destination);

        while (turnsDone < Constants.TURNS_LIMIT) {
            List<Pair<Direction, TileType>> moves = getMoves(destination);

            for (int move_i = 0; move_i < min(Constants.MOVES_PER_TURN, moves.size()); ++move_i) {

                if (player.getPosition().equals(base) && comeToBase) {
                    player.restockBackpack();
                    destination = calculateRandomDestination();
                    this.destinations.add(destination);
                    comeToBase = false;
                } else if (player.getPosition().equals(destination)) {
                    destination = base;
                    comeToBase = true;
                    break;
                }

                Pair<Direction, TileType> move = moves.get(move_i);
                player.makeMove(move,true);
                path.addToPath(player);

            }

            turnsDone++;
            if (player.getState().foodInBackpack() > 0) {
                player.use(ResourceType.FOOD);
            } else if (!player.getPosition().equals(base)) {
                return 0; // death
            }
        }
        return player.getAchievedPoints();
    }

    public static List<Pair<Integer, Integer>> getAround(Pair<Integer, Integer> basicPosition) {
        int x = basicPosition.getValue0();
        int y = basicPosition.getValue1();

        return List.of(
                Pair.with(x - 1, y),
                Pair.with(x, y - 1),
                Pair.with(x, y + 1),
                Pair.with(x + 1, y),
                basicPosition
        );
    }

    public Set<Pair<Integer, Integer>> getDestinations() {
        return new HashSet<>(destinations);
    }

    public GamePath getAcceptablePath() {
        return path;
    }
}
