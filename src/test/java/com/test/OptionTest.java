package com.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OptionTest {
    public static void main(String[] args) {
        //
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/mkrao/Desktop/Oncamigo/option"));
            String line = reader.readLine();
            int counter = 1;
            while (line != null) {
                line = line.replace("Malli", counter + "");
                System.out.println(line);
                counter++;
                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
