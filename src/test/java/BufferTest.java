import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing the buffer
 */
public class BufferTest {
    private Buffer b;
    @BeforeEach
    public void set_up(){
        b = new Buffer(Component.ONE);
    }

    @Test
    public void test_empty_buffer(){
        assertTrue(b.isEmpty());
        assertFalse(b.isFull());
    }

    @Test
    void test_size(){
        assertTrue(b.put(Component.ONE));
        assertEquals(b.getSize(),1);
        assertFalse(b.isEmpty());
        assertTrue(b.put(Component.ONE));
        assertEquals(b.getSize(),2);
        assertFalse(b.put(Component.ONE));
        assertTrue(b.isFull());

        assertNotNull(b.get());
        assertEquals(b.getSize(),1);
        assertNotNull(b.get());
        assertEquals(b.getSize(),0);
        assertNull(b.get());
        assertTrue(b.isEmpty());
        assertFalse(b.isFull());
    }

    @Test
    void test_wrong_component(){
        assertFalse(b.put(Component.TWO));
        assertFalse(b.put(Component.TWO));
        assertTrue(b.isEmpty());
        assertNull(b.get());
    }
}
