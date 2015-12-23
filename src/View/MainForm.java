package View;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame{
    private FonPanel fon;
    private Menu menu;
    private Dimension programDimension, screenDimension;
    private boolean isGameRun, isMenuCockroachesRun;

    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
    }

    public MainForm(){
        initAll();
    }

    private void initAll(){
        initMainFrame();
        initMenuAndRunMenuTarakans();
    }

    private void initMainFrame(){
        setTitle("Smash the cockroach");
        setIcon();
        initFon();

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initFon(){
        fon = new FonPanel();
        checkFonDimension();

        setContentPane(fon);
        setSize(programDimension);
    }

    private void checkFonDimension(){
        programDimension = fon.getDimension();

        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenDimension.setSize(screenDimension.getWidth(), screenDimension.getHeight() - 10);

        if (screenDimension.getHeight() < programDimension.getHeight())
            programDimension.setSize(programDimension.getWidth(), screenDimension.getHeight());

        if (screenDimension.getWidth() < programDimension.getWidth())
            programDimension.setSize(screenDimension.getWidth(), programDimension.getHeight());
    }

    private void setIcon(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage(iconPath);
        setIconImage(icon);
    }

    private void initMenuAndRunMenuTarakans(){
        menu = new Menu(programDimension, this);
        add(menu);
        menu.initMenuCockroaches();
        setMenuCockroachesRun(true);
    }

    private void setFon(String path){
        fon.setFon(path);
        checkFonDimension();
        fon.repaint();

        programDimension = fon.getDimension();
        setSize(programDimension);
    }

    public void setGameRun(boolean isGameRun){
        this.isGameRun = isGameRun;
    }

    public void setMenuCockroachesRun(boolean isMenuCockroachesRun){

        this.isMenuCockroachesRun = isMenuCockroachesRun;
    }

    public void paint(Graphics g){
        fon.repaint();
        menu.repaint();

        if (isMenuCockroachesRun) {
            menu.updateMenuCockroaches();
        }
        else
        if (isGameRun){
            //todo
        }
    }

    private static final String iconPath = "res\\icon.png";
}
