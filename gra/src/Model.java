
import java.awt.image.BufferedImage;

public class Model {

    boolean[] keys;  //qwerasdf
    double[] ballPosition; //x,y,alfa [metry,radiany]
    BufferedImage plansza;
    double frameDuration;  //długość kroku czasowego symulacji [sekundy]

    public Model(String map) {
        keys = new boolean[8]; 
        ballPosition = new double[2 + 1];  
        // wczytanie mapy 
    }
    
    public void setFrameDuration(double frameDuration) {
        this.frameDuration = frameDuration;
    }

    public double[] nextFrame(boolean[] keys) {
        this.keys = keys;
        // wyznaczenie nowego położenia
        return ballPosition;
    }
}
