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

    private boolean canProduce(){
        for(Buffer b: this.buffers){
            if(b.isEmpty())
                return false;
        }
        producing = true;
        t.waitFor((long)Util.get_x_of_log_normal(this.mu,this.sigma));
        return true;
    }

    private void produce(){
        for (Buffer buffer : buffers) {
            buffer.get();
        }
        produced++;
        producing = false;
        tryClosing();
    }


    private void tryClosing(){
        if(this.produced < Configuration.PRODUCTION_TARGET ){
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
        if(done){
            return;
        }
        else{
            this.t.add(1L);
        }
        if(!producing){
            canProduce();
            return;
        }
        else if(producing && !this.t.waiting()){
            produce();
        }


    }
}
