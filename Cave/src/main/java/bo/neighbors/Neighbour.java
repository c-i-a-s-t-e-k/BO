package bo.neighbors;

import bo.Game;

public interface Neighbour {
    abstract public Game getNeighbour(Game game, int ... args);
}
