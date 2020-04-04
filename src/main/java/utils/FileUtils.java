package main.java.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readAll(File file) {
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            List<String> lines = new ArrayList<String>();
            String s = buf.readLine();
            while (s != null) {
                lines.add(s);
                s = buf.readLine();
            }
            return lines;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
