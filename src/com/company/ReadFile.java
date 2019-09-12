package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFile {
    private String fileName;
    private int counter = 0;
    ReadFile(String name){
        fileName = name;
    }

    String getElement() {
        String line = null;
        String ansiString = null;
        try {
            FileReader fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (Stream<String> linesToRead = Files.lines(Paths.get(fileName), StandardCharsets.ISO_8859_1)) {
            line = linesToRead.skip(counter).findFirst().get();
            ansiString = new String(line.getBytes(StandardCharsets.ISO_8859_1), "windows-1251");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ansiString;
    }

    boolean isEof() {
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(fileName), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.skip(counter).findFirst().isPresent();
    }

    void incrementCounter() {
        this.counter++;
    }

    public String getFileName() {
        return fileName;
    }
}
