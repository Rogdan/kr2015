package Visual.Menu;

import Visual.MouseAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 26.12.2015.
 */
public class PauseButton extends JButton {
    private int programWidth;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public PauseButton(int programWidth){
        this.programWidth = programWidth;
        initButton();
    }

    private void initButton(){
        initButtonProperties();
        checkButtonPosition();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("pause");
            }
        });
    }

    private void initButtonProperties() {
        setIcon(new ImageIcon(BUTTON_PATH));
        setSize(BUTTON_DIMENSION);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        addMouseListener(new MouseAction(this, false));
    }

    public void checkButtonPosition(){
        int xCoordinate = programWidth - BUTTON_DIMENSION.width - 10;
        int yCoordinate = 0;
        setBounds(xCoordinate, yCoordinate, BUTTON_DIMENSION.width, BUTTON_DIMENSION.height);
        repaint();
    }

    public void setProgramWidth(int width){
        programWidth = width;
        checkButtonPosition();
    }

    public static final String BUTTON_PATH = "res\\pause button\\pause.png";
    public static final Dimension BUTTON_DIMENSION = new Dimension(90, 70);
}