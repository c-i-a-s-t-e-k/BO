import bo.cave.enums.Direction;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    @Test public void nextPositionTest() {
        Pair<Integer, Integer> position = new Pair<>(1,1);
        Assertions.assertEquals(new Pair<>(1,0), Direction.UP.getNextPosition(position));
        Assertions.assertEquals(new Pair<>(1,2), Direction.DOWN.getNextPosition(position));
        Assertions.assertEquals(new Pair<>(0,1), Direction.LEFT.getNextPosition(position));
        Assertions.assertEquals(new Pair<>(2,1), Direction.RIGHT.getNextPosition(position));
    }

    @Test public void secondIsOnTest() {
        Pair<Integer, Integer> position1 = new Pair<>(1,1);
        Pair<Integer, Integer> position2 = new Pair<>(0,1);
        Pair<Integer, Integer> position3 = new Pair<>(1,0);
        Assertions.assertEquals(Direction.LEFT, Direction.secondIsOn(position1, position2));
        Assertions.assertEquals(Direction.RIGHT, Direction.secondIsOn(position2, position1));
        Assertions.assertEquals(Direction.UP, Direction.secondIsOn(position1, position3));
        Assertions.assertEquals(Direction.DOWN, Direction.secondIsOn(position3, position1));
    }
}
