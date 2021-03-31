import Utility.Configuration;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final Queue<Component> buffer = new LinkedList<Component>();
    private Component type;
    private boolean isDone = false;

    public Buffer(Component type){
        this.type = type;
    }

    public boolean isEmpty(){
        return buffer.size() == 0;
    }
    public boolean isFull(){return buffer.size() >= Configuration.BUFFER_CAPACITY;}

    /**
     * Adds a component to buffer
     * @param c
     */
    public void put(Component c){
        buffer.add(c);
    }

    public void setDone(){
        this.isDone = true;
    }

    public boolean isDone(){
        return this.isDone;
    }

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

    public int getSize(){
        return buffer.size();
    }


}
