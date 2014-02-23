
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

//Szkic uniwersalnej klasy do prezentowania modelu matematycznego.
//Udostępnia użytkownikowi publiczne pola i metody obiektu.
//Pola double i metody wyświetla we FlowLayout.
//Poniżej wyświetla pole BufferedImage.
//Założenia: pole BufferedImage jest ostatnie
public class User extends JPanel implements ActionListener {

    MyComponent mc;
    JTextField par[];
    JLabel lab[];
    JButton but[];
    Field fieldlist[];
    Method methlist[];
    Object object;
    int imgs[], imgsCount;
    int width, height;
    Panel flow;

    public User(Object object) {
        super(new BorderLayout());
        try {

            setBackground(Color.red);
            this.object = object;
            Class cls = object.getClass();
            fieldlist = cls.getDeclaredFields();
            methlist = cls.getDeclaredMethods();

            int attributesCount = fieldlist.length + methlist.length;
            par = new JTextField[attributesCount];
            lab = new JLabel[attributesCount];
            but = new JButton[attributesCount];
            imgs = new int[attributesCount];
            imgsCount = 0;

            flow = new Panel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            flow.add(new JLabel("                                                    "));
            //IE rozbija układ apletu, musiałem wyrównać do lewej

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat formatter = new DecimalFormat("#.###");
            formatter.setDecimalFormatSymbols(symbols);

            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                if (fld.getType() == int.class) {
                    if (fld.getName().equals("clientWidth")) {
                        width = fld.getInt(object);
                    }
                    if (fld.getName().equals("clientHeight")) {
                        height = fld.getInt(object);
                    }
                }
                if (!Modifier.isPublic(fld.getModifiers())) {
                    continue;
                }
                if (fld.getType() == double.class) {
                    lab[i] = new JLabel("   " + fld.getName() + " ");
                    flow.add(lab[i]);

                    String str = formatter.format(fld.getDouble(object));
                    par[i] = new JTextField(str, 5);

                    flow.add(par[i]);
                }
                if (fld.getType() == BufferedImage.class) {
                    imgs[imgsCount] = i;
                    imgsCount++;
                }
            }

            flow.add(new JLabel("       "));  //odstęp przed przyciskiem

            for (int i = 0; i < methlist.length; i++) {
                Method m = methlist[i];
                if (!Modifier.isPublic(m.getModifiers())) {
                    continue;
                }
                but[i] = new JButton(m.getName());
                but[i].addActionListener(this);
                flow.add(but[i]);
            }

            mc = new MyComponent();
            setBackground(Color.white);
            add(flow, BorderLayout.NORTH);
            add(mc, BorderLayout.CENTER);

            setSize(width, height);
            setBackground(Color.white);

            methlist[0].invoke(object);

            mc.repaint();
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }

    class MyComponent extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            try {
                for (int i = 0; i < imgsCount; i++) {
                    g.drawImage(
                            (BufferedImage) (fieldlist[imgs[i]].get(object)), 0, 0, this);
                }
            } catch (Exception ex) {
                System.out.println("EXCEPTION " + ex);
            }
        }
    }

    @Override
    final public void actionPerformed(ActionEvent zdarzenie) {
        try {
            for (int i = 0; i < fieldlist.length; i++) {
                if (fieldlist[i].getType() == double.class
                        && Modifier.isPublic(fieldlist[i].getModifiers())) {
                    fieldlist[i].setDouble(object, Double.parseDouble(par[i].getText()));
                }
            }
            methlist[0].invoke(object);
            new Thread(new ProgressObserver()).start();
        } catch (Exception ex) {
            System.out.println("EXCEPTION " + ex);
        }
    }

    class ProgressObserver implements Runnable {

        public void run() {
            try {
                int progress = 0;
                while (progress < 100) {
                    progress = object.getClass().getField("progress").getInt(object);
                    but[0].setText(String.valueOf(progress));  //nie wyświetla się
                    Thread.sleep(1);
                }
                but[0].setText(methlist[0].getName());
                mc.repaint();
            } catch (Exception ex) {
                System.out.println("EXCEPTION " + ex);
            }
        }
    }
}
