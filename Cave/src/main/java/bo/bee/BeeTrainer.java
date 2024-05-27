package bo.bee;

import bo.Constants;
import bo.Game;
import bo.cave.CaveMap;
import bo.neighbors.Neighbour;
import bo.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeeTrainer {
    CaveMap map;

    Neighbour neighbourStrategy;

    public BeeTrainer(CaveMap map, Neighbour neighbourStrategy) {
        this.map = map;
        this.neighbourStrategy = neighbourStrategy;
    }

    public void train() {
        List<Bee> population = new ArrayList<>();

        System.out.print("Starting bees scores: ");
        int addedToPopulation = 0;
        while (addedToPopulation < Constants.BEES_POPULATION_SIZE) {
            Player player = new Player(map);
            Game game = new Game(map, player);
            int score = game.startGame();
            if (score == 0) continue;
            System.out.print(score + " ");
            population.add(new Bee(game, score));
            ++addedToPopulation;
        }
        System.out.println();
        System.out.println("__________________________________________________");


        for (int iter = 0; iter < Constants.BEES_ITERATIONS; iter++) {
            List<Bee> newPopulation = new ArrayList<>();

            for (Bee bee : population) {
                for (int j = 0; j < Constants.BEES_NEIGHBOURS_COUNT; j++) {
                    Neighbour strategy = neighbourStrategy;
                    Game neighbourGame = strategy.getNeighbour(bee.game);
                    int neighbourScore = neighbourGame.startGame();
                    newPopulation.add(new Bee(neighbourGame, neighbourScore));
                }
            }

            population.addAll(newPopulation);
            population.sort(Comparator.comparingInt(b -> -b.score));
            population.subList(Constants.BEES_POPULATION_SIZE, population.size()).clear();

            if (iter % 5 == 0) {
                System.out.println("After iteration nr: " + iter + " | Best Score is: " + population.getFirst().score);
                System.out.print("Bees scores: ");
                for (Bee curr: population){
                    System.out.print(curr.score + " ");
                }
                System.out.println("\n__________________________________________________");
            }
        }

        Bee bestBee = population.getFirst();
        System.out.println("Best score found: " + bestBee.score);
    }
}
