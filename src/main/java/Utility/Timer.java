package Utility;

/**
 * A utility for measuring time and keeping track of events
 */
public class Timer {
    private long timeCounted=0L;//time counted
    private final String name;//text of buffer
    private long waitEvent = 0L;//when do you have to wait until

    public Timer(String name){
        this.name = name;
    }

    /**
     * Adds time to timer
     * @param add
     */
    public void add(long add){
        timeCounted = add + timeCounted;
    }

    /**
     * Marks until which tick you have to wait for
     * @param time
     */
    public void waitFor(long time){
        this.waitEvent = Configuration.clock + time;
    }

    /**
     * Has the timer reached the tick it's waiting for
     * @return
     */
    public boolean waiting(){
        if (Configuration.clock <= waitEvent)
            return true;
        return false;
    }

    /**
     * How much time has elapsed on the timer
     * @return
     */
    public double getTimeCounted(){
        return (double)this.timeCounted;
    }

    /**
     * Get string of time counted
     * @return
     */
    public String toString(){
        return name+": "+(timeCounted)+"ticks";
    }

}
