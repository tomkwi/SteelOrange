
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class User extends JPanel implements ActionListener {

    Panel flow;
    Model model;

    public User(Model model) {
        try {

            this.model = model;

            flow = new Panel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            flow.add(new JLabel("test"));
            setBackground(Color.green);

            repaint();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    final public void actionPerformed(ActionEvent zdarzenie) {
        try {
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
