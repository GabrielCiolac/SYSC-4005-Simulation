import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This JUnit Test primarily focuses on the scheduling algorithm
 */
public class InspectorTest {

    private Inspector i;
    @BeforeEach
    public void set_up(){
        i = new Inspector();
        Buffer b1 = new Buffer(Component.ONE);
        Buffer b2 = new Buffer(Component.ONE);
        Buffer b3 = new Buffer(Component.TWO);

        i.addBuffer(b1);
        i.addBuffer(b2);
        i.addBuffer(b3);
    }

    @Test
    public void test_deposit_empty_buffer(){
        assertTrue(i.tryDeposit(Component.ONE));
        assertTrue(i.tryDeposit(Component.TWO));
    }

    @Test
    public void test_deposit_invalid_component(){
        assertFalse(i.tryDeposit(Component.THREE));
    }
}
