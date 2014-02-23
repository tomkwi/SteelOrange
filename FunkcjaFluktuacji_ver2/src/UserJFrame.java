
import java.awt.Color;
import javax.swing.JFrame;

public class UserJFrame extends JFrame {

    public static void main(String args[]) {
        try {
            String nazwaModelu = args[0];
            User u = new User(Class.forName(nazwaModelu).newInstance());
            UserJFrame okno = new UserJFrame();
            //okno.setBackground(Color.red);
            okno.setSize(1000+8, 600+64);
            okno.add(u);
            okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
            okno.setVisible(true);
            //okno.invalidate();
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }

    }
}
