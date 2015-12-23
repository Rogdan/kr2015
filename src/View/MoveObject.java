package View;

import Model.MovingObjectModel;
import Model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Rogdan on 20.12.2015.
 */

public class MoveObject extends JButton {
    private MovingObjectModel movingModel;
    private Icon skin;
    private SkinPack skinPack;

    public MoveObject(MovingObjectModel movingModel, SkinPack skinPack){
        this.movingModel = movingModel;
        this.skinPack = skinPack;

        initAll();
    }

    private void initAll(){
        initMovingObject();
        initActionListener();
    }

    private void initMovingObject(){
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
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

    private void initActionListener(){
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movingModel.bump();
                System.out.println("bump");
                //todo add bump animation or sound
            }
        });
    }

    public void flee(){
        movingModel.makeStep();

        switch (movingModel.getState()){
            case OUT_OF_RANGE:
                break;
            case MOVE_DOWN:
            case MOVE_UP:
            case MOVE_LEFT:
            case MOVE_RIGHT:
            case DEAD:
                setSkin();
                setObjectSize();
                Point point = movingModel.getCurrentCoordinate();
                setBounds(point.getX(), point.getY(), skin.getIconWidth(), skin.getIconHeight());
                repaint();
                break;
        }
    }

    public MovingObjectModel.State getState() {
        return movingModel.getState();
    }
}
