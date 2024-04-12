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
}
