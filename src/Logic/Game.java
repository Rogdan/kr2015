package Logic;

import SoundMaker.Sound;
import Visual.*;
import Visual.Menu.NotificationPanel;

import java.io.*;
import java.util.*;

/**
 * Created by Rogdan on 26.12.2015.
 */

public class Game implements Observer{
    private GameBoard gameBoard;
    private ArrayList<MoveObject> moveObjects = new ArrayList<>();
    private ArrayDeque <MoveObject> queueOfMoveObjects = new ArrayDeque<>(100);
    private Scanner in;
    private GameThread gameThread;
    private int currentLevel;
    private int deadCount;

    public Game (GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    private void newGame() {
        currentLevel = 1;
        loadLevel(currentLevel);
        startGame();
    }

    private void loadLevel(int level){
        deadCount = 0;
        MainFrame.notificationPanel.notificationAboutLevelStart(level);
        try {
            in = new Scanner(new BufferedReader(new FileReader(LEVEL_PATH + String.valueOf(level) + ".txt")));
            while (in.hasNext())
                loadObjectPack();
        } catch (IOException ignored){
            System.out.println("Exception in level loading");
        }
        in.close();
    }

    private void startGame(){
        gameBoard.newGame();
        gameThread = new GameThread(this);
        gameThread.start();
    }

    private void clearBoard(){
        queueOfMoveObjects.clear();
        for (MoveObject moveObject : moveObjects) {
            gameBoard.remove(moveObject);
        }
        moveObjects.clear();
    }

    private void repaintObjects(){
        checkMoveObjectList();
        for (int i = 0; i < moveObjects.size(); i++) {
            MoveObject moveObject = moveObjects.get(i);
            moveObject.flee();
            switch (moveObject.getState()){
                case DEAD:
                    soundToPlay();
                    gameBoard.addScores(moveObject.getScores());
                    break;
                case TRASH:
                case OUT_OF_RANGE:
                    gameBoard.remove(moveObject);
                    moveObjects.remove(i);
                    checkMoveObjectList();
                    break;
            }
        }
    }

    private void soundToPlay() {
        deadCount++;
        switch (deadCount){
            case 1:
                Sound.playInThread("first_life.wav");
                break;
            case 4:
                Sound.playInThread("double kill.wav");
                break;
            case 8:
                Sound.playInThread("mega kill.wav");
                break;
            case 12:
                Sound.playInThread("monster kill.wav");
                break;
            case 16:
                Sound.playInThread("rampage.wav");
                break;
        }
    }

    public void checkGameState(){
        switch (gameBoard.getBoardState()){
            case PAUSE:
                gameThread.pause();
                break;
            case RUN:
                repaintObjects();
                break;
            case NOT_EXIST:
            case STOP:
                clearBoard();
                gameThread.stop();
                break;

        }
    }

    private void checkMoveObjectList(){
        if (isLevelEnd()) {
            Sound.playInThread("holy sheet.wav");
            checkGameEnd();
        }
        else
            loadFullListFromQueue();
    }

    private boolean isLevelEnd(){
        return queueOfMoveObjects.isEmpty() && moveObjects.isEmpty();
    }

    private void checkGameEnd(){
        if (currentLevel + 1 <= MAXIMAL_LEVEL) {
            MainFrame.notificationPanel.levelCompleteNotification();
            waitWhileRead();
            loadLevel(++currentLevel);
        }
        else {
            MainFrame.notificationPanel.youWinNotification();
            gameThread.stop();
            waitWhileRead();
            MainFrame.switcher.update(null, "main menu");
        }
    }

    private void loadFullListFromQueue(){
        while (moveObjects.size() < COUNT_OF_ACTIVE_MOVING_OBJECT && !queueOfMoveObjects.isEmpty()) {
            MoveObject moveObject = queueOfMoveObjects.poll();
            moveObjects.add(moveObject);
            gameBoard.add(moveObject);
        }
        gameBoard.repaint();
    }

    private void waitWhileRead() {
        try {
            Thread.sleep(NotificationPanel.WAIT_IN_MILLIS + 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void save(){
    }

    private void loadObjectPack(){
        Random r = new Random();
        MyPoint maxPoint = new MyPoint(gameBoard.getBoardDimension().width, gameBoard.getBoardDimension().height);

        SkinPack skinPack = new SkinPack();
        skinPack.packFor(in.next());

        int countOfObject = in.nextInt();
        in.next();
        int health = in.nextInt();
        in.next();
        int scoreForOne = in.nextInt();

        for (int i = 0; i < countOfObject; i++) {
            int updateBetweenSteps = r.nextInt(MAXIMAL_SCREEN_UPDATE_BETWEEN_STEPS) + 1;

            MoveObjectModel moveObjectModel = new MoveObjectModel(updateBetweenSteps, health, DAMAGE_TAKEN, maxPoint, scoreForOne);
            MoveObject moveObject = new MoveObject(moveObjectModel, skinPack);
            queueOfMoveObjects.addLast(moveObject);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.toString().equals("new game"))
            newGame();

        if (arg.toString().equals("resume"))
            gameThread.resume();

        if (arg.toString().equals("main menu")){
            clearBoard();
            MainFrame.switcher.update(null, "main menu");
        }

        if (arg.toString().equals("save"))
            save();
    }

    public static String LEVEL_PATH = "res\\levels\\";
    public static final String SAVE_PATH = "res\\save\\save.txt";
    public static final int MAXIMAL_SCREEN_UPDATE_BETWEEN_STEPS = 6;
    public static final int DAMAGE_TAKEN = 1;
    public static final int MAXIMAL_LEVEL = 4;
    public static final int COUNT_OF_ACTIVE_MOVING_OBJECT = 20;
}