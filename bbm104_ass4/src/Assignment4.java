import javax.swing.*;

public class Assignment4 extends JFrame {

    public static void main(String[] args) {
        JFrame game = new JFrame();
        Initial initiald = new Initial();
        game.add(initiald);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(500, 720);
        game.setVisible(true);
    }
}
