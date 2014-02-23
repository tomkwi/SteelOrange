
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

class ModelFunkcjaBCalka2 {

    int width = 1000;
    int height = 600;
    public int progress = 0;
    public double t = 4;
    public double n = 5;
    public double s = 1;
    public double przedzial = 0.1;
    public double zakres = 10;
    public double losowania = 100000;
    public double skala=0.3;
    public BufferedImage drawing;
    
    public void rysuj() {
        try {

            int histogram[];
            int maxHistX = (int) ((zakres * 2) / przedzial) + 2;
            histogram = new int[maxHistX];
            for (int i = 0; i < maxHistX; i++) {
                histogram[i] = 0;
            }

            double sumaCalek = 0;
            int przyciete = 0;
            for (int i = 0; i < losowania; i++) {
                FunkcjaB fb = new FunkcjaB(t, n, s, i);
                fb.obliczCalke2();
                sumaCalek += fb.calka2;
                int ind = (int) ((fb.calka2+zakres) / przedzial);
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
                    -zakres, zakres, 0, skala,
                    "wartość całki 2 funkcji B", "ilość wyników / (losowania * przedział)",
                    (zakres*2)/20,
                    skala/10,
                    Color.white, Color.black, Color.gray);

            int granice[]=new int[maxHistX];
            for (int i = 0; i < maxHistX; i++) {
                granice[i]= g.transformX(i*przedzial-zakres);
            }

            g.setColor(Color.blue);
            for (int i=0; i<maxHistX-1; i++) {
                int h = histogram[i];
                double slupek = h / (losowania * przedzial);
                g.gr.fillRect(granice[i], g.transformY(slupek), granice[i+1]-granice[i], (int)(g.scaleY*slupek));
            }

            DecimalFormat formatter = new DecimalFormat("#.###");
            String pozaZakresem = formatter.format(przyciete / losowania * 100);
            String sredniaCalki = formatter.format(sumaCalek / losowania);
            g.drawComment("średnia " + sredniaCalki + ", "
                    + "poza zakresem " + pozaZakresem + "%");
            progress = 100;

        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}