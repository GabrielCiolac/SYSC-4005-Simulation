package Utility;

public class Timer {
    private long timeCounted=0L;
    private String name;
    private long currentStartTime = 0L;
    private long waitEvent = 0L;

    public Timer(String name){
        this.name = name;
    }

    public void startTimer(){
        if(this.currentStartTime != 0L)
            return;
        this.currentStartTime = Configuration.clock;
    }

    public void endTimer(){
        if(currentStartTime == 0L)
            return;
        this.timeCounted = (Configuration.clock - currentStartTime) + timeCounted;
        currentStartTime = 0L;
    }

    public void add(long add){
        timeCounted = add + timeCounted;
    }

    public void waitFor(long time){
        waitEvent = Configuration.clock + time;
    }

    public boolean waiting(){
        return Configuration.clock <= waitEvent;
    }

    public double getTimeCounted(){
        return (double)this.timeCounted;
    }

    public String toString(){
        return name+": "+(timeCounted)+"clocks";
    }

}
