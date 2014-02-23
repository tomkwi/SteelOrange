
import javax.swing.JApplet;

//aplet jest wywoływany w  gra\build\classes\gra.htm
//na razie działa mi tylko w InternetExplorerze (coś mi się zacieło z javą w firefoxie)
//ten fragment kodu html i pliki class trzeba wkopiować do twojego projektu jsp

//skoro masz jsp to będzie dostępna zmienna opisująca wybór planszy (m.in)
//i trzeba będzie te parametry podać do apletu poprzez parametr jsp

//miałaś fajny pomysł żeby interfejs zrobić jako www

public class UserJApplet extends JApplet {

    @Override
    public void init() {
        try {
            Model model=new Model();
            User user = new User(model);
            setSize(640, 480);
            add(user);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}
