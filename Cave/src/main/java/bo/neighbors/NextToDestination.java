package bo.neighbors;

import bo.Game;
import bo.GamePath;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.min;

public class NextToDestination implements Neighbour {

    @Override
    public Game getNeighbour(Game game, int... args) {
        Game neighbor = new Game(game);
        GamePath path = game.getAcceptablePath();
        if (path.isEmpty()) return neighbor;
        short movesDone = 0;
        boolean comeToBase = false;
        List<Pair<Direction, TileType>> moves = new ArrayList<>();
        for(Pair<Integer,Integer> destination: game.getDestinations()){

            for(Pair<Integer,Integer> alterDestination: Game.getAround(destination)){
                moves = neighbor.getMoves(alterDestination);
                if(!moves.isEmpty())break;
            }

            for (int move_i = 0; move_i < min(5, moves.size()); ++move_i) {
                Pair<Direction, TileType> move = moves.get(move_i);
                neighbor.player.makeMove(move,true);
                neighbor.getAcceptablePath().addToPath(neighbor.player);
            }
            moves = neighbor.getMoves(neighbor.map.getBasePosition());
            for (int move_i = 0; move_i < min(5, moves.size()); ++move_i) {
                Pair<Direction, TileType> move = moves.get(move_i);
                neighbor.player.makeMove(move,true);
                neighbor.getAcceptablePath().addToPath(neighbor.player);
            }

        }

        return neighbor;
    }
}
