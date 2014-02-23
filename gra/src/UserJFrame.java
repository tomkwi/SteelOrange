
import javax.swing.JFrame;

public class UserJFrame extends JFrame {

    public static void main(String args[]) {
        try {

            //parametry apletu
            Integer width=640;
            Integer height=480;
            String fileName="mapa_1.gif";

            Model model=new Model();
            User user = new User(model,fileName,width.toString(),height.toString());
            UserJFrame okno = new UserJFrame();
            okno.setSize(width+8, height+64);  //8x64 - non client area
            okno.add(user);
            okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
            okno.setVisible(true);
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }

    }
}
