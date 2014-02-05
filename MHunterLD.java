package mhunterld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * MWatkins Feb 4, 2014
 */
public class MHunterLD {

    static ArrayList<String> currentFile;
    static ArrayList<Tile> tiledef;
    static BufferedReader br = null;
    static int startX = 100;
    static int startY = 100;
    static Board board;
    static gamePanel canvas;
    static JFrame frame;
    static final int frameWidth = 1300;
    static final int frameHeight = 800;

    static int boardLeft=11;
    static int boardRight=11;
    static String boardTitle;

    private static void init() {
        currentFile = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        init();
        readFile("src/mhunterld/levels/default.txt");
        buildFromFile();
        buildGameCanvas();
        canvasControl();
    }

    static private void readFile(String path) throws IOException {
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                currentFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            System.out.println("Error in " + path + ": " + e);
        }
        br.close();
    }

    private static void buildFromFile() {
        for (int i = 0; i < currentFile.size(); i++) {
            String cLine = currentFile.get(i);
            if (cLine.contains("<def>")) {
                i = buildDef(i);
            }
            if (cLine.contains("<board>")) {
                i = buildBoard(i);
            }
        }
        System.out.println("Finished reading  level file");
    }

    private static int buildDef(int i) {
        System.out.println("building Def");
        String cLine = currentFile.get(i);
        while (!cLine.contains("</def>")) {
            if (cLine.contains("<tiledef>")) {
                i = buildTileDef(i);
            }
            if (cLine.contains("<bgidef>")) {
                i = setBGI(i);
            }
            i++;
            cLine = currentFile.get(i);
        }
        return i;
    }

    private static int buildBoard(int i) {
        String cLine = currentFile.get(i);
        while (!cLine.contains("</board>")) {
            if (cLine.contains("<title>")) {
                cLine = removeXML(cLine);
                boardTitle = cLine;
            }
            if (cLine.contains("<width>")) {
                cLine = removeXML(cLine);
                boardLeft = Integer.parseInt(cLine);
                System.out.println(cLine);
            }
            if(cLine.contains("<height>")){
                cLine = removeXML(cLine);
                boardRight = Integer.parseInt(cLine);
                System.out.println(cLine);
            }
            i++;
            cLine = currentFile.get(i);
        }
        return i;
    }

    private static int buildTileDef(int i) {
        String cLine = currentFile.get(i);
        while (!cLine.contains("</tiledef>")) {

            i++;
            cLine = currentFile.get(i);
        }

        return i;
    }

    private static int setBGI(int i) {
        System.out.println("setting background image");
        return i;
    }

    private static void buildGameCanvas() {
        frame = new JFrame("MHunter Level Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);//lock game resolution
        canvas = new gamePanel();
        sendBoardSettings();      
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

    private static String removeXML(String toremove) {
        toremove = toremove.trim();
        int startpoint = toremove.indexOf(">") + 1;
        int endpoint = toremove.indexOf("</");
        toremove = toremove.substring(startpoint, endpoint);
        return toremove;
    }

    private static void sendBoardSettings() {
        canvas.setBoardSize(boardLeft, boardRight);
    }

}
