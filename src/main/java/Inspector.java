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
    private int componentsProduced = 0;

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
        int smallestSize = Configuration.BUFFER_CAPACITY;
        int indexOfSmallest = -1;

        for (int i = 0; i < buffers.size(); i++) {
            Buffer b = this.buffers.get(i);
            if (b.getType() != c || b.isDone()){
                continue;
            }
            else if (b.isEmpty()){
                b.put(c);//if buffer empty deposit
                return true;
            }
            if (!b.isFull() && b.getSize() < smallestSize) { //if buffer size is smaller then smallest so far, record index
                    indexOfSmallest = i;
                    smallestSize = b.getSize();
            }
        }
        if(indexOfSmallest != -1){
            Buffer b = this.buffers.get(indexOfSmallest);//adds to smallest buffer
            b.put(c);
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

    public boolean allDone(){
        for(Buffer b: buffers)
            if(!b.isDone())
                return false;
        return true;
    }
    public void dutyCycle() {
        if(allDone())
            return;
        else{
            this.producing.add(1L);
        }

        if(this.inHand == null){
            this.inHand = produceComponent();
            this.waitForProduce(inHand);
        }

        if(!this.producing.waiting()){
            if(!tryDeposit(inHand)){
                this.blocked.add(1L);
            }
            else{
                inHand = null;
                componentsProduced++;
            }
        }
    }
}
