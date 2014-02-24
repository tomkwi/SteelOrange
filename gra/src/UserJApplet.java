
import javax.swing.JApplet;

public class UserJApplet extends JApplet {

    @Override
    public void init() {
        try {
            Model model = new Model(getParameter("map"));
            User user = new User(model);
            setSize(640, 480);
            add(user);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}
