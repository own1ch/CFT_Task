package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class MergeSort {

    private HashMap<String, ReadFile> inputFiles = new HashMap<>();
    private String mode;
    private String typeOfData;
    private String lastLine = null;
    private int lastInt;

    MergeSort(String sortMode, String dataType, String outputFileName, ArrayList<String> inFiles){ // В конструкторе определяем тип сортировки
        mode = sortMode;
        typeOfData = dataType;
        if(dataType.equals("integer")){
            if(sortMode.equals("asc")){
                lastInt = Integer.MIN_VALUE;
            }else{
                lastInt = Integer.MAX_VALUE;
            }
        }
        sortArray(outputFileName, inFiles);
    }

    private void deleteFile() {
        File out = new File("out.txt");
        if(out.exists()) {
            out.delete();
        }
    }

    private void sortArray(String outputFileName, ArrayList<String> inFiles){ // Здесь файл на выход и создаем объекты класса ReadFile для каждого имени файла из аршументов
        for (String fileName : inFiles) {
            ReadFile file = new ReadFile(fileName);
            inputFiles.put(fileName, file);
        }
        if (typeOfData.equals("integer")) {
            getDataInt();
        } else if (typeOfData.equals("string")) {
            getDataStr();
        }
    }
    private int getIntElement(HashMap<Integer, ReadFile> sortArray) { // Достаю минимальный элемент с мапы которую вкидывает метод сорт и увеличиваю каунтер нужного файла на 1.
        Map.Entry<Integer, ReadFile> minEntry = null;
        for (Map.Entry<Integer, ReadFile> entry : sortArray.entrySet()) {
            if (mode.equals("asc")) {
                if (minEntry == null || entry.getKey() < minEntry.getKey()) {
                    minEntry = entry;
                }
            } else {
                if (minEntry == null || entry.getKey() > minEntry.getKey()) {
                    minEntry = entry;
                }
            }
        }
        assert minEntry != null;
        ReadFile someFile = minEntry.getValue();
        someFile.incrementCounter();
        return minEntry.getKey();
    }
    private String getStrElement(HashMap<String, ReadFile> sortArray){ // Достаю минимальный элемент с мапы которую вкидывает метод сорт и увеличиваю каунтер нужного файла на 1.
        Map.Entry<String, ReadFile> minEntry = null;
        for (Map.Entry<String, ReadFile> entry : sortArray.entrySet()) {
            if (mode.equals("asc")) {
                if (minEntry == null || checkLine(entry.getKey(), minEntry.getKey())) {
                    minEntry = entry;
                }
            } else {
                if (minEntry == null || !checkLine(entry.getKey(), minEntry.getKey())) {
                    minEntry = entry;
                }
            }
        }
        assert minEntry != null;
        ReadFile someFile = minEntry.getValue();
        someFile.incrementCounter();
        return minEntry.getKey();
    }

    private boolean checkLine(String firstString, String secondString){
        String lowerLine = null;
        boolean result = false;

        if(firstString.length() <= secondString.length()){
            lowerLine = firstString;
            result = true;
        } else{
            lowerLine = secondString;
        }

        for(int i = 0 ; i < lowerLine.length(); i++){
            int firstCharacter = (int) firstString.charAt(i);
            int secondCharacter = (int) secondString.charAt(i);

            if (firstCharacter < secondCharacter){
                return true;
            } else if(firstCharacter > secondCharacter){
                return false;
            }
        }
        return result;
    }

    private void getDataInt(){
        HashMap<Integer, ReadFile> values = new HashMap<>();
        while(true){
            values.clear();
            for (ReadFile file: inputFiles.values()){
                if (file.isEof()) {
                    if(isNumeric(file.getElement())) {
                        int element = Integer.parseInt(file.getElement());
                        values.put(element, file);
                    }else{
                        file.incrementCounter();
                    }
                }
            }
            if (values.size() == 0){
                return;
            }
            writeOutputInt(values);
        }
    }
    private void getDataStr(){
        HashMap<String, ReadFile> values = new HashMap<>();
        while(true){
            values.clear();
            for (ReadFile file: inputFiles.values()){
                if (file.isEof()) {
                    String element = file.getElement();
                    values.put(element, file);
                }else{
                    file.incrementCounter();
                }
            }
            if (values.size() == 0){
                return;
            }
            writeOutputStr(values);
        }
    }
    private void writeOutputInt(HashMap<Integer, ReadFile> values) { //рекурсивный метод который считывает строку каждого файла в соответствии с каунтером и пихает в мапу, которая в последствии улетает в метод сверху ЫЫы
        try(FileWriter output = new FileWriter("out.txt", true)) {
            int element = 0;
            element = getIntElement(values);

            if(mode.equals("asc")){
                if(element >= lastInt){
                    lastInt = element;
                    System.out.println(element);
                    output.write(Integer.toString(element) + "\n");
                }
            }else{
                if(element <= lastInt){
                    lastInt = element;
                    System.out.println(element);
                    output.write(Integer.toString(element) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeOutputStr(HashMap<String, ReadFile> values) { //рекурсивный метод который считывает строку каждого файла в соответствии с каунтером и пихает в мапу, которая в последствии улетает в метод сверху ЫЫы
        try(FileWriter output = new FileWriter("out.txt", true)) {
            String element = null;
            element = getStrElement(values);

            if(mode.equals("asc")){
                if(lastLine == null || checkLine(lastLine, element)){
                    lastLine = element;
                    System.out.println(element);
                    output.write((element) + "\n");
                }
            }else{
                if(lastLine == null || checkLine(element, lastLine)){
                    lastLine = element;
                    System.out.println(element);
                    output.write((element) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNumeric(String str)
    {
        try{
            Integer.parseInt(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
