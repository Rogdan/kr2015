package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Rogdan on 23.12.2015.
 */
public class Menu extends JPanel {
    private JButton newGameButton, continueGameButton, helpButton;
    private Dimension programDimension;
    private MainForm gameBoard;
    private ArrayList <MoveObject> fonCockroaches;

    public Menu (Dimension programDimension, MainForm gameBoard){
        this.gameBoard = gameBoard;
        this.programDimension = programDimension;
        initAll();
    }

    private void initAll() {
        initMenu();
        initMenuComponents();
    }

    private void initMenu() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        setOpaque(false);
        checkDimension();
        repaint();
    }

    public void setDimension(Dimension dimension){
        programDimension = dimension;
        checkDimension();
    }

    private void checkDimension(){
        int y = (int) programDimension.getHeight() / 4;
        int x = (int) programDimension.getWidth() / 3;
        setBounds(x, y, 400, 280);
    }

    private void initMenuComponents(){
        initNewGameButton();
        initContinueGameButton();
        initAchievementsButton();
        initAboutButton();
    }

    private void initNewGameButton() {
        newGameButton = new JButton();
        initButton(newGameButton);
        newGameButton.setIcon(new ImageIcon("res\\newGame.png"));
        add(newGameButton);
    }

    private void initContinueGameButton() {
        //todo
    }

    private void initAchievementsButton() {
        //todo
    }

    private void initSetFonButton(){
        //todo repaint menu in another coordinate
    }

    private void initAboutButton() {
        helpButton = new JButton();
        initButton(helpButton);
        helpButton.setIcon(new ImageIcon("res\\help.png"));
        add(helpButton);
    }

    private void initButton(JButton button){
        button.setSize(MENU_BUTTON_DIMENSION);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
    }

    public void initMenuCockroaches(){
        int x = (int) programDimension.getWidth();
        int y = (int) programDimension.getHeight();
        Model.Point maxCoordinate = new Model.Point(x, y);
        SkinPack skinPack = new SkinPack();
        skinPack.packForLittleCockroach();
        fonCockroaches = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Random r = new Random();
            MovingObjectModel movingObjectModel = new MovingObjectModel(r.nextInt(5) + 1, 1, 1, maxCoordinate, true);
            MoveObject cockroach = new MoveObject(movingObjectModel, skinPack);
            gameBoard.add(cockroach);
            fonCockroaches.add(cockroach);
        }

        TaskOnTimer task = new TaskOnTimer();
        task.setFrame(gameBoard);
        java.util.Timer timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(task, 0, 5);
    }

    private void stopMenuCockroachesRun(){
        //todo Remove oll cockroaches from gameBoard
        gameBoard.setMenuCockroachesRun(false);
    }

    public void updateMenuCockroaches(){
        for(int i = 0; i < fonCockroaches.size(); i ++){
            MoveObject cockroach = fonCockroaches.get(i);
            cockroach.flee();
            MovingObjectModel.State state = cockroach.getState();

            switch (state) {
                case TRASH:
                case OUT_OF_RANGE:
                    gameBoard.remove(cockroach);
                    fonCockroaches.remove(i);
                    break;
            }
        }

        if (fonCockroaches.size() == 0) {
            System.out.println("there");
            stopMenuCockroachesRun();
        }
    }

    public static Dimension MENU_BUTTON_DIMENSION = new Dimension(400, 70);
}