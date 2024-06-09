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
import java.util.ArrayList;
import java.util.List;

public class PopulationSizeTest {
    public static void main(String[] args){
        List<Integer> xScale = new ArrayList<>();
        List<Integer> combinedScores = new ArrayList<>();

        for(int i = 2;i <= 50; i ++){
            Constants.BEES_POPULATION_SIZE = i;
            BeeTrainer combined = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED), new CombinedNeighbour());
            combined.train();
            xScale.add(i);
            combinedScores.add(combined.bestBees.getLast());

        }

        Plot plt = Plot.create();
        plt.plot()
                .add(xScale,combinedScores)
                .label("Results");


        plt.xlabel("Size of bees' population");
        plt.ylabel("Score");
        plt.title("Score depends on bees' population");
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
