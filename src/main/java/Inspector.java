import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.HashSet;
import java.util.LinkedList;

public class Inspector implements Runnable{
    private LinkedList<Buffer> buffers;
    private HashSet<Component> components;
    private final Timer t = new Timer("Total Time Blocked");

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

        return (Component) components.toArray()[index];
    }

    /**
     * Scheduling Algorithm
     * @return
     */
    private void tryDeposit(Component c){
        int smallestSize = Configuration.BUFFER_CAPACITY;
        int indexOfSmallest = -1;

        for (int i = 0; i < buffers.size(); i++) {
            Buffer b = this.buffers.get(i);
            if (b.getType() != c || b.isDone()){
                continue;
            }
            else if (b.isEmpty()){
                b.put(c);//if buffer empty deposit
                t.endTimer();
                return;
            }
            if (b.getSize() < smallestSize) { //if buffer size is smaller then smallest so far, record index
                    indexOfSmallest = i;
                    smallestSize = b.getSize();
                }
            }
            if(indexOfSmallest != -1){
                t.endTimer();
                Buffer b = this.buffers.get(indexOfSmallest);//adds to smallest buffer
                b.put(c);
                return;
            }
            t.startTimer();
    }

    /**
     * Checks that all the buffers are done
     * @return
     */
    private boolean allDone(){
        for(int i = 0; i < this.buffers.size();i++){
            if(!this.buffers.get(i).isDone())
                return false;
        }
        return true;
    }


    private long getWaitTime(Component c){
        if(c==Component.ONE)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP1_MU,Configuration.SERVINSP1_SIGMA);
        if(c==Component.TWO)
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP22_MU,Configuration.SERVINSP22_SIGMA);
        else
            return (long)Util.get_x_of_log_normal(Configuration.SERVINSP23_MU,Configuration.SERVINSP23_SIGMA);

    }


    @Override
    public void run() {
        while(!allDone()){
            Component c = produceComponent();
            long waitTime = getWaitTime(c);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Util.log("Error Occurred Waiting");
            }

             tryDeposit(c);
        }
        Util.log(t.toString());

    }
}
