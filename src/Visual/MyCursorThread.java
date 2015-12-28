package Visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rogdan on 28.12.2015.
 */
public class MyCursorThread extends JLabel implements Runnable{
    Thread t;
    MainFrame mainFrame;

    public MyCursorThread(MainFrame mainFrame){
        setIcon(new ImageIcon(DEFAULT_CURSOR_PATH));
        setSize(95, 117);
        t = new Thread(this);
        this.mainFrame = mainFrame;
        t.start();
    }

    public synchronized void setCursor(String path){
        setIcon(new ImageIcon(path));
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Point location = mainFrame.getMousePosition(true);
            if (location != null) {
                int x = location.x;
                int y = location.y;
                setBounds(x - 15, y - 40, getWidth(), getHeight());
                repaint();
            }
        }
    }

    public static final String DEFAULT_CURSOR_PATH = "res\\cursor\\default.png";
    public static final String PRESSED_CURSOR_PATH = "res\\cursor\\press.png";
}
