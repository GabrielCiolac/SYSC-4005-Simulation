import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BufferTest {
    private Buffer b;
    @BeforeEach
    public void set_up(){
        b = new Buffer(Component.ONE);
    }

    @Test
    public void test_empty_buffer(){

    }
}
