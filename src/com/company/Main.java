package com.company;

import com.sun.source.tree.ContinueTree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static private String dataType;
    static private String outputFileName;
    static private ArrayList<String> inFiles = new ArrayList<>();

    public static void main(String[] args) { // Читаем аргументы, в тз глянь как надо шоб было, логика простейшая, но мб ты знаешь как это сделать красивее)
        int i = 0, j =0;
        String sortMode;
        long startTime = System.currentTimeMillis();
        if (args[0].equals("-a")){
            sortMode = "asc";
            i++;
        } else if(args[0].equals("-d")){
            sortMode = "desc";
            i++;
        } else{
            sortMode = "asc";
        }
        if(args[i].equals("-s")){
            dataType = "string";
            outputFileName = args[i+1];
            i+=2;
        } else if(args[i].equals("-i")){
            dataType = "integer";
            outputFileName = args[i+1];
            i+=2;
        } else {
            System.out.println("Parameters error.");
            System.exit(0);
        }
        for (j = i; j < args.length; j++) {
            inFiles.add(args[j]);
        }
        MergeSort newSort = new MergeSort(sortMode, dataType, outputFileName, inFiles); // Стратуем сортировку
        long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Память: " + usedBytes / 1048576 + " MB");
        System.out.println("Время: " + (System.currentTimeMillis() - startTime));
    }
}
