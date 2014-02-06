package mhunterld;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * MWatkins Feb 4, 2014
 */
public class MHunterLD {

    static ArrayList<String> currentFile;
    static ArrayList<Tile> tiledef;
    static Board gameBoard;

    static gamePanel canvas;
    static JFrame frame;
    static final int frameWidth = 1300;
    static final int frameHeight = 800;

    private static void init() {
        currentFile = new ArrayList<>();
        tiledef = new ArrayList<>();
    }

    public static void main(String[] args) {
        init(); 
        try{
        gameBoard = new Board("levels/default.txt");//if in jar
        } catch(Exception e){//if not in jar
            System.out.println("Hi, Mike!");
            gameBoard = new Board("src/mhunterld/levels/default.txt");
        }
        buildGameCanvas();
        canvasControl();
    }

    private static void buildGameCanvas() {
        frame = new JFrame("MHunter Level Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);//lock game resolution
        canvas = new gamePanel(gameBoard);
        frame.setContentPane(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    private static void canvasControl() {
        while (true) {
            canvas.repaint();
            pause();

        }
    }

    private static void pause() {
        try {
            Thread.sleep(12); // wait 5ms
        } catch (Exception e) {
            System.out.println("paint error");
        }
    }
}
