package bo.neighbors;

import bo.Game;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShuffledPathNeighbour implements Neighbour {
    @Override
    public Game getNeighbour(Game game, int... args) {
        Game neighbor = new Game(game);
        Random random = new Random();
        List<Pair<Direction, TileType>> moves;

        List<Pair<Integer, Integer>> positions = new ArrayList<>(game.getDestinations());
        positions.add(game.map.getBasePosition());

        Collections.shuffle(positions, random);

        for (Pair<Integer, Integer> position : positions) {
            moves = neighbor.getMoves(position);

            for (Pair<Direction, TileType> move : moves) {
                Pair<Integer, Integer> newPos = move.getValue0().getNextPosition(neighbor.player.getPosition());
                if (neighbor.map.isPlaced(newPos)) {
                    neighbor.player.makeMove(move, true);
                    neighbor.getAcceptablePath().addToPath(neighbor.player);
                    break;  // Move to the next position after a valid move
                }
            }

        }

        return neighbor;
    }


}
