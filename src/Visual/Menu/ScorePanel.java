package Visual.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rogdan on 24.12.2015.
 */
public class ScorePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel digits[];
    private volatile int currentScores;

    public ScorePanel() {
        initScorePanel();
        initScorePanelComponents();
    }

    private void initScorePanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBounds(0, 0, PANEL_DIMENSION.width, PANEL_DIMENSION.height);
    }

    private void initScorePanelComponents(){
        initScoreLabel();
        initScores();
    }

    private void initScoreLabel() {
        scoreLabel = new JLabel();
        scoreLabel.setOpaque(false);
        scoreLabel.setIcon(new ImageIcon(SCORES_PATH + "score.png"));
        add(scoreLabel);
    }

    private void initScores() {
        digits = new JLabel[MAXIMAL_SCORES];

        for (int i = 0; i < MAXIMAL_SCORES; i++) {
            JLabel label = new JLabel();
            label.setOpaque(false);
            digits[i] = label;
            add(label);
        }

        clearScores(0);
    }

    public void addScores(int count){
        currentScores += count;
        setScores(currentScores);
    }

    public void setScores(int scores) {
        currentScores = scores;

        String stringScores = Integer.toString(scores);
        clearScores(stringScores.length());
        for (int i = 0; i < stringScores.length(); i++) {
            String digit = String.valueOf(stringScores.charAt(i));
            digits[i].setIcon(new ImageIcon(DIGITS_PATH + digit + ".png"));
            digits[i].setVisible(true);
            digits[i].repaint();
        }
        repaint();
    }

    public void clearScores(int from) {
        for (int i = from; i < MAXIMAL_SCORES; i++) {
            digits[i].setIcon(null);
        }
    }

    private int getScores() {
        return currentScores;
    }

    public static final int MAXIMAL_SCORES = 8;
    public static final Dimension PANEL_DIMENSION = new Dimension(300 + MAXIMAL_SCORES*70, 70);
    public static String SCORES_PATH = "res\\scores\\";
    public static final String DIGITS_PATH = SCORES_PATH + "Digits\\";
}