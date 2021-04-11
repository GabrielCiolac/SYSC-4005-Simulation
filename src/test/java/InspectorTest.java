import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This JUnit Test primarily focuses on the scheduling algorithm
 */
public class InspectorTest {

    private Inspector i;
    private Buffer b1,b2,b3;
    @BeforeEach
    public void set_up(){
        i = new Inspector();
        b1 = new Buffer(Component.ONE);
        b2 = new Buffer(Component.ONE);
        b3 = new Buffer(Component.TWO);

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

    @Test
    public void test_order(){
        //b1 should have been deposited in
        assertTrue(i.tryDeposit(Component.ONE));
        assertFalse(b1.isEmpty());
        assertTrue(b2.isEmpty());
        assertTrue(b3.isEmpty());

        //b2 should have been deposited in
        assertTrue(i.tryDeposit(Component.ONE));
        assertFalse(b1.isEmpty());
        assertFalse(b2.isEmpty());
        assertTrue(b3.isEmpty());

        //b1 should have been deposited in
        assertTrue(i.tryDeposit(Component.ONE));
        assertFalse(b1.isEmpty());
        assertFalse(b2.isEmpty());
        assertTrue(b1.isFull());
        assertFalse(b2.isFull());
        assertTrue(b3.isEmpty());

        //b2 should have been deposited in
        assertTrue(i.tryDeposit(Component.ONE));
        assertFalse(b1.isEmpty());
        assertFalse(b2.isEmpty());
        assertTrue(b1.isFull());
        assertTrue(b2.isFull());
        assertTrue(b3.isEmpty());

        assertNotNull(b2.get());

        //b2 should have been deposited in
        assertTrue(i.tryDeposit(Component.ONE));
        assertFalse(b1.isEmpty());
        assertFalse(b2.isEmpty());
        assertTrue(b1.isFull());
        assertTrue(b2.isFull());
        assertTrue(b3.isEmpty());

    }
}
