
import java.awt.Color;
import java.awt.image.BufferedImage;

//ma pola i metody, których używa user
public class ModelWykres2DTest {

    public double x1 = 0, x2 = Math.PI * 6, y1 = -1.2, y2 = 1.2,
            unitX = Math.PI / 2, unitY = 0.25, iter = 2000;
    public int clientWidth = 800, clientHeight = 600;
    public BufferedImage bi;
    public int progress;

    ModelWykres2DTest() {
    }

    public void rysuj() {

        //wykres
        bi = new BufferedImage(clientWidth, clientHeight, BufferedImage.TYPE_INT_BGR);
        //double unitX=(x2-x1)/10,unitY=(y2-y1)/10;
        Wykres2D uk = new Wykres2D(bi, x1, x2, y1, y2,
                "x", "sin(x)",
                unitX, unitY,
                Color.white, Color.black, Color.gray);
//        uk.setColor(Color.red);
//        uk.drawLine(x1, y1, x2, y1);
//        uk.drawLine(x1, y1, x1, y2);
//        uk.drawLine(x1, y2, x2, y2);
//        uk.drawLine(x2, y2, x2, y1);
//        uk.setColor(Color.green);
//        uk.drawLine(x1, 0, x2, 0);
//        uk.drawLine(0, y2, 0, y1);
        uk.setColor(Color.green);
        progress = 0;
        for (double x = x1; x < x2; x += (x2 - x1) / iter) {
            uk.drawPixel(x, Math.sin(x));
            progress = (int) ((x2 / x) * 100);
        }
        progress = 100;
    }
}
