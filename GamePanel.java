import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCRREN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCRREN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    BufferedImage snakeHead;
    BufferedImage snakeBody;
    Object apple;
    Object cherry;
    Object citrus;
    boolean cherryActivated = false;
    boolean citrusActivated = false;

    {
        try {
            snakeHead = ImageIO.read(new File("./src/assets/snake_head.png"));
            snakeBody =  ImageIO.read(new File("./src/assets/snake_body.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Ghost ghost;
    boolean ghosteEnable = true;

    public GamePanel(){
        random = new Random();
        setPreferredSize(new Dimension(SCRREN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());

        //We generate all the fruits
        try {
            apple = new Object("Apple", 1, ImageIO.read(new File("./src/assets/apple.png")));
            cherry = new Object("Cherry", 5, ImageIO.read(new File("./src/assets/cherry.png")));
            citrus = new Object("Citrus", 2, ImageIO.read(new File("./src/assets/citrus.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ghosteEnable){
            ghost = new Ghost();
        }

    }

    public void startGame(){
        applesEaten=0;
        bodyParts=6;
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        draw(g2d);
    }

    public void draw(Graphics2D g){

        if(running) {
            //We draw the apples
            g.drawImage(apple.getImg(), apple.getPosX(), apple.getPosY(), null);

            //The other fruits are randomly generated
           if(random.nextInt(300)  == 1 || cherryActivated){
                cherryActivated = true;
                g.drawImage(cherry.getImg(), cherry.getPosX(), cherry.getPosY(), null);
            }
            if(random.nextInt(100) == 1 || citrusActivated){
                citrusActivated = true;
                g.drawImage(citrus.getImg(), citrus.getPosX(), citrus.getPosY(), null);
            }

            //we move the ghost
            if(ghosteEnable){
                ghost.generateNextPosition();
                g.drawImage(ghost.getGhostImg(), ghost.getGhostX(), ghost.getGhostY(), null);
            }

            for (int i = 0; i < bodyParts; i++) {
                //Its the head
                if (i == 0) {
                    g.drawImage(snakeHead, x[i], y[i], null);
                } else {
                    g.drawImage(snakeBody, x[i], y[i], null);
                }
            }
            str += "Mean = " + Float.toString((sum/( (float)compteur)));
            g.setColor(Color.BLUE);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + checkScore() , (SCRREN_WIDTH - metrics.stringWidth("Score: " + checkScore()))/2, g.getFont().getSize());
        }else{
            if((x[0] == 0) && (y[0] == 0)){
                titleScreen(g);
            }else{
                gameOver(g);
            }

        }
    }

    public int checkScore(){
        return ((apple.getNumberEaten() * apple.getValueObject()) + (cherry.getNumberEaten() * cherry.getValueObject()) + (citrus.getNumberEaten() * citrus.getValueObject()) );
    }

    public void move(){
        for(int i = bodyParts; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] +UNIT_SIZE;
                break;
        }
    }

    public void checkFruits(){

        if(running){
            if(apple.checkColision(x[0], y[0])){
                bodyParts += apple.getValueObject();
                apple.generateCoord();
            }
            if(cherry.checkColision(x[0],y[0])){
                bodyParts += cherry.getValueObject();
                cherry.generateCoord();
                cherryActivated=false;
            }
            if(citrus.checkColision(x[0],y[0])){
                bodyParts += citrus.getValueObject();
                citrus.generateCoord();
                citrusActivated= false;
            }
        }
    }



    public void checkCollision(){

        //we check if the head collides with the body
        for(int i = bodyParts; i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        //we check if head touches left border
        if(x[0] < 0){
            running = false;
        }
        //check right
        if(x[0] > SCRREN_WIDTH){
            running = false;
        }
        //check top
        if(y[0] < 0){
            running = false;
        }
        //Check bottom
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void titleScreen(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Press enter to start. Control: w,a,s,d", (SCRREN_WIDTH - metrics.stringWidth("Press enter to start. Control: w,a,s,d"))/2, SCREEN_HEIGHT/2);
    }

    public void gameOver(Graphics2D g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCRREN_WIDTH - metrics.stringWidth("Game over"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + checkScore(), (SCRREN_WIDTH - metrics1.stringWidth("Score: " + checkScore()))/2, g.getFont().getSize());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkFruits();
            checkCollision();

            if(ghosteEnable && ghost.checkCollision(x[0],y[0])){
                running = false;
                timer.stop();

            }
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                    //Can't collide with himself
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    //Can't collide with himself
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    //Can't collide with himself
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    //Can't collide with himself
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                   //Start game
                    if(!running){
                        startGame();
                    }

                    break;
            }
        }
    }

}
