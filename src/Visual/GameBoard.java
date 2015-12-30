package Visual;

import Visual.Menu.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameBoard extends JPanel implements Observer {
    private Dimension programDimension;
    private ScorePanel scorePanel;
    private PauseButton pauseButton;
    private PauseMenuPanel pauseMenuPanel;
    private volatile BoardState boardState;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public GameBoard(Dimension programDimension, ScorePanel scorePanel, PauseButton pauseButton, PauseMenuPanel pauseMenuPanel){
        this.programDimension = programDimension;
        this.scorePanel = scorePanel;
        this.pauseButton = pauseButton;
        this.pauseMenuPanel = pauseMenuPanel;

        initAll();
    }

    private void initAll(){
        boardState = BoardState.NOT_EXIST;
        initGameBoard();
        initGameBoardComponents();
    }

    private void initGameBoard() {
        setLayout(null);
        setOpaque(false);
        setBounds(0, 0, programDimension.width, programDimension.height);
    }

    private void initGameBoardComponents(){
        pauseMenuPanel.setVisible(false);
        pauseButton.setVisible(false);
        scorePanel.setVisible(false);

        pauseButton.addObserver(this);
        pauseMenuPanel.addObserver(this);
        add(scorePanel);
        add(pauseButton);
        add(pauseMenuPanel);
    }

    public Dimension getBoardDimension(){
        return programDimension;
    }

    public void newGame(){
        scorePanel.setScores(0);
        runGame();
    }

    public void pause(){
        scorePanel.setVisible(false);
        pauseButton.setVisible(false);
        pauseMenuPanel.setVisible(true);

        boardState = BoardState.PAUSE;
    }

    public void runGame(){
        scorePanel.setVisible(true);
        pauseButton.setVisible(true);
        pauseMenuPanel.setVisible(false);

        boardState = BoardState.RUN;
    }

    public void stop(){
        pause();
        boardState = BoardState.NOT_EXIST;
    }

    public void checkBoardState(){
        switch (boardState){
            case RUN:
                runGame();
                break;
            case PAUSE:
                pause();
                break;
            case STOP:
                scorePanel.setScores(0);
                stop();
        }
    }

    public void addScores(int scores){
        scorePanel.addScores(scores);
    }

    public enum BoardState{
        PAUSE, RUN, STOP, NOT_EXIST
    }

    public BoardState getBoardState(){
        return boardState;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.toString().equals("pause")) {
            boardState = BoardState.PAUSE;
            myObservable.notifyObservers("pause");
        }

        if (arg.toString().equals("resume")){
            boardState = BoardState.RUN;
            myObservable.notifyObservers("resume");
        }

        if (arg.toString().equals("main menu")){
            boardState = BoardState.STOP;
            myObservable.notifyObservers("main menu");
        }

        if (arg.toString().equals("save")){
            myObservable.notifyObservers("save");
        }

        checkBoardState();
    }

    public static final int MAXIMAL_TIME_BETWEEN_UPDATE = 6;
}