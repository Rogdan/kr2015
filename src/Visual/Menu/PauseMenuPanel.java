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

public class PauseMenuPanel extends JPanel {
    private Dimension programDimension;
    private JButton mainMenuButton, resumeButton, saveButton;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public PauseMenuPanel(Dimension programDimension){
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
        initResumeButton();
        initResumeButtonAction();
        initSaveButton();
        initSaveButtonAction();
        initMainMenuButton();
        initMainMenuButtonAction();

        repaint();
    }

    private void initResumeButton() {
        resumeButton = new JButton();
        resumeButton.setIcon(new ImageIcon(GRAPHIC_PATH + "resume.png"));
        initButton(resumeButton);

        add(resumeButton);
    }

    private void initResumeButtonAction(){
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("resume");
            }
        });
    }

    private void initSaveButton() {
        saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(GRAPHIC_PATH + "save.png"));
        initButton(saveButton);

        add(saveButton);
    }

    private void initSaveButtonAction(){
        saveButton.removeMouseListener(saveButton.getMouseListeners()[0]);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("save");
            }
        });
    }

    private void initMainMenuButton() {
        mainMenuButton = new JButton();
        mainMenuButton.setIcon(new ImageIcon(GRAPHIC_PATH + "main menu.png"));
        initButton(mainMenuButton);

        add(mainMenuButton);
    }

    private void initMainMenuButtonAction(){
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myObservable.notifyObservers("main menu");
            }
        });
    }

    private void initButton(JButton button) {
        button.addMouseListener(new MouseAction(button, false));
        button.setSize(BUTTON_DIMENSION);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
    }

    public static final Dimension PANEL_DIMENSION = new Dimension(400, 240);
    public static final Dimension BUTTON_DIMENSION = new Dimension(400, 70);
    public static final String GRAPHIC_PATH = "res\\pause menu\\";
}