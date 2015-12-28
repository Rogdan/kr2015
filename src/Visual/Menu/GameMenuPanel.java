package Visual.Menu;

import Logic.Game;
import Visual.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 26.12.2015.
 */

public class GameMenuPanel extends JPanel {
    private Dimension programDimension;
    private JButton loadGameButton, chooseLevelButton, newGameButton, backButton;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public GameMenuPanel(Dimension programDimension){
        this.programDimension = programDimension;
        initAll();
    }

    private void initAll(){
        initGameMenuPanel();
        initGameMenuComponents();
    }

    private void initGameMenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setPanelToCenterOfProgram();
    }

    private void setPanelToCenterOfProgram() {
        int programWidth = (int)programDimension.getWidth();
        int programHeight = (int)programDimension.getHeight();
        int panelWidth = (int) PANEL_DIMENSION.getWidth();
        int panelHeight = (int) PANEL_DIMENSION.getHeight();

        int xCoordinate = (programWidth - panelWidth)/2;
        int yCoordinate = (programHeight - panelHeight)/2;

        setBounds(xCoordinate, yCoordinate, panelWidth, panelHeight);
        repaint();
    }

    private void initGameMenuComponents(){
        initNewGameButton();
        initNewGameButtonAction();

        initLoadGameButton();
        initChooseLevelButton();
        initBackButton();
        initBackButtonAction();

        repaint();
    }

    private void initNewGameButton() {
        newGameButton = new JButton();
        newGameButton.setIcon(new ImageIcon(GRAPHIC_PATH + "new game.png"));
        initButton(newGameButton);

        add(newGameButton);
    }

    private void initNewGameButtonAction(){
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("new game");
            }
        });
    }

    private void initLoadGameButton() {
        loadGameButton = new JButton();
        //todo Check load game enabled
        loadGameButton.setIcon(new ImageIcon(GRAPHIC_PATH + "load game disabled.png"));
        initButton(loadGameButton);

        add(loadGameButton);
    }

    private void initChooseLevelButton() {
        chooseLevelButton = new JButton();
        chooseLevelButton.setIcon(new ImageIcon(GRAPHIC_PATH + "choose level.png"));
        initButton(chooseLevelButton);

        add(chooseLevelButton);
    }

    private void initBackButton(){
        backButton = new JButton();
        backButton.setIcon(new ImageIcon(GRAPHIC_PATH + "back.png"));
        initButton(backButton);

        add(backButton);
    }

    private void initBackButtonAction(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("main menu");
            }
        });
    }

    private void initButton(JButton button){
        button.setSize(BUTTON_DIMENSION);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.addMouseListener(new MouseAction(button, false));
    }



    public static final Dimension PANEL_DIMENSION = new Dimension(550, 320);
    public static final Dimension BUTTON_DIMENSION = new Dimension(550, 70);
    public static final String GRAPHIC_PATH = "res\\game menu\\";
}