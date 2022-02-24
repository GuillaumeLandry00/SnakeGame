import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(){

       add(new GamePanel());
        setTitle("Snake game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
