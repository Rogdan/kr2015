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

public class MainMenuPanel extends JPanel {
    private Dimension programDimension;
    private JButton gameButton, settingsButton, helpButton, aboutButton, exitButton;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public MainMenuPanel(Dimension programDimension){
        this.programDimension = programDimension;
        initAll();
    }

    private void initAll(){
        initMainMenuPanel();
        initMainMenuComponents();
        repaint();
    }

    private void initMainMenuPanel() {
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

    private void initMainMenuComponents(){
        initGameButton();
        initGameButtonAction();

        initSettingsButton();
        initHelpButton();
        initAboutButton();

        initExitButton();
        initExitButtonAction();
    }

    private void initGameButton() {
        gameButton = new JButton();
        gameButton.setIcon(new ImageIcon(GRAPHIC_PATH + "game.png"));
        initButton(gameButton);

        add(gameButton);
    }

    private void initGameButtonAction(){
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("game");
            }
        });
    }

    private void initSettingsButton(){
        settingsButton = new JButton();
        settingsButton.setIcon(new ImageIcon(GRAPHIC_PATH + "settings.png"));
        initButton(settingsButton);

        add(settingsButton);
    }

    private void initHelpButton() {
        helpButton = new JButton();
        helpButton.setIcon(new ImageIcon(GRAPHIC_PATH + "help.png"));
        initButton(helpButton);

        add(helpButton);
    }

    private void initAboutButton() {
        aboutButton = new JButton();
        aboutButton.setIcon(new ImageIcon(GRAPHIC_PATH + "about.png"));
        initButton(aboutButton);

        add(aboutButton);
    }

    private void initExitButton() {
        exitButton = new JButton();
        exitButton.setIcon(new ImageIcon(GRAPHIC_PATH + "exit.png"));
        initButton(exitButton);

        add(exitButton);
    }

    private void initExitButtonAction(){
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("exit");
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

    private static final int COUNT_OF_COMPONENTS = 5;
    public static final Dimension PANEL_DIMENSION = new Dimension(400, COUNT_OF_COMPONENTS*(70+10));
    public static final Dimension BUTTON_DIMENSION = new Dimension(400, 70);
    public static final String GRAPHIC_PATH = "res\\main menu\\";
}