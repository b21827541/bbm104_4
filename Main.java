import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

public class Main extends Application {

    private Pane root = new Pane();
    private boolean gameover = false;
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Block> lines = new ArrayList<Block>();
    private ArrayList<Block> enemies = new ArrayList<Block>();
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Block> decoration = new ArrayList<Block>();
    private int score1 = 0;
    private int score2 = 0;
    private int level = 1;
    private int time = 0;
    private Block player = new Block(230, 630, 40, 70, "player", Color.RED);
    private Parent create(){
        root.setPrefSize(500, 720);
        Block grass = new Block(0, 0, 500, 720, "grass", Color.GREEN);
        Block road = new Block(100, 0, 300, 720, "road", Color.GRAY);
        for(int i = 0; i<12;i++) {
            addLines(i);
        }
        root.getChildren().addAll(grass, road);
        for(Block b: lines){
            root.getChildren().add(b);
        }
        for(int i = 0; i<6;i++) {
            addCircles(i);
        }
        for(Circle c: circles){
            c.setFill(Color.DARKGREEN);
            root.getChildren().add(c);
        }
        root.getChildren().addAll(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.start();
        addDecoration();
        for(Block b: decoration){
            root.getChildren().add(b);
        }
        addEnemy();
        return root;
    }
    public void addEnemy(){
        for(int i = 0; i < 5; i++){
            int random = ThreadLocalRandom.current().nextInt(-150, 101);
            Block b = new Block(250+random, -(i*180), 30, 50, "enemy", Color.YELLOW);
            root.getChildren().add(b);
            enemies.add(b);
            blocks.add(b);
        }
    }
    public void addDecoration(){
        int random;
        Block b;
        for(int i = 0; i < 10; i++){

            if(i<5)
                random = ThreadLocalRandom.current().nextInt(20,80);
            else
                random = ThreadLocalRandom.current().nextInt(420,480);
            if(i<5)
                b = new Block(random, 100- i*200, 10,10,"decoration", Color.LIGHTGRAY);
            else
                b = new Block(random, -i%5*200,10,10,"decoration", Color.LIGHTGRAY);
            decoration.add(b);
        }
    }
    public void addLines(int i){
        int x1 = 245;
        int Width = 10;
        int Height = 50;
        int Empt = 20;
        Block rec = new Block(x1,i*(Height+Empt)-140,Width,Height, "lines", Color.BLACK);
        lines.add(rec);
        blocks.add(rec);
    }
    public void addCircles(int i){
        int random;
        Circle circ;

        if(i<3)
            random = ThreadLocalRandom.current().nextInt(20,80);
        else
            random = ThreadLocalRandom.current().nextInt(420,480);
        if(i<3)
            circ = new Circle(random, 600-i*200, 20);
        else
            circ = new Circle(random, 600-(i%3*200)-100, 20);
        circles.add(circ);
    }
    /*public List<Block> blocks(){
        return root.getChildren().stream().map(n -> (Block)n).collect(Collectors.toList());

    }*/
    /*public List<Rectangle> enemies(){
        return root.getChildren().stream().map(n -> (Rectangle)n).collect(Collectors.toList());
    }*/
    public void update() throws Exception {
        time += 0.16;
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
        Text lvl = new Text();
        Text score = new Text();
        if(!gameover) {
            lvl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            lvl.setFill(Color.WHITE);
            lvl.setStrokeWidth(1);
            lvl.setStroke(Color.LIGHTGRAY);
            lvl.setText("Level " + level);
            lvl.setX(10);
            lvl.setY(50);
            root.getChildren().add(lvl);
            score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            score.setFill(Color.WHITE);
            score.setStrokeWidth(1);
            score.setStroke(Color.LIGHTGRAY);
            score.setText("Score " + score2);
            score.setX(10);
            score.setY(70);
            root.getChildren().add(score);

        }
        if(gameover) {
            root.getChildren().remove(lvl);
            Text lvlend = new Text();
            lvlend.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
            lvlend.setFill(Color.RED);
            lvlend.setStrokeWidth(1);
            lvlend.setStroke(Color.BLACK);
            lvlend.setText("Level " + level);
            lvlend.setX(100);
            lvlend.setY(300);
            Text scoreEnd = new Text();
            scoreEnd.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
            scoreEnd.setFill(Color.RED);
            scoreEnd.setStrokeWidth(1);
            scoreEnd.setStroke(Color.BLACK);
            scoreEnd.setText("Score " + score2);
            scoreEnd.setX(270);
            scoreEnd.setY(300);
            Block endscreen = new Block(0,0,500,720, "end", Color.GRAY);
            Text text = new Text();
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            text.setFill(Color.RED);
            text.setStrokeWidth(2);
            text.setStroke(Color.BLACK);
            text.setText("Game Over");
            text.setX(95);
            text.setY(260);
            endscreen.setOpacity(0.02);
            root.getChildren().addAll(endscreen,text,lvlend,scoreEnd);

        }
        for(Circle c: circles){
            int random = ThreadLocalRandom.current().nextInt(0, 2);
            int random1 ;

            if(random == 0)
                random1 =  ThreadLocalRandom.current().nextInt(40, 61);
            else
                random1 = ThreadLocalRandom.current().nextInt(440, 461);
            if(gameover)
                break;
            if(c.getTranslateY()>720) {
                c.setTranslateY(-700);
//                c.setTranslateX(random1);   //// this shit doesnt work so how bout making another fucking class for it you dumbo! yeet
            }
            else
                c.setTranslateY(c.getTranslateY() + 2);
        }
        for(Block b: decoration){
            int random = ThreadLocalRandom.current().nextInt(0, 2);
            int random1 ;

            if(random == 0)
                random1 =  ThreadLocalRandom.current().nextInt(20, 81);
            else
                random1 = ThreadLocalRandom.current().nextInt(420, 481);
            if(gameover)
                break;
            if(b.getTranslateY()>720) {
                b.setTranslateY(-700);
            }else
                b.setTranslateY(b.getTranslateY() + 2);
        }
        for(Block b: lines){
            if(gameover)
                break;
            if(b.getTranslateY()>770) {
                b.setTranslateY(-70);
            }else{
                b.down();
            }
        }
        for(Block b: enemies) {
            int random = ThreadLocalRandom.current().nextInt(100, 351);
            if (b.getBoundsInParent().intersects(player.getBoundsInParent())){
                gameover = true;
            }
            if(gameover)
                break;
            if(b.getTranslateY()>770) {
                b.setTranslateX(random);
                b.setTranslateY(-70);
            }if(score2 < 4){
                b.down();
                continue;
            }if(score2 < 8){
                b.downFast();
                level = 2;
                continue;
            }if(score2 < 12){
                b.downFaster();
                level = 3;
                continue;
            }if(score2 < 16){
                b.downFastest();
                level = 4;
                continue;
            }if(score2 >= 16){
                b.downYeet();
                level = 5;
                continue;
            }
        }
        for(Block b: blocks){
            switch (b.type){
                case "enemy":
                    if(b.getBoundsInParent().intersects(player.getBoundsInParent())){
                        gameover = true;
                        player.setFill(Color.BLACK);
                        b.setFill(Color.BLACK);

                    }
                    if(b.getTranslateY()>720){
                        score2++;

                        b.setFill(Color.GREEN);
                    }
                    if(b.getTranslateY()<0)
                        b.setFill(Color.YELLOW);
                    break;
                default:
                    break;
            }
        }
        /*blocks().forEach(s -> {
            switch (s.type){
                case "enemy":
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                        s.dead = true;
                        gameover = true;
                        player.setFill(Color.BLACK);
                        s.setFill(Color.BLACK);
                    }
                    break;
                default:
                    break;


            }
        });*/
        /*root.getChildren().removeIf(n -> {
            Block b = (Block) n;
            return b.dead;

        });*/
    }

    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(create());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case A:
                    if(gameover)
                        break;

                    if(player.getTranslateX() - 10 > 90) {
                        player.left();
                        break;
                    }
                    break;
                case D:
                    if(gameover)
                        break;
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
            setTranslateY(getTranslateY() + 10);
        }
        void downFast(){
            setTranslateY(getTranslateY() + 15);
        }
        void downFaster(){
            setTranslateY(getTranslateY() + 20);
        }
        void downFastest(){
            setTranslateY(getTranslateY() + 25);
        }
        void downYeet(){
            setTranslateY(getTranslateY() + 40);
        }
    }












































    public static void main (String[] args) {
        launch(args);
    }
}
