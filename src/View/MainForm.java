package View;

import Model.*;
import Model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class MainForm implements Observer{
    private FonPanel fon;
    private JFrame mainFrame;
    private Dimension screenSize;

    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        mainForm.setVisibleTrue();
        mainForm.startGame();
    }

    public MainForm(){
        initAll();
    }

    private void initAll(){
        initMainFrame();
        initFon();
    }

    private void initMainFrame(){
        mainFrame = new JFrame("Smash the cockroach");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage(iconPath);
        screenSize = kit.getScreenSize();
        screenSize = new Dimension(1000, 600);

        mainFrame.setIconImage(img);
        mainFrame.setSize(screenSize);
        mainFrame.setResizable(false);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initFon(){
        fon = new FonPanel("res\\Table1.jpg");
        mainFrame.setContentPane(fon);
        mainFrame.setSize(fon.getDimension());
    }

    private void setVisibleTrue(){
        mainFrame.setVisible(true);
    }

    private void startGame(){
        int x = (int) screenSize.getWidth();
        int y = (int) screenSize.getHeight();
        Point maxCoordinate = new Point(x, y);

        for (int i = 1; i < 5; i++) {
            MoveObject moveObject = new MoveObject(new MovingObjectModel(10 - i % 10 + 5, 2, maxCoordinate), 1);
            mainFrame.add(moveObject);
            moveObject.addObserver(this);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    moveObject.flee();
                }
            }).start();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        MoveObject obj = (MoveObject) arg;

        JLabel smashLabel = new JLabel();
        smashLabel.setOpaque(false);
        smashLabel.setIcon(new ImageIcon("res\\smashed.png"));
        smashLabel.setBounds(obj.getLastPoint().getX(), obj.getLastPoint().getY(), 90, 90);
        mainFrame.remove(obj);
        mainFrame.add(smashLabel);
        mainFrame.repaint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    mainFrame.remove(smashLabel);
                    mainFrame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static final String iconPath = "res\\icon.png";
}
