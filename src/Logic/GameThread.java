package Logic;

/**
 * Created by Rogdan on 27.12.2015.
 */
public class GameThread implements Runnable {
    private Game game;
    public Thread t;
    private volatile boolean isPause, isExit;

    public GameThread(Game game){
        this.game = game;
        t = new Thread(this);
        t.setDaemon(true);
    }

    @Override
    public void run() {
        while (!isExit) {
            checkPause();
            game.checkGameState();
            try {
                Thread.sleep(time_sleep);
            } catch (InterruptedException ignored) {}
        }
    }

    public void stop(){
        isExit = true;
    }

    public void pause(){
        isPause = true;
    }

    public synchronized void resume(){
        isPause = false;
        notify();
    }

    public void start(){
        t.start();
    }

    private synchronized void checkPause(){
        if (isPause)
            try {
                System.out.println("pause");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public static final int time_sleep = 5;
}