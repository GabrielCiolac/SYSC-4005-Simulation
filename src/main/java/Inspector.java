import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.HashSet;
import java.util.LinkedList;

public class Inspector implements Runnable{
    private LinkedList<Buffer> buffers;
    private HashSet<Component> components;
    private Timer t = new Timer("Total Time Blocked");

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

        int index = (int) (Math.random() * (components.size() - 0));

        return (Component) components.toArray()[index];
    }

    /**
     * Scheduling Algorithm
     * @return
     */
    private void deposit(Component c){
        int smallestSize = 3;
        int indexOfSmallest = 0;

        while(smallestSize != 3) { //spin until you find a buffer
            for (int i = 0; i < buffers.size(); i++) {
                Buffer b = this.buffers.get(i);
                if (b.getType() != c || b.isDone()){
                    continue;//if buffer is not of type of component, ignore
                }
                else if (b.isEmpty()){
                    b.put(c);//if buffer empty deposit
                }



                if (b.getSize() < smallestSize) { //if buffer size is smaller then smallest so far, record index
                    indexOfSmallest = i;
                    smallestSize = b.getSize();
                }
            }
            t.startTimer(); //only start the timer if blocked
            Util.log("Blocked");
        }
        t.endTimer();
        Buffer b = this.buffers.get(indexOfSmallest);//adds to smallest buffer
        b.put(c);
    }

    /**
     * Checks that all the buffers are done
     * @return
     */
    private boolean allDone(){
        for(int i = 0; i < buffers.size();i++){
            if(!buffers.get(i).isDone())
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

             deposit(c);
        }
        Util.log(t.toString());

    }
}
