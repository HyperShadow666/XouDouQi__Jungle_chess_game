package com.ensah.game1.bll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {
    public static void main(String[] args) {
        String filePath = "note.txt";
        String ok [] = processLastLine(filePath);
        char[] inputs = ok[0].toCharArray();
        //System.out.println(inputs[0]);
        int p1 = Character.getNumericValue(inputs[0]);
        System.out.println(p1);
    }

    public static String[] processLastLine(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String lastLine = null;

            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine != null) {
                String[] parts = lastLine.split("\\.");

                return parts;
            } else {
                System.out.println("The file is empty.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }
}
