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

public class MapSizeTest {

    public static void main(String[] args){
        Constants.BEES_ITERATIONS = 50;
        List<Integer> xScale = new ArrayList<>();
        List<Integer> combinedScores = new ArrayList<>();
        List<Integer> nextToScores = new ArrayList<>();
        List<Integer> shuffleScores = new ArrayList<>();

        for(int i = 30;i <= 200; i += 10){
            Constants.MAP_SIZE = i;
            BeeTrainer combined = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED), new CombinedNeighbour());
            BeeTrainer shuffled = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED),new ShuffledPathNeighbour());
            BeeTrainer nextTo = new BeeTrainer(CaveMap.generateCaveMap(Constants.SEED),new NextToDestination());
            combined.train();
            shuffled.train();
            nextTo.train();
            xScale.add(i);
            shuffleScores.add(shuffled.bestBees.getLast());
            combinedScores.add(combined.bestBees.getLast());
            nextToScores.add(nextTo.bestBees.getLast());
        }

        Plot plt = Plot.create();
        plt.plot()
                .add(xScale,combinedScores)
                .label("Combined Strategies Results");

        plt.plot()
                .add(xScale,nextToScores)
                .label("Next to Results");

        plt.plot()
                .add(xScale,shuffleScores)
                .label("Shuffling Results");

        plt.xlabel("Size of map");
        plt.ylabel("Score");
        plt.title("Score depends on map's size");
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
