import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Producer
 */
public class Inspector{
    private LinkedList<Buffer> buffers;
    private HashSet<Component> components;
    private final Timer blocked = new Timer("Total Time Blocked");
    private final Timer producing = new Timer("Time Spent Producing");
    private Component inHand = null;
    private boolean done = false;

    public Inspector(){
        this.buffers = new LinkedList<Buffer>();
        this.components = new HashSet<Component>();
    }

    /**
     * Adds a buffer to the Inspector
     * @param b
     */
    public void addBuffer(Buffer b){
        buffers.add(b);
        components.add(b.getType());
    }

    /**
     * Gets a component from the calling population
     * @return
     */
    private Component produceComponent(){
        if(buffers.size() == 0)
            return null;
        int index = (int) (Math.random() * components.size());
        Component c = (Component) components.toArray()[index];
        return c;
    }

    /**
     * Scheduling Algorithm
     * @return
     */
    private boolean tryDeposit(Component c){
        Buffer candidate = null; //for buffers with 1 entry
        for(Buffer b: this.buffers){ //checks all buffers
            if(b.isFull() || b.getType() != c) //if the buffer is full or is not of type component, continue
                continue;
            else if(b.isEmpty()){ //if buffer is empty
                b.put(c);
                return true;
            }
            else if(candidate == null && b.getSize() == 1) //if no candidate yet, and size is 1
                candidate = b;//make candidate, this insures 1->3 priority
        }
        if(candidate != null){ //if there is a candidate
            candidate.put(c);
            return true;
        }
        return false;
    }


    /**
     * Gets the number of ticks that the inspector needs to wait for to produce a component
     * @param c
     * @return
     */
    private long getWaitTime(Component c){
        if(c==Component.ONE)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP1_MU,Configuration.SERVINSP1_SIGMA);
        if(c==Component.TWO)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP22_MU,Configuration.SERVINSP22_SIGMA);
        else
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP23_MU,Configuration.SERVINSP23_SIGMA);

    }


    /**
     * Sets the wait time on the production timer
     * @param c
     */
    private void waitForInspection(Component c){
        long wait = getWaitTime(c);
        this.producing.waitFor(wait);
    }

    /**
     * Prints statistics
     */
    public void print(){
        double percentage = (this.blocked.getTimeCounted()/(this.producing.getTimeCounted()))*100;
        System.out.println("Percentage of time spent blocked: "+percentage+"%");
    }

    /**
     * Checks if all the buffers are done, if they are then sets the done flag
     */
    public void isDone(){
        this.done = buffers.size() == 0;
    }

    /**
     * Called by the launcher on every tick
     */
    public void dutyCycle() {
        this.producing.add(1L);//increment the time spent producing
        if(inHand == null) {//if you have no component in hand
            inHand = produceComponent(); //grab a component from the calling population
            waitForInspection(inHand); //set the inspection timer
            return; //started producing in this tick, can return
        }
        if(!this.producing.waiting()){ //if you are not waiting to produce something
            if(tryDeposit(inHand)) //try to deposit what's in your hand
                inHand = null; //if you were able to, set inHand to null
            else
                this.blocked.add(1L);//if you aren't waiting and you were unable to deposit, you were blocked
        }
        isDone();//check to see if you can set done flag
    }
}
