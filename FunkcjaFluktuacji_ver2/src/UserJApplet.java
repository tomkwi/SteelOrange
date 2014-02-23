
import javax.swing.JApplet;

public class UserJApplet extends JApplet {

    @Override
    public void init() {
        try {
            String nazwaModelu = getParameter("model");
            User u = new User(Class.forName(nazwaModelu).newInstance());
            setSize(1000, 600);
            add(u);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}
