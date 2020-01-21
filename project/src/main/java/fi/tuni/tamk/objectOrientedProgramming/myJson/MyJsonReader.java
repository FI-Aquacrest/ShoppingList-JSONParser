package fi.tuni.tamk.objectOrientedProgramming.myJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads the file in a received path and returns it as a one-line String.
 */
public class MyJsonReader {
    /**
     * Reads the file in a given path and returns it as a single-line String.
     *
     * @param file File to be read.
     * @return The received file in a single-line String.
     * @throws IOException If there are issues with reading the file.
     */
    public static String fileToOneLine(File file) throws IOException {
        String oneLine = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            oneLine += line.trim();
        }

        br.close();
        return oneLine;
    }
}
