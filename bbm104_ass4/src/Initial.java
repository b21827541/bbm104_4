import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import javax.swing.Timer;

public class Initial extends JPanel implements ActionListener{
    private int Speed;
    private int Space;
    private int HEIGHT = 700;
    private int WIDTH = 500;
    private int width = 50;
    private int height = 100;
    private Rectangle ae86;
    private ArrayList<Rectangle> cars;
    private Random rand;
    Timer timer;
    public Initial(){
        rand = new Random();

        cars = new ArrayList<Rectangle>();
        ae86 = new Rectangle(WIDTH/2-90, HEIGHT-100, width, height);
        Space = 100;
        Speed = 2;
        addCars(true);
        addCars(true);
        addCars(true);
        addCars(true);


        timer = new Timer(10, this);
        timer.start();
    }

    public void addCars(boolean first){
        int posx = rand.nextInt()%2;
        int x = 0;
        int y = 0;
        int Width = height;
        int Height = width;
        if(posx == 0)
            x = WIDTH/2-150;
        else
            x = WIDTH/2%10;
        if(first){
            cars.add(new Rectangle(x, y-100-(cars.size()*Space), Width-50, Height+20));
        }else{
            cars.add(new Rectangle (x, cars.get(cars.size()-1).x+Space, Width, Height));
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WIDTH, HEIGHT+20);
        g.setColor(Color.gray);
        g.fillRect(WIDTH/2-150, 0, WIDTH-200, HEIGHT+20);
        g.setColor(Color.red);
        g.fillRect(ae86.x, ae86.y+20, ae86.width, ae86.height+20);
        g.setColor(Color.black);
        g.drawLine(WIDTH/2, 0, WIDTH/2,HEIGHT+20);
        g.setColor(Color.yellow);

        for(Rectangle rect:cars){
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }

    }

    public void actionPerformed(ActionEvent ae){
        Rectangle rect;
        for(int i=0; i < cars.size(); i++){
            rect = cars.get(i);
            rect.y += Speed;
        }
        repaint();
    }

}
