import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.LinkedList;

public class WorkStation{
    private LinkedList<Buffer> buffers;
    private double mu,sigma;
    private int produced = 0;
    private Timer t = new Timer("Total Production Time For "+Configuration.PRODUCTION_TARGET+" Units");
    private boolean done = false;
    private boolean producing = false;


    public WorkStation(double mu, double sigma){
        this.buffers = new LinkedList<Buffer>();
        this.mu = mu;
        this.sigma = sigma;

    }

    public void addBuffer(Buffer b){
        buffers.add(b);
    }

    private boolean tryProduce(){
        for(int i = 0; i < buffers.size();i++){
            if(buffers.get(i).isEmpty()){
                return false;
            }
        }
        long time = (long) Util.get_x_of_log_normal(this.mu,this.sigma);
        t.waitFor(time);
        return true;
    }

    private void produce(){
        for (Buffer buffer : buffers) {
            buffer.get();
        }
        producing = false;
        produced++;
        System.out.println("Produced: "+produced);
    }


    private void closeBuffers(){
        if(this.produced <= Configuration.PRODUCTION_TARGET){
            return;
        }
        for(Buffer b: buffers){
            b.get();//encase a thread is blocked on the buffer
            b.setDone();
        }
        this.done = true;
    }

    public void print(){
        System.out.println(t.toString());
    }

    public void dutyCycle() {
        this.closeBuffers();
        if(!done){
            t.add(1L);
        }
        else{
            return;
        }
        if(!producing){
           producing = tryProduce();
           return;
        }
        else if(!t.waiting()){
            this.produce();
        }

    }
}
