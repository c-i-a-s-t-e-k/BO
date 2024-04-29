package bo;

import bo.cave.CaveMap;
import bo.cave.enums.Direction;
import bo.cave.enums.TileType;
import bo.player.Player;
import bo.player.ResourceType;
import org.javatuples.Pair;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    CaveMap map;
    Player player;
    GamePath path;
    int turnsLimit = 20;

    public Game(CaveMap map, Player player) {
        this.map = map;
        this.player = player;
        this.path = new GamePath();
    }

    public void startGame() {
        Pair<Integer, Integer> base = map.getBasePosition();
        path.addToPath(player);
        int turnsDone = 0;
        int score = 0;
        int energyToGetBack = 0;
//        Random random = new Random();
        int turnEnergy;
        List<Pair<Integer, Integer>> round = new ArrayList<>();
        int prevTurnsDone = -1;
        while (turnsDone < turnsLimit && prevTurnsDone != turnsDone){
            prevTurnsDone = turnsDone;
            round.add(base);
            while (energyToGetBack < player.energyLeft() - 5 && (turnsLimit - turnsDone) * 5 > energyToGetBack) {
                player.use(ResourceType.FOOD);
                turnEnergy = 5;

                for (Pair<Direction, TileType> move : player.getMoves()) {
                    TileType type = move.getValue1();
                    Direction direction = move.getValue0();
                    if (turnEnergy - type.energyToStep() >= 0) {
                        player.makeMove(move);
                        energyToGetBack += type.energyToStep();
                        turnEnergy -= type.energyToStep() + type.energyToScore();
                        break;
                    }
                }
                round.add(player.getPosition());
                path.addToPath(player);
                turnsDone++;
            }
            turnEnergy = 5;
            player.use(ResourceType.FOOD);
            for (Pair<Direction, TileType> move : map.getMovesToBack(round)) {
                TileType type = move.getValue1();
                if(turnEnergy - type.energyToStep() < 0) {
                    turnsDone++;
                    player.use(ResourceType.FOOD);
                    turnEnergy = 5;
                }
                turnEnergy -= type.energyToStep();
                player.makeMove(move);
                path.addToPath(player);
            }
            round.clear();
            player.restockBackpack();
        }
    }


    public GamePath getAcceptablePath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
