package Visual;
import javax.swing.*;

/**
 * Created by Rogdan on 22.12.2015.
 */

public class SkinPack {
    private Icon upImage, downImage, leftImage, rightImage, smashedImage;

    public SkinPack(){
    }

    public SkinPack (ImageIcon leftImage, ImageIcon rightImage, ImageIcon upImage, ImageIcon downImage, ImageIcon smashedImage){
        this.upImage = upImage;
        this.downImage = downImage;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.smashedImage = smashedImage;
    }

    public SkinPack(String leftPath, String rightPath, String upPath, String downPath, String smashedPath){
        this(new ImageIcon(upPath), new ImageIcon(downPath), new ImageIcon(leftPath),
                new ImageIcon(rightPath), new ImageIcon(smashedPath));
    }

    public Icon getUpImage() {
        return upImage;
    }

    public Icon getDownImage() {
        return downImage;
    }

    public Icon getLeftImage() {
        return leftImage;
    }

    public Icon getRightImage() {
        return rightImage;
    }

    public Icon getSmashedImage(){
        return smashedImage;
    }

    public void littleCockroachesPack(){
        upImage = littleUp;
        downImage = littleDown;
        leftImage = littleLeft;
        rightImage = littleRight;
        smashedImage = littleSmashed;
    }

    public void catPack(){
        upImage = new ImageIcon(OBJECT_PATH + "cat\\right up.gif");
        downImage = new ImageIcon(OBJECT_PATH + "cat\\right up.gif");
        leftImage = new ImageIcon(OBJECT_PATH + "cat\\left down.gif");
        rightImage = new ImageIcon(OBJECT_PATH + "cat\\right up.gif");
        smashedImage = new ImageIcon(OBJECT_PATH + "cat\\dead.gif");
    }

    public void packFor(String who){
        if (who.equals("little_cockroaches"))
            littleCockroachesPack();

        if (who.equals("cat"))
            catPack();
    }

    private static final String LITTLE_COCKROACH_PATH = "res\\moving objects\\little cockroaches\\";
    private static final String OBJECT_PATH = "res\\moving objects\\";

    private static ImageIcon littleDown = new ImageIcon(LITTLE_COCKROACH_PATH + "littleDown.png");
    private static ImageIcon littleUp = new ImageIcon(LITTLE_COCKROACH_PATH + "littleUp.png");
    private static ImageIcon littleRight = new ImageIcon(LITTLE_COCKROACH_PATH + "littleRight.png");
    private static ImageIcon littleLeft = new ImageIcon(LITTLE_COCKROACH_PATH + "littleLeft.png");
    private static ImageIcon littleSmashed = new ImageIcon(LITTLE_COCKROACH_PATH + "sw.gif");
}