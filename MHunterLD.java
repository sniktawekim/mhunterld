package mhunterld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * MWatkins Feb 4, 2014
 */
public class MHunterLD {

    static ArrayList currentFile;
    static BufferedReader br = null;

    public static void main(String[] args) {
        readFile("default.txt");
    }

   static private void readFile(String path) {
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                currentFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            System.out.println("Error in " + path + ": " + e);
        }
    }
}
