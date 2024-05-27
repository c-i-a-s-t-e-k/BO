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
    int populationSize = Constants.BEES_POPULATION_SIZE;
    int iterations = Constants.BEES_ITERATIONS;
    int neighbourCount = Constants.BEES_NEIGHBOURS_COUNT;

    Neighbour neighbourStrategy;

    public BeeTrainer(CaveMap map, Neighbour neighbourStrategy) {
        this.map = map;
        this.neighbourStrategy = neighbourStrategy;
    }

    public void train() {
        List<Bee> population = new ArrayList<>();

        System.out.print("Starting bees scores: ");
        int addedToPopulation = 0;
        while (addedToPopulation < populationSize) {
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


        for (int iter = 0; iter < iterations; iter++) {
            List<Bee> newPopulation = new ArrayList<>();

            for (Bee bee : population) {
                for (int j = 0; j < neighbourCount; j++) {
                    Neighbour strategy = neighbourStrategy;
                    Game neighbourGame = strategy.getNeighbour(bee.game);
                    int neighbourScore = neighbourGame.startGame();
                    newPopulation.add(new Bee(neighbourGame, neighbourScore));
                }
            }

            population.addAll(newPopulation);
            population.sort(Comparator.comparingInt(b -> -b.score));
            while (population.size() > populationSize) {
                population.removeLast();
            }

            if (iter % 5 == 0) {
                System.out.println("After iteration nr: " + iter + " | Best Score is: " + population.getFirst().score);
                System.out.print("Bees scores: ");
                for (Bee curr: population){
                    System.out.print(curr.score + " ");
                }
                System.out.println();
                System.out.println("__________________________________________________");
            }
        }

        Bee bestBee = population.getFirst();
        System.out.println("Best score found: " + bestBee.score);
    }
}
