package Visual;

import Logic.MyPoint;
import Logic.MoveObjectModel;

import javax.swing.*;
import java.awt.*;

public class MoveObject extends JButton {
    private MoveObjectModel movingModel;
    private Icon skin;
    private SkinPack skinPack;
    private int scoresForThis = 0;

    public MoveObject(MoveObjectModel movingModel, SkinPack skinPack){
        this.movingModel = movingModel;
        this.skinPack = skinPack;

        initAll();
    }

    private void initAll(){
        initMovingObject();
    }

    private void initMovingObject(){
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        setOpaque(false);
        removeMouseListener(getMouseListeners()[0]);
        addMouseListener(new MouseAction(this, true));
        setSkin();
        setObjectSize();
    }

    private void setSkin(){
        switch (movingModel.getState()){
            case MOVE_DOWN:
                skin = skinPack.getDownImage();
                break;
            case MOVE_LEFT:
                skin = skinPack.getLeftImage();
                break;
            case MOVE_RIGHT:
                skin = skinPack.getRightImage();
                break;
            case MOVE_UP:
                skin = skinPack.getUpImage();
                break;
            case DEAD:
                skin = skinPack.getSmashedImage();
                break;
        }

        setIcon(skin);
        repaint();
    }

    private void setObjectSize(){
        int x = skin.getIconHeight();
        int y = skin.getIconWidth();
        setSize(new Dimension(x, y));
    }

    public int getScores(){
        return scoresForThis;
    }

    public void flee(){
        movingModel.makeStep();

        switch (movingModel.getState()){
            case OUT_OF_RANGE:
                break;
            case DEAD:
                scoresForThis = movingModel.getScoreForKill();
            case MOVE_DOWN:
            case MOVE_UP:
            case MOVE_LEFT:
            case MOVE_RIGHT:
                setSkin();
                setObjectSize();
                MyPoint myPoint = movingModel.getCurrentCoordinate();
                setBounds(myPoint.getX(), myPoint.getY(), skin.getIconWidth(), skin.getIconHeight());
                repaint();
                break;
        }
    }

    public void doClick(){
        movingModel.bump();
    }

    public MoveObjectModel.State getState() {
        return movingModel.getState();
    }
}