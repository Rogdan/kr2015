package Visual;

import Logic.Game;
import Logic.Switcher;
import SoundMaker.Sound;
import Visual.Menu.NotificationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;

public class MainFrame extends JFrame{
    private FonPanel fon;
    private Dimension programDimension, screenDimension;
    public static Switcher switcher;
    private Game game;
    public static MyCursorThread myCursorThread;
    public static NotificationPanel notificationPanel;

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public MainFrame(){
        initAll();
    }

    private void initAll(){
        initMainFrame();
        initCursor();
        initSwitcher();
        initNotificationPanel();
        initGame();
    }

    private void initMainFrame(){
        setTitle("Smash the cockroach");
        setIcon();
        initFon();
        setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        dispose();

        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initCursor(){
        myCursorThread = new MyCursorThread(this);
        add(myCursorThread);
        int[] pixels = new int[16 * 16];
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor (image, new Point(10, 10), "invisibleCursor");
        setCursor(transparentCursor);
        getGlassPane().setCursor(transparentCursor);
        getGlassPane().setVisible(true);
    }

    private void initSwitcher() {
        switcher = new Switcher(this);
    }

    private void initNotificationPanel(){
        notificationPanel = new NotificationPanel(programDimension);
        add(notificationPanel);
    }

    private void initGame(){
        game = new Game(switcher.getGameBoard());
        switcher.getGameMenuPanel().addObserver(game);
        switcher.getGameBoard().addObserver(game);
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
        screenDimension.setSize(screenDimension.getWidth(), screenDimension.getHeight());

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

    public void setFon(String path) {
        fon.setFon(path);
        checkFonDimension();
        fon.repaint();

        programDimension = fon.getDimension();
        setSize(programDimension);
    }

    public Dimension getProgramDimension(){
        return programDimension;
    }

    private static final String iconPath = "res\\icon.png";
}