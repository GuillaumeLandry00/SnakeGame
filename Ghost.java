import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ghost {

    private int ghostX;
    private int ghostY;
    Random rand = new Random();
    private BufferedImage ghostImg;
    private int distance;
    private int compteur;
    private int direction;

    public Ghost(){
        ghostX = GamePanel.SCRREN_WIDTH/2;
        ghostY = GamePanel.SCREEN_HEIGHT/2;
        distance = rand.nextInt((8 - 1) + 1) + 1;
        compteur =0;
        //Start by the top
        direction = rand.nextInt((4 - 1) + 1) + 1;
        try {
            ghostImg =  ImageIO.read(new File("./src/assets/ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateNextPosition(){

        if(compteur > distance){
            direction = rand.nextInt((4 - 1) + 1) + 1;
            distance = rand.nextInt((8 - 1) + 1) + 1;
            compteur =0;
        }

        compteur++;


        //the ghost move randomly
        switch(direction){
            case 1:
                ghostY += GamePanel.UNIT_SIZE;
                break;
            case 2:
                ghostY -= GamePanel.UNIT_SIZE;
                break;
            case 3:
                ghostX += GamePanel.UNIT_SIZE;
                break;
            case 4:
                ghostX -= GamePanel.UNIT_SIZE;
                break;
        }
        this.checkCollisionGhost();
    }

    /**
     * Check if the ghost has a collision with another object
     */
    public boolean checkCollision(int posX, int posY){
        boolean collided = false;

        if((posX == ghostX) && (posY == ghostY)){
            collided = true;
        }

        return collided;
    }

    //we make sure the ghost stay in the board;
    private void checkCollisionGhost(){
        if(ghostX < 0){
            ghostX = GamePanel.SCRREN_WIDTH;
        }
        if(ghostX>GamePanel.SCRREN_WIDTH){
            ghostX = 0;
        }
        if(ghostY < 0){
            ghostY = GamePanel.SCREEN_HEIGHT;
        }
        if(ghostY > GamePanel.SCREEN_HEIGHT){
            ghostY = 0;
        }
    }

    public int getGhostX() {
        return ghostX;
    }

    public int getGhostY() {
        return ghostY;
    }

    public BufferedImage getGhostImg() {
        return ghostImg;
    }
}
