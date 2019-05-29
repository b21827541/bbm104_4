import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

public class Main extends Application {

    private Pane root = new Pane();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Block> lines = new ArrayList<Block>();
    private ArrayList<Block> enemies = new ArrayList<Block>();
    private double time = 0;
    private Block player = new Block(230, 630, 40, 70, "player", Color.RED);
    private Parent create(){
        root.setPrefSize(500, 720);


        Block grass = new Block(0, 0, 500, 720, "grass", Color.LIGHTGREEN);
        Block road = new Block(100, 0, 300, 720, "road", Color.GRAY);
        for(int i = 0; i<12;i++) {
            addLines(i);
        }
        root.getChildren().addAll(grass, road);
        for(Block b: lines){
            root.getChildren().add(b);
        }
        /*for(int i = 0; i<6;i++) {
            addCircles(i);
        }
        for(Circle c: circles){
            c.setFill(Color.GREEN);
            root.getChildren().add(c);
        }*/
        root.getChildren().addAll(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        addEnemy();
        return root;
    }
    public void addEnemy(){
        for(int i = 0; i < 5; i++){
            int random = ThreadLocalRandom.current().nextInt(-150, 101);
            Block b = new Block(250+random, -(i*180), 30, 50, "enemy", Color.YELLOW);
            root.getChildren().add(b);
            enemies.add(b);


        }


    }
    public void addLines(int i){
        int x1 = 245;
        int Width = 10;
        int Height = 50;
        int Empt = 20;
        Block rec = new Block(x1,i*(Height+Empt)-140,Width,Height, "lines", Color.BLACK);
        lines.add(rec);
    }
    /*public void addCircles(int i){
        int random;
        Circle circ;
        if(i<3)
            random = ThreadLocalRandom.current().nextInt(20,80);
        else
            random = ThreadLocalRandom.current().nextInt(420,480);
        if(i<3)
            circ = new Circle(random, 600-i*200, 20);
        else
            circ = new Circle(random, 600-(i%3*200)+100, 20);
        circles.add(circ);
    }*/
    public List<Block> blocks(){
        return root.getChildren().stream().map(n -> (Block)n).collect(Collectors.toList());

    }
    /*public List<Rectangle> enemies(){
        return root.getChildren().stream().map(n -> (Rectangle)n).collect(Collectors.toList());
    }*/
    public void update(){
        time += 0.01;
        /*enemies().forEach(s -> {
            switch ((int)s.getWidth()){
                case 30:
                    s.setTranslateY(s.getY() + 20);
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                    }
                    break;
            }
        });*/
        for(Block b: lines){
            if(b.getTranslateY()>770) {
                b.setTranslateY(-70);
            }else{
                b.down();
            }
        }
        for(Block b: enemies){
            int random = ThreadLocalRandom.current().nextInt(100, 351);
            if(b.getTranslateY()>770) {
                b.setTranslateX(random);
                b.setTranslateY(-70);
            }else if(time < 15){
                b.down();
            }else if(time < 30){
                b.downFast();
            }else{
                b.downFaster();
            }
        }
        blocks().forEach(s -> {
            switch (s.type){
                case "enemy":
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                        s.dead = true;
                    }
                    break;
                default:
                    break;


            }
        });
        root.getChildren().removeIf(n -> {
            Block b = (Block) n;
            return b.dead;

        });

    }

    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(create());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case A:
                    if(player.getTranslateX() - 10 > 90) {
                        player.left();
                        break;
                    }
                    break;
                case D:
                    if(player.getTranslateX() + 10 < 370) {
                        player.right();
                        break;
                    }
                    break;
            }
        });
        stage.setScene(scene);
        stage.show();
    }
    private static class Bushes extends Circle{

    }

    private static class Block extends Rectangle {
        final String type;
        boolean dead = false;
        Block(int x, int y, int width, int height, String type, Color color){
            super(width, height, color);

            this.type =  type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void left(){
            setTranslateX(getTranslateX() - 10);
        }

        void right(){
            setTranslateX(getTranslateX() + 10);
        }

        void down(){
            setTranslateY(getTranslateY() + 0.5);
        }
        void downFast(){
            setTranslateY(getTranslateY() + 1);
        }
        void downFaster(){
            setTranslateY(getTranslateY() + 1.5);
        }
        void downFastest(){
            setTranslateY(getTranslateY() + 2);
        }
    }












































    public static void main (String[] args) {
        launch(args);
    }
}
