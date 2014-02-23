
import javax.swing.JFrame;

public class UserJFrame extends JFrame {

    public static void main(String args[]) {
        try {
            Model model=new Model();
            User user = new User(model);
            UserJFrame okno = new UserJFrame();
            okno.setSize(640+8, 480+64);  //8x64 - non client area
            okno.add(user);
            okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
            okno.setVisible(true);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }

    }
}
