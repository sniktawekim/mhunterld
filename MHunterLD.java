package mhunterld;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * MWatkins Feb 4, 2014
 */
public class MHunterLD {

    static MPanel canvas;
    static CharacterPanel charCanvas;
    static JFrame frame;
    static final int frameWidth = 1300;
    static final int frameHeight = 800;
    static String currentCanvas = "menu";

    private static void init() {
        buildCanvas();
        canvasControl();
    }

    public static void main(String[] args) {
        init();

    }

    private static void buildCanvas() {
        frame = new JFrame("Mike's Awesome Game, yo!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);//lock game resolution
        determineCanvas();
        frame.setContentPane(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    private static void canvasControl() {
        changeCursor();
        while (canvas.update().compareToIgnoreCase("good") == 0) {
            canvas.repaint();
            pause();
        }
        currentCanvas = canvas.update();
        determineCanvas();
        frame.dispose();
        frame = null;
        buildCanvas();
        canvasControl();
    }

    private static void pause() {
        try {
            Thread.sleep(5); // wait 5ms
        } catch (Exception e) {
            System.out.println("paint error");
        }
    }

    private static void determineCanvas() {
        if (currentCanvas.compareToIgnoreCase("menu") == 0) {
            canvas = new MenuPanel();
        }
        if (currentCanvas.compareToIgnoreCase("ld") == 0) {
            canvas = new LDPanel();
        }
        if (currentCanvas.compareToIgnoreCase("chars") == 0) {
            canvas = new CharacterPanel();
        }
        if (currentCanvas.compareToIgnoreCase("play") == 0) {
            canvas = new GamePanel();
        }
    }

    private static void changeCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image;
        try {
            //just to see if we are in the jar or not:
            Board currentBoard = new Board("levels/default.lvl");
            image = toolkit.getImage("pics/hud/cursor/cursor.png");
        } catch (Exception e) {
            image = toolkit.getImage("src/mhunterld/pics/hud/cursor/cursor.png");
        }
        Cursor c = toolkit.createCustomCursor(image, new Point(frame.getX() + 3,
                frame.getY() + 3), "img");
        frame.setCursor(c);
    }
}
