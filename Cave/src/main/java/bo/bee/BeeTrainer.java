package bo.bee;

import bo.Game;
import bo.cave.CaveMap;
import bo.neighbors.Neighbour;
import bo.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeeTrainer {
    CaveMap map;
    int populationSize = 10;
    int iterations = 100;
    int neighbourCount = 5;

    Neighbour neighbourStrategy;

    public BeeTrainer(CaveMap map, Neighbour neighbourStrategy) {
        this.map = map;
        this.neighbourStrategy = neighbourStrategy;
    }

    public void train() {
        List<Bee> population = new ArrayList<>();

        System.out.print("Starting bees scores: ");
        for (int i = 0; i < populationSize; i++) {
            Player player = new Player(map);
            Game game = new Game(map, player);
            int score = game.startGame();
            System.out.print(score + " ");
            population.add(new Bee(game, score));
        }
        System.out.println();
        System.out.println("__________________________________________________");


        for (int iter = 0; iter < iterations; iter++) {
            List<Bee> newPopulation = new ArrayList<>();

            for (Bee bee : population) {
                for (int j = 0; j < neighbourCount; j++) {
                    try {
                        Neighbour strategy = neighbourStrategy;
                        Game neighbourGame = strategy.getNeighbour(bee.game);
                        int neighbourScore = neighbourGame.startGame();
                        newPopulation.add(new Bee(neighbourGame, neighbourScore));
                    } catch (RuntimeException e) {
                        // Some iterations cause error due to lack of resources, it is perfectly fine
                    }
                }
            }

            population.addAll(newPopulation);
            population.sort(Comparator.comparingInt(b -> -b.score));
            while (population.size() > populationSize) {
                population.remove(population.size() - 1);
            }

            if (iter % 5 == 0) {
                System.out.println("Iteration nr: " + iter + " | Best Score is: " + population.get(0).score);
                System.out.print("Bees scores: ");
                for (Bee curr: population){
                    System.out.print(curr.score + " ");
                }
                System.out.println();
                System.out.println("__________________________________________________");
            }
        }

        Bee bestBee = population.get(0);
        System.out.println("Best score found: " + bestBee.score);
    }
}
