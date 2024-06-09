package bo.neighbors;

import bo.Constants;
import bo.Game;
import bo.GamePath;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.min;

public class CombinedNeighbour implements Neighbour {

    @Override
    public Game getNeighbour(Game game, int... args) {
        Game neighbor = new Game(game);
        GamePath path = game.getAcceptablePath();
        if (path.isEmpty()) return neighbor;

        Random random = new Random();
        List<Pair<Direction, TileType>> moves;

        List<Pair<Integer, Integer>> positions = new ArrayList<>(game.getDestinations());
        positions.add(game.map.getBasePosition());

        // Shuffle the positions to determine the order of processing
        Collections.shuffle(positions, random);

        for (Pair<Integer, Integer> destination : positions) {
            // Get moves around the current position
            List<Pair<Integer, Integer>> aroundPositions = Game.getAround(destination);

            for (Pair<Integer, Integer> alterDestination : aroundPositions) {
                moves = neighbor.getMoves(alterDestination);
                if (!moves.isEmpty()) {
                    for (int move_i = 0; move_i < min(Constants.MOVES_PER_TURN, moves.size()); ++move_i) {
                        Pair<Direction, TileType> move = moves.get(move_i);
                        neighbor.player.makeMove(move, true);
                        neighbor.getAcceptablePath().addToPath(neighbor.player);
                    }
                    break;  // Once a valid move is made, break out of the loop
                }
            }

            // Process moves for the current destination itself if no moves were found around
            moves = neighbor.getMoves(destination);
            if (!moves.isEmpty()) {
                for (int move_i = 0; move_i < min(Constants.MOVES_PER_TURN, moves.size()); ++move_i) {
                    Pair<Direction, TileType> move = moves.get(move_i);
                    neighbor.player.makeMove(move, true);
                    neighbor.getAcceptablePath().addToPath(neighbor.player);
                }
            }

            // Process moves for the base position
            moves = neighbor.getMoves(neighbor.map.getBasePosition());
            for (int move_i = 0; move_i < min(Constants.MOVES_PER_TURN, moves.size()); ++move_i) {
                Pair<Direction, TileType> move = moves.get(move_i);
                neighbor.player.makeMove(move, true);
                neighbor.getAcceptablePath().addToPath(neighbor.player);
            }
        }

        return neighbor;
    }
}
