package Utility;

public class Timer {
    private long timeCounted=0L;
    private String name;
    private long waitEvent = 0L;

    public Timer(String name){
        this.name = name;
    }

    public void add(long add){
        timeCounted = add + timeCounted;
    }

    public void waitFor(long time){
        this.waitEvent = Configuration.clock + time;
    }

    public boolean waiting(){
        if (Configuration.clock < waitEvent)
            return true;
        return false;
    }

    public double getTimeCounted(){
        return (double)this.timeCounted;
    }

    public String toString(){
        return name+": "+(timeCounted)+"ticks";
    }

}
