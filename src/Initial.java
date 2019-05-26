
import javax.imageio.ImageIO;
import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

public class Initial extends JPanel implements ActionListener, KeyListener{
    private int Speed;
    private int Space;
    private int HEIGHT = 700;
    private int WIDTH = 500;
    private int width = 50;
    private int height = 100;
    private Rectangle ae86;
    private ArrayList<Rectangle> cars;
    Timer timer;
    boolean flag = false;
    private int move = 20;
    private int count = 1;
    //BufferedImage car1;
    //BufferedImage car2;

    public Initial(){
        /*try {
            car1 = ImageIO.read(new File("/home/berk/IdeaProjects/ass_4/src/pics/ourcar3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            car2 = ImageIO.read(new File("/home/berk/IdeaProjects/ass_4/src/pics/them.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        timer = new Timer(20, this);
        cars = new ArrayList<Rectangle>();
        ae86 = new Rectangle(WIDTH/2-90, HEIGHT-80, width+10, height-20);
        Space = 300;
        Speed = 2;
        addKeyListener(this);
        setFocusable(true);
        addCars(true);
        addCars(true);


        timer.start();
    }

    public void addCars(boolean first){
        int randomNum = ThreadLocalRandom.current().nextInt(-150, 100+ 1);
        int x;
        int y = 0;
        int Width = height;
        int Height = width;
        x = WIDTH/2+randomNum;

        if(first){
            cars.add(new Rectangle(x, y-100-(cars.size()*Space), Width-50, Height+20));
        }else{
            cars.add(new Rectangle (x, cars.get(cars.size()-1).y-400, Width-50, Height+20));
        }
    }

    public void paint(Graphics g){
        super.paintComponents(g);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WIDTH, HEIGHT+20);
        g.setColor(Color.gray);
        g.fillRect(WIDTH/2-150, 0, WIDTH-200, HEIGHT+20);
        if (flag)
            g.setColor(Color.black);
        else
            g.setColor(Color.red);

        g.fillRect((int)ae86.getX(), (int)ae86.getY(), (int)ae86.getWidth(), (int) ae86.getHeight()+20);

        g.setColor(Color.black);
        g.drawLine(WIDTH/2, 0, WIDTH/2,HEIGHT+20);
        if(flag) {
            g.setColor(Color.black);
            timer.stop();
        }else{
            g.setColor(Color.yellow);
        }

        for(Rectangle rect:cars){
            if(!flag)
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
            else
                g.fillRect(rect.x, rect.y, rect.width, rect.height);

        }
    }

    public void actionPerformed(ActionEvent ae){
        Rectangle rect;
        count ++;
        for(int i=0; i < cars.size(); i++){
            rect = cars.get(i);
            if(count%1000 == 0) {
                Speed++;
                if(move < 50)
                    move += 10;
            }
            rect.y += Speed;
        }
        // cars crash
        for(Rectangle r: cars){
            if(r.intersects(ae86)){

                flag=true;
                timer.stop();
            }
        }
        for(int i = 0; i<cars.size(); i++){
            rect = cars.get(i);
            if(rect.y + rect.height > HEIGHT){
                cars.remove(rect);
                addCars(false);
            }
        }
        repaint();
    }
    public void accelerate(){
        if(ae86.y-move>0){
            ae86.y -= move;
        }
    }
    public void decelerate(){
        if(ae86.y+move+ae86.height<HEIGHT-1){
            ae86.y += move;
        }
    }
    public void left(){
        if(ae86.x - move > WIDTH/2-160){
            ae86.x -= move;
        }
    }
    public void right(){
        if(ae86.x + move < HEIGHT/2+10){
            ae86.x += move;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int button = e.getKeyCode();
        switch (button){
            case KeyEvent.VK_UP:
                accelerate();
                break;
            case KeyEvent.VK_DOWN:
                decelerate();
                break;
            case KeyEvent.VK_LEFT:
                left();
                break;
            case KeyEvent.VK_RIGHT:
                right();
                break;
            default:
                break;
        }
    }
}
