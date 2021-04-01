import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.LinkedList;

/**
 * Consumer
 */
public class WorkStation{
    private LinkedList<Buffer> buffers;
    private double mu,sigma;
    private int produced = 0;
    private Timer t = new Timer("Total Production Time For "+Configuration.PRODUCTION_TARGET+" Units");
    private boolean producing = false;


    public WorkStation(double mu, double sigma){
        this.buffers = new LinkedList<Buffer>();
        this.mu = mu;
        this.sigma = sigma;

    }

    /**
     * Adds a buffer to the workstation
     * @param b
     */
    public void addBuffer(Buffer b){
        buffers.add(b);
    }

    /**
     * Checks if the workstation can produce by seeing that each buffer has something in it
     * @return
     */
    private boolean canProduce(){
        for(Buffer b: this.buffers){
            if(b.isEmpty())
                return false; //if one of the buffers is empty return false
        }
        producing = true; //if none of the buffers are empty set the producing flag
        long wait = (long)Util.get_x_of_log_normal(this.mu,this.sigma);
        t.waitFor(wait); //add the wait time
        return true; //return true
    }

    /**
     * Produces by removing a component from each buffer
     */
    private void produce(){
        for (Buffer buffer : buffers)
            buffer.get();
        produced++;//adds to production count
        producing = false;//unsets producing flag
        tryClosing();//tries to flag buffers as done
    }


    /**
     * This tries to flag the buffers as done
     */
    private void tryClosing(){
        if(this.produced < Configuration.PRODUCTION_TARGET )
            return; //if you are bellow the production target return
        for(Buffer b: buffers)
            b.setDone(); //otherwise mark each buffer as done
    }

    /**
     * Prints statistics
     */
    public void print(){
        double throughput = t.getTimeCounted()/produced;
        System.out.println("Throughput "+throughput+" ticks/unit");
    }

    /**
     * Called by the launcher on every tick
     */
    public void dutyCycle() {
        this.t.add(1L); //add 1 second to production time

        if(!producing)//if not producing
            canProduce(); //check if you can produce
        else if(!this.t.waiting())//if producing and not waiting
            produce();
    }
}
