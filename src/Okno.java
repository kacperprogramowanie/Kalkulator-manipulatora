import javax.swing.*;
import java.awt.*;

public class Okno extends JFrame {

    Widok panel = new Widok();
    ImageIcon icon = new ImageIcon("icon.png");

    public Okno() {
        setTitle("Kalkulator manipulatora");
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setSize(800, 600);
        setIconImage(icon.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
