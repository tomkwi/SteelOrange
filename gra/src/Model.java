

import java.awt.image.BufferedImage;

public class Model implements ModelInterface {

    boolean[] keys;
    double[] ballPosition;
    String fileName;
    BufferedImage plansza;

    public Model() {
        keys = new boolean[8]; //stan qwerasdf
        ballPosition = new double[2+1];  //współrzędne x,y,a a-kąt
        fileName = "";
        plansza = null;
    }
    
    @Override
    public void writeKeys(boolean[] keys) {
        this.keys=keys;
    }

    @Override
    public double[] readBallPosition() {
        return ballPosition;
    }

    @Override
    public void setBoardFile(String fileName) {
        this.fileName=fileName;
    }
    
    @Override
    public void nextFrame() {
        if (plansza==null)
            return;
    }    
    
}
