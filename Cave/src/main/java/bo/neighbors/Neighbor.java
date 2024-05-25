package bo.neighbors;

import bo.Game;

public interface Neighbor {
    abstract public Game getNeighbor(Game game, int ... args);
}
