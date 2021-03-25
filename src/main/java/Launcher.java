import Utility.*;

public class Launcher {

    public static void main(String args[]){
        Thread ws1,ws2,ws3,servinsp1,servinsp2;

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


        ws1 = new Thread(w1,"WorkStation1");
        ws2 = new Thread(w2,"WorkStation2");
        ws3 = new Thread(w3,"WorkStation3");

        servinsp1 = new Thread(insp1,"Inspector1");
        servinsp2 = new Thread(insp2,"Inspector2");

        ws1.start();
        ws2.start();
        ws3.start();
        servinsp1.start();
        servinsp2.start();






    }
}
