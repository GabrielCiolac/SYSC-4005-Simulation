package Utility;

public class Timer {
    private long timeCounted=0L;
    private String name;
    private long currentStartTime = 0L;

    public Timer(String name){
        this.name = name;
    }

    public void startTimer(){
        if(this.currentStartTime != 0L)
            return;
        this.currentStartTime = System.currentTimeMillis();
    }

    public void endTimer(){
        if(currentStartTime == 0L)
            return;
        timeCounted = (System.currentTimeMillis() - currentStartTime) + timeCounted;
        currentStartTime = 0L;
    }

    public void add(long time){
        timeCounted = time + timeCounted;
    }

    public long getTime(){
        return this.timeCounted;
    }
    public String toString(){
        return name+": "+(timeCounted/1000)+"minutes";
    }

}
