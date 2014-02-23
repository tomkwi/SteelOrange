
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

class ModelRozkladNormalny {

    int width = 1000;
    int height = 600;
    public int progress = 0;
    public double generator = 1;
    public double przedzial = 0.02;
    public double zakres = 3;
    public double losowania = 100000;
    public BufferedImage drawing;

    public void rysuj() {
        try {

            String generatorName = "";
            switch ((int) generator) {

                case 1:
                    generatorName = "metoda_odwroconej_dystrybuanty";
                    break;
                case 2:
                    generatorName = "metoda_Boxa_Mullera";
                    break;
            }
            RozkladNormalny rn = new RozkladNormalny();
            Method m = RozkladNormalny.class.getMethod(generatorName);

            int histogram[];
            int maxHistX = (int) ((zakres * 2) / przedzial) + 2;
            histogram = new int[maxHistX];
            for (int i = 0; i < maxHistX; i++) {
                histogram[i] = 0;
            }
            int przyciete = 0;
            for (int i = 0; i < losowania; i++) {
                double rand=((Double) (m.invoke(rn))).doubleValue();
                int ind = (int) ((rand + zakres) / przedzial);
                if (ind < 0 || ind >= maxHistX) {
                    przyciete++;
                    continue;
                }
                histogram[ind]++;
                progress = (int) ((i / losowania) * 110) + 5;
            }

            drawing = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
            progress = 0;
            Wykres2D g = new Wykres2D(drawing,
                    -zakres, zakres, 0, 1,
                    generatorName, "ilość wyników / (losowania * przedział)",
                    0.5, 0.1,
                    Color.white, Color.black, Color.gray);
            
            //stablicowanie granic słupków
            int granice[]=new int[(int)(zakres*2/przedzial+2)];
            for (double i = -zakres; i < zakres; i += przedzial) {
                granice[(int) ((i + zakres) / przedzial)]=g.transformX(i);
            }

            g.setColor(Color.blue);
            for (double i = -zakres; i < zakres; i += przedzial) {
                int ind = (int) ((i + zakres) / przedzial);
                int h = histogram[ind];
                double slupek = h / (losowania * przedzial);
                g.gr.fillRect(granice[ind], g.transformY(slupek), granice[ind+1]-granice[ind], (int)(g.scaleY*slupek));
            }
            g.drawComment("poza zakresem " + String.valueOf(przyciete / losowania * 100) + "%");
            progress = 100;

        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}