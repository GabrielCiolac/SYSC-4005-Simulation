import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.HashSet;
import java.util.LinkedList;

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

    public void addBuffer(Buffer b){
        buffers.add(b);
        components.add(b.getType());
    }

    private Component produceComponent(){
        if(buffers.size() == 0)
            return null;

        int index = (int) (Math.random() * components.size());
        Component c = (Component) components.toArray()[index];

        for(Buffer b: buffers){
            if(b.getType() == c && !b.isDone())
                return c;
        }
        components.remove(c);
        return produceComponent();
    }

    /**
     * Scheduling Algorithm
     * @return
     */
    private boolean tryDeposit(Component c){
        Buffer canidate = null;
        for(Buffer b: this.buffers){
            if(b.isDone()){
                buffers.remove(b);
                return false;
            }
            else if(b.isFull()){
                continue;
            }
            else if(b.isEmpty()){
                b.put(c);
                return true;
            }
            else if(canidate == null && b.getSize() == 1){
                canidate = b;
            }
        }
        if(canidate != null){
            canidate.put(c);
            return true;
        }

        return false;
    }


    private long getWaitTime(Component c){
        if(c==Component.ONE)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP1_MU,Configuration.SERVINSP1_SIGMA);
        if(c==Component.TWO)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP22_MU,Configuration.SERVINSP22_SIGMA);
        else
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP23_MU,Configuration.SERVINSP23_SIGMA);

    }


    private void waitForProduce(Component c){
        long waitTime = getWaitTime(c);
        this.producing.waitFor(waitTime);
    }

    public void print(){
        double percentage = (this.blocked.getTimeCounted()/(this.producing.getTimeCounted()))*100;
        System.out.println("Percentage of time spent blocked: "+percentage+"%");
    }

    public void isDone(){
        for(Buffer b: buffers)
            if(!b.isDone())
                return;
        done = true;
    }

    public void dutyCycle() {
        if(done)
            return;
        this.producing.add(1L);
        if(inHand == null) {
            inHand = produceComponent();
            waitForProduce(inHand);
            return; //started producing in this tick, can return
        }
        if(!this.producing.waiting()){
            if(tryDeposit(inHand)){
                inHand = null;
                isDone();
            }
            else{
                this.blocked.add(1L);
            }
        }
    }
}
