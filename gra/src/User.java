
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class User extends JPanel implements ActionListener {

    Panel flow;

    //jeszcze mi się tu nic nie rysuje, ale to nieważne
    //bo chcę Ci jak najszybciej wysłać szkielet apletu
    //(robiłem projekt, gdzie właśnie tak wywoływałem aplet)
    public User(ModelInterface model) {
        try {

            flow = new Panel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            flow.add(new JLabel("test"));
            setBackground(Color.green);
            
            repaint();    
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    //to ma być do czytania klawiszy
    //i ma uaktualniać tablice stanu klawiszy
    final public void actionPerformed(ActionEvent zdarzenie) {
        try {
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //User ma zmierzyć czas tworzenia ramki (modelu i swój)
    //i według timera wywoływać model i odrysowywać ramkę

}
