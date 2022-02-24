import java.awt.image.BufferedImage;
import java.util.Random;

public class Object {

    private String name;
    private int valueObject;
    private int posX;
    private int posY;
    private int numberEaten = 0;
    private BufferedImage img;
    private Random random = new Random();

    Object(String name, int value, BufferedImage img){
        this.name = name;
        this.valueObject = value;
        this.img = img;
        this.generateCoord();
    }

    /**
     * Check if two coordinate collides with the two positions
     * @return
     */
    public boolean checkColision(int posX, int posY){

        boolean returnValue = false;

        if((posX == this.posX) && (posY == this.posY) ){
            numberEaten++;
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Generate a new coordinate
     */
    public void generateCoord(){
        posX = random.nextInt((int) (GamePanel.SCRREN_WIDTH/GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
        posY = random.nextInt((int) (GamePanel.SCREEN_HEIGHT/GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
    }

    /** Getter section **/
    public String getName() {
        return name;
    }

    public int getValueObject() {
        return valueObject;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getNumberEaten() {
        return numberEaten;
    }

    public BufferedImage getImg() {
        return img;
    }
}
