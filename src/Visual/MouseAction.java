package Visual;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import SoundMaker.*;

public class MouseAction implements MouseListener {
    private JButton button;
    private boolean clickOnPress;

    public MouseAction(JButton button, boolean clickOnPress){
        this.button = button;
        this.clickOnPress = clickOnPress;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!clickOnPress) {
            button.doClick();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (clickOnPress)
            button.doClick();
        MainFrame.myCursorThread.setCursor(MyCursorThread.PRESSED_CURSOR_PATH);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MainFrame.myCursorThread.setCursor(MyCursorThread.DEFAULT_CURSOR_PATH);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        MainFrame.myCursorThread.setCursor(MyCursorThread.DEFAULT_CURSOR_PATH);
    }
}