import Utility.Configuration;
import Utility.Timer;
import Utility.Util;

import java.util.LinkedList;

public class WorkStation implements Runnable{
    private LinkedList<Buffer> buffers;
    private double mu,sigma;
    private int produced = 0;
    private Timer t = new Timer("Total Production Time For "+Configuration.PRODUCTION_TARGET+" Units");


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
                Thread.yield();
                i = 0;
            }
        }

        for (Buffer buffer : buffers) {
            buffer.get();
        }

        long waitTime = (long) Util.get_x_of_log_normal(this.mu,this.sigma);

        this.t.add(waitTime);

        //Util.log("Produced A Product");
        produced++;

    }


    private void closeBuffers(){
        for(Buffer b: buffers){
            b.get();//encase a thread is blocked on the buffer
            b.setDone();
        }
    }

    @Override
    public void run() {
        t.startTimer();

        while(produced < Configuration.PRODUCTION_TARGET){
            tryProduce();
        }
        closeBuffers();
        t.endTimer();
        Util.log(t.toString());
    }
}
