package bo.tests;


import bo.Constants;
import bo.bee.BeeTrainer;
import bo.cave.CaveMap;
import bo.neighbors.CombinedNeighbour;
import bo.neighbors.NextToDestination;
import bo.neighbors.ShuffledPathNeighbour;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;

public class StrategyTest {

    public static void main(String[] args){
        BeeTrainer combined = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED), new CombinedNeighbour());
        BeeTrainer shuffled = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED),new ShuffledPathNeighbour());
        BeeTrainer nextTo = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED),new NextToDestination());

        combined.train();
        shuffled.train();
        nextTo.train();

        Plot plt = Plot.create();
        plt.plot()
                .add(combined.bestBees)
                .label("Combined Strategies Results");

        plt.plot()
                .add(nextTo.bestBees)
                .label("Next to Results");

        plt.plot()
                .add(shuffled.bestBees)
                .label("Shuffling Results");

        plt.xlabel("Nr of iteration");
        plt.ylabel("Score");
        plt.title("Score depends on  neighbor's finding strategy");
        plt.legend().loc("upper right");
        try {
            plt.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PythonExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
