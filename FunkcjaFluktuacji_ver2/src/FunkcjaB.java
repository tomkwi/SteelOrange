
import java.util.Random;

class FunkcjaB {

    public int iloscPunktow;
    public double B[];
    public double calka1;
    public double calka2;
    public double delta_t;

    //t - długość odcinka
    //n - 2^n to ilość części odcinka
    //s - współczynnik rozrzutu zmiennej losowej przyrostu B
    //r - posiew generatora rozkladu normalnego
    public FunkcjaB(double t, double n, double s, double r) {
        //podział odcinka [0,t] na 2^n części :
        // t_k = t_0 + k * t/(2^n) =>
        delta_t = t / Math.pow(2.0, n);
        //zmienna losowa U :
        // U~N(esp_U,dev_U) => U=K*dev_U+esp_U, K~N(0,1)
        double esp_U = 0.0;
        double dev_U = Math.sqrt(delta_t) * s;
        iloscPunktow = (int) (Math.pow(2, n));
        Random K = new Random((int) r);
        //funkcja losowa B(t,n,s,r) :
        // 1) B(t[0])=0
        // 2) jeżeli obliczono B(t[0]), B(t[1]),..., B(t[k-1])
        //    wtedy B(t[k])=B(t[k-1])+U    
        B = new double[iloscPunktow + 1];
        B[0] = 0;
        for (int j = 1; j <= iloscPunktow; j++) {
            double U = K.nextGaussian() * dev_U + esp_U;
            B[j] = B[j - 1] + U;
        }
    }

    public void obliczCalke1() {
        calka1 = 0;
        for (int j = 1; j <= iloscPunktow; j++) {
            calka1 += B[j] * B[j];
        }
        calka1 *= delta_t;  //wyciągnięcie stałej przd sumę
    }

    public void obliczCalke2() {
        calka2 = 0;
        double t_jm1 = 0;  // t_jm1 <=> t_j-1
        for (int j = 1; j <= iloscPunktow; j++) {
            calka2 += t_jm1 * B[j - 1] * (B[j] - B[j - 1]);
            t_jm1 = delta_t * j;
        }
    }
}
