
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class ModelFunkcjaBCalka1 {

    int width = 1000;
    int height = 600;
    public int progress = 0;
    public double t = 4;
    public double n = 5;
    public double s = 1;
    public double przedzial = 0.1;
    public double zakres = 50;
    public double losowania = 100000;
    public double skala = 0.2;
    public BufferedImage drawing;

    public void rysuj() {
        try {

            int histogram[];
            int maxHistX = (int) (zakres / przedzial);
            histogram = new int[maxHistX];
            for (int i = 0; i < maxHistX; i++) {
                histogram[i] = 0;
            }

            double sumaCalek = 0;
            int przyciete = 0;
            for (int i = 0; i < losowania; i++) {
                FunkcjaB fb = new FunkcjaB(t, n, s, i);
                fb.obliczCalke1();
                sumaCalek += fb.calka1;
                int ind = (int) (fb.calka1 / przedzial);
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
                    0, zakres, 0, skala,
                    "wartość całki", "histogram rozkładu",
                    zakres / 20, skala / 10,
                    Color.white, Color.black, Color.gray);

            int graniceEkranowe[] = new int[maxHistX];
            double slupki[] = new double[maxHistX];
            for (int i = 0; i < maxHistX; i++) {
                graniceEkranowe[i] = g.transformX(i * przedzial);
            }
            g.setColor(Color.blue);
            for (int i = 0; i < maxHistX - 1; i++) {
                slupki[i] = histogram[i] / (losowania * przedzial);
                g.gr.fillRect(graniceEkranowe[i], g.transformY(slupki[i]),
                        graniceEkranowe[i + 1] - graniceEkranowe[i],
                        (int) (g.scaleY * slupki[i]));
            }

            double sredniaH = 0;
            for (int i = 0; i < maxHistX - 1; i++) {
                sredniaH += slupki[i] * i * przedzial;
            }

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat formatter = new DecimalFormat("#.###");
            formatter.setDecimalFormatSymbols(symbols);
            String pozaZakresem = formatter.format(przyciete / losowania * 100);
            String sredniaCalki = formatter.format(sumaCalek / losowania);
            String sredniaHistogramu = formatter.format(sredniaH);
            g.drawComment("średnia całki: " + sredniaCalki + ", "
                    + "poza zakresem: " + pozaZakresem + "%, "
                    + "średnia histogramu: " + sredniaHistogramu);
            progress = 100;

        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}
