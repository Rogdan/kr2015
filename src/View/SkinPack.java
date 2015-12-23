package View;

import javax.swing.*;

/**
 * Created by Rogdan on 22.12.2015.
 */

public class SkinPack {
    private ImageIcon upImage, downImage, leftImage, rightImage, smashedImage;

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

    public ImageIcon getUpImage() {
        return upImage;
    }

    public ImageIcon getDownImage() {
        return downImage;
    }

    public ImageIcon getLeftImage() {
        return leftImage;
    }

    public ImageIcon getRightImage() {
        return rightImage;
    }

    public ImageIcon getSmashedImage(){
        return smashedImage;
    }

    public void packForLittleCockroach(){
        upImage = littleUp;
        downImage = littleDown;
        leftImage = littleLeft;
        rightImage = littleRight;
        smashedImage = littleSmashed;
    }

    private static ImageIcon littleDown = new ImageIcon("res\\littleDown.png");
    private static ImageIcon littleUp = new ImageIcon("res\\littleUp.png");
    private static ImageIcon littleRight = new ImageIcon("res\\littleRight.png");
    private static ImageIcon littleLeft = new ImageIcon("res\\littleLeft.png");
    private static ImageIcon littleSmashed = new ImageIcon("res\\smashed.png");
}
