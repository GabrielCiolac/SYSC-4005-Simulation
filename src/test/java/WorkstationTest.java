import Utility.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Workstation Functions
 */
public class WorkstationTest {

    private WorkStation w;
    private Buffer b1,b2;
    @BeforeEach
    public void set_up() {
        w = new WorkStation(Configuration.WS1_MU,Configuration.WS1_SIGMA);
        b1 = new Buffer(Component.ONE);
        b2 = new Buffer(Component.TWO);
        w.addBuffer(b1);
        w.addBuffer(b2);
    }

    @Test()
    void test_produce(){
        for(int i = 0; i < Configuration.PRODUCTION_TARGET;i++) {
            assertFalse(b1.isDone());
            assertFalse(b2.isDone());
            assertFalse(w.canProduce());
            assertTrue(b1.put(Component.ONE));
            assertFalse(w.canProduce());
            assertTrue(b2.put(Component.TWO));
            assertTrue(w.canProduce());
            w.produce();
            assertTrue(b1.isEmpty());
            assertTrue(b2.isEmpty());
        }
        assertTrue(b1.isDone());
        assertTrue(b2.isDone());
    }
}
