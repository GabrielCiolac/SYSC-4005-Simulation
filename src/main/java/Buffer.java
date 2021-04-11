import Utility.Configuration;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Data structure for buffers
 */
public class Buffer {
    private final Queue<Component> buffer = new LinkedList<Component>();//backed by a queue FIFO
    private final Component type;//what type is this buffer
    private boolean isDone = false; //by default the buffer is not done

    public Buffer(Component type){
        this.type = type;
    }

    /**
     * Checks if empty
     * @return
     */
    public boolean isEmpty(){
        return buffer.size() == 0;
    }

    /**
     * Checks if full
     * @return
     */
    public boolean isFull(){return buffer.size() >= Configuration.BUFFER_CAPACITY;}

    /**
     * Adds a component to buffer
     * @param c
     */
    public boolean put(Component c){
        if(c != this.type || this.isFull())
            return false;
        buffer.add(c);
        return true;
    }

    /**
     * sets the buffer as done
     */
    public void setDone(){
        this.isDone = true;
    }

    /**
     * checks if buffer is done
     * @return
     */
    public boolean isDone(){
        return this.isDone;
    }

    /**
     * Gets component type
     * @return
     */
    public Component getType(){
        return this.type;
    }
    /**
     * Removes component from buffer
     * @return
     */
    public Component get(){
        return buffer.poll();
    }

    /**
     * Returns buffer size
     * @return
     */
    public int getSize(){
        return buffer.size();
    }


}
