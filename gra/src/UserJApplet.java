
import javax.swing.JApplet;

public class UserJApplet extends JApplet {

    @Override
    public void init() {
        try {
            Model model=new Model();
            String fileName = getParameter("fileName");
            String width = getParameter("width");
            String height = getParameter("height");
            User user = new User(model,fileName,width,height);
            setSize(640, 480);
            add(user);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }
}
