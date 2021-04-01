import Utility.*;

public class Launcher {

    public static void main(String args[]){

        Buffer ws1c1,ws2c1,ws2c2,ws3c1,ws3c3;

        ws1c1 = new Buffer(Component.ONE);
        ws2c1 = new Buffer(Component.ONE);
        ws3c1 = new Buffer(Component.ONE);

        ws2c2 = new Buffer(Component.TWO);
        ws3c3 = new Buffer(Component.THREE);

        Inspector insp1 = new Inspector();
        Inspector insp2 = new Inspector();
        insp1.addBuffer(ws1c1);
        insp1.addBuffer(ws2c1);
        insp1.addBuffer(ws3c1);
        insp2.addBuffer(ws2c2);
        insp2.addBuffer(ws3c3);

        WorkStation w1 = new WorkStation(Configuration.WS1_MU,Configuration.WS1_SIGMA);
        WorkStation w2 = new WorkStation(Configuration.WS2_MU,Configuration.WS2_SIGMA);
        WorkStation w3 = new WorkStation(Configuration.WS3_MU,Configuration.WS3_SIGMA);
        w1.addBuffer(ws1c1);
        w2.addBuffer(ws2c1);
        w3.addBuffer(ws3c1);
        w2.addBuffer(ws2c2);
        w3.addBuffer(ws3c3);


        while(!ws1c1.isDone() || !ws2c1.isDone() || !ws3c1.isDone()){
            w1.dutyCycle();
            w2.dutyCycle();
            w3.dutyCycle();
            insp1.dutyCycle();
            insp2.dutyCycle();
            Configuration.clock++;

        }

        long clock = Configuration.clock;
        System.out.print("INSP1 ");
        insp1.print();
        System.out.print("INSP2 ");
        insp2.print();
        System.out.print("WS1 ");
        w1.print();
        System.out.print("WS2 ");
        w2.print();
        System.out.print("WS3 ");
        w3.print();


    }
}
