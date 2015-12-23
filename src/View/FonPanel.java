package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FonPanel extends JPanel {
    private Image fon;

    public FonPanel(String fonPath){
        setLayout(null);
        setFon(fonPath);
    }

    public FonPanel() {
        this(DEFAULT_PATH);
    }

    public void paintComponent(Graphics g){
        g.drawImage(fon, 0, 0, null);
    }

    public void setFon(String path){
        fon = null;
        try {
            fon = ImageIO.read(new File(path));
        } catch (IOException e) {
            setFon(DEFAULT_PATH);
        }

        repaint();
    }

    public Dimension getDimension(){
        return new Dimension(fon.getWidth(null), fon.getHeight(null));
    }

    private static final String DEFAULT_PATH = "res\\default_table.jpg";
}
