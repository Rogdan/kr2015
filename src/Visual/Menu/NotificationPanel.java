package Visual.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rogdan on 27.12.2015.
 */
public class NotificationPanel extends JPanel {
    private Dimension programDimension;

    public NotificationPanel(Dimension programDimension){
        this.programDimension = programDimension;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setVisible(false);
        setOpaque(false);
        setSize(600, 70);
        setToTheCenterOfProgram();
    }

    public void notificationAboutLevelStart(int level){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(LEVEL_PATH));
        add(label);

        String stringScores = Integer.toString(level);
        for (int i = 0; i < stringScores.length(); i++) {
            JLabel digitLabel = new JLabel();
            String digit = String.valueOf(stringScores.charAt(i));
            digitLabel.setIcon(new ImageIcon(DIGITS_PATH + digit + ".png"));
            add(digitLabel);
        }
        showAndHide();
    }

    public void levelCompleteNotification(){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(LEVEL_COMPLETE));
        add(label);
        showAndHide();
    }

    public void youWinNotification(){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(YOU_WIN));
        add(label);
        showAndHide();
    }

    private void showAndHide(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
                setToTheCenterOfProgram();
                repaint();
                waitWhileUserRead();
                setVisible(false);
                removeAll();
                repaint();
            }
        }).start();
    }

    private void waitWhileUserRead(){
        try {
            Thread.sleep(WAIT_IN_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setToTheCenterOfProgram(){
        int x = (int)programDimension.getWidth();
        int y = (int) programDimension.getHeight();

        x = (x - getWidth())/2;
        y = (y - getHeight())/2;
        setBounds(x, y, getWidth(), getHeight());
    }

    private static final int MAX_LEVEL = 2;
    private static final String NOTIFICATION_PATH = "res\\notifications\\";
    private static final String LEVEL_PATH = NOTIFICATION_PATH + "level.png";
    private static final String LEVEL_COMPLETE = NOTIFICATION_PATH + "level complete.png";
    public static final String YOU_WIN = NOTIFICATION_PATH + "you win.png";
    private static final String DIGITS_PATH = ScorePanel.DIGITS_PATH;
    public static final int WAIT_IN_MILLIS = 2000;

}
