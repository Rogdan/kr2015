package View;

import Model.MovingObjectModel;
import Model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 20.12.2015.
 */

public class MoveObject extends JButton implements Observer{
    private MovingObjectModel movingModel;
    private int damageTaken;
    private Icon skin;
    private Point lastPoint;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public MoveObject(MovingObjectModel movingModel, int damageTaken){
        this.movingModel = movingModel;
        this.damageTaken = damageTaken;

        initAll();
    }

    private void initAll(){
        initMovingObject();
        initActionListener();
    }

    private void initMovingObject(){
        movingModel.addObserver(this);

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        setSkin();
        setObjectSize();

        movingModel.addObserver(this);
    }

    private void setSkin(){
        int state = movingModel.getState();

        switch (state){
            case MovingObjectModel.MOVING_DOWN :
                skin = littleDown;
                break;
            case MovingObjectModel.MOVING_LEFT :
                skin = littleLeft;
                break;
            case MovingObjectModel.MOVING_RIGHT :
                skin = littleRight;
                break;
            case MovingObjectModel.MOVING_UP :
                skin = littleUp;
                break;
        }

        setIcon(skin);
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
                movingModel.bump(damageTaken);
                //todo add bump animation or sound
            }
        });
    }

    public void flee(){
        movingModel.init();
    }

    public Point getLastPoint(){
        return lastPoint;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.toString().equals("dead")){
            myObservable.notifyObservers(this);

            setVisible(false);
            return;
        }

        if (arg.toString().equals("out of range")){
            setVisible(false);
            return;
        }

        if (arg instanceof Point){
            Point point = (Point) arg;
            lastPoint = point;
            setBounds(point.getX(), point.getY(), skin.getIconWidth(), skin.getIconHeight());
            repaint();
        }
    }

    private static Icon littleDown = new ImageIcon("res//littleDown.png");
    private static Icon littleUp = new ImageIcon("res//littleUp.png");
    private static Icon littleRight = new ImageIcon("res//littleRight.png");
    private static Icon littleLeft = new ImageIcon("res//littleLeft.png");
}
