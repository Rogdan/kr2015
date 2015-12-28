package Logic;

import Visual.GameBoard;
import Visual.Menu.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 26.12.2015.
 */
public class Switcher implements Observer{
    private JFrame mainForm;
    private GameMenuPanel gameMenuPanel;
    private GameBoard gameBoard;
    private MainMenuPanel mainMenuPanel;
    private State state;
    private Dimension dimension;
    private ArrayList <Component> components;


    public Switcher(JFrame mainForm){
        this.mainForm = mainForm;
        dimension = new Dimension(mainForm.getWidth(), mainForm.getHeight());

        initAll();
        checkState();
    }

    private void initAll() {
        state = State.MAIN_MENU;
        components = new ArrayList<>();
        initMainMenuPanel();
        initGameMenuPanel();
        initGameBoard();
    }

    public void initGameMenuPanel(){
        gameMenuPanel = new GameMenuPanel(dimension);
        gameMenuPanel.addObserver(this);
        gameMenuPanel.setVisible(false);
        mainForm.add(this.gameMenuPanel);
        components.add(GAME_MENU_POSITION, this.gameMenuPanel);
    }

    private void initMainMenuPanel(){
        mainMenuPanel = new MainMenuPanel(dimension);
        mainMenuPanel.addObserver(this);
        mainForm.add(mainMenuPanel);
        components.add(MAIN_MENU_POSITION, mainMenuPanel);
    }

    private void initGameBoard(){
        gameBoard = new GameBoard(dimension, new ScorePanel(), new PauseButton(dimension.width), new PauseMenuPanel(dimension));
        gameBoard.setVisible(false);
        gameBoard.addObserver(this);
        mainForm.add(gameBoard);
        components.add(GAME_BOARD_POSITION, gameBoard);
    }

    private void checkState(){
        switch (state){
            case MAIN_MENU:
                setVisible(MAIN_MENU_POSITION);
                break;
            case GAME_MENU:
                setVisible(GAME_MENU_POSITION);
                break;
            case GAME_RUNNING:
                setVisible(GAME_BOARD_POSITION);
                break;
            case EXIT:
                System.exit(0);
                break;
        }

        mainForm.repaint();
    }

    public GameMenuPanel getGameMenuPanel(){
        return gameMenuPanel;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    private void setVisible(int pos){
        for(Component component : components){
            component.setVisible(false);
        }

        components.get(pos).setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {;
        if (arg.toString().equals("game"))
            state = State.GAME_MENU;
        else
        if (arg.toString().equals("main menu"))
            state = State.MAIN_MENU;
        else
        if (arg.toString().equals("new game"))
            state = State.GAME_RUNNING;
        else
        if (arg.toString().equals("exit"))
            state = State.EXIT;

        checkState();
    }

    public enum State {
        MAIN_MENU, GAME_MENU, GAME_RUNNING, EXIT
    }

    public static final int MAIN_MENU_POSITION = 0;
    public static final int GAME_MENU_POSITION = 1;
    public static final int GAME_BOARD_POSITION = 2;
}