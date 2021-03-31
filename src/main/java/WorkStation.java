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


    public WorkStation(double mu, double sigma){
        this.buffers = new LinkedList<Buffer>();
        this.mu = mu;
        this.sigma = sigma;

    }

    public void addBuffer(Buffer b){
        buffers.add(b);
    }

    private void tryProduce(){
        for(int i = 0; i < buffers.size();i++){
            if(buffers.get(i).isEmpty()){
                return;
            }
        }

        for (Buffer buffer : buffers) {
            buffer.get();
        }

        t.waitFor((long) Util.get_x_of_log_normal(this.mu,this.sigma));
        produced++;
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
        t.startTimer();
        if(!done && !t.waiting()){
            this.tryProduce();
            this.closeBuffers();
        }
        if(done){
            t.endTimer();
        }

    }
}
