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

    /**
     * Adds a component to buffer
     * @param c
     */
    public synchronized void put(Component c){
        while(buffer.size() >= Configuration.BUFFER_CAPACITY){
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        buffer.add(c);
        notifyAll();
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
    public synchronized Component get(){
        while(buffer.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){

            }
        }
        Component got = buffer.poll();
        notifyAll();
        return got;
    }

    public int getSize(){
        return buffer.size();
    }


}
