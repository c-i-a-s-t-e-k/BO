package bo.neighbors;

import bo.Game;

public interface Neighbour {
    Game getNeighbour(Game game, int ... args);
}
