package View;

import javax.swing.*;
import java.util.TimerTask;

/**
 * Created by Rogdan on 23.12.2015.
 */
public class TaskOnTimer extends TimerTask {
    JFrame frame;

    @Override
    public void run() {
        frame.repaint();
    }

    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}
