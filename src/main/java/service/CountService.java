package service;


import Words.WordAndCount;
//import org.omnifaces.util.Servlets;

import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

@ApplicationScoped
@ManagedBean

public class CountService {

/*    public List<Part> getFiles() {
        return files;
    }

    public void setFiles(List<Part> files) {
        this.files = files;
    }

    private List<Part> files; // +getter+setter

    public void upload() throws IOException {
        if (files != null) {
            for (Part file : files) {
                String name = Servlets.getSubmittedFileName(file);
                String type = file.getContentType();
                long size = file.getSize();
                InputStream content = file.getInputStream();
                // ...
            }
        }
    }*/
    private String wordsOutHtml = "";// text for html output
    private String wordsOutFormatted = ""; // text for file output
    private String encoding2;
    private String fileNameSafeTo;
    private String directory;
    private Part part2;
    private String typingDir = "C:/Users/Q/Desktop/Typing/";
    private File[] listOfFiles;

  //  private Boolean simpleWordListBox;
    private Boolean wordListWithCapsBox;
    private Boolean wordListWithNumbersBox;
    private Boolean wordListWithSpecials1Box;
    private Boolean wordListWithSpecials2Box;

    private ArrayList<WordAndCount> wordlistModified;

    public String getFileNameSafeTo() {
        return fileNameSafeTo;
    }

    public void setFileNameSafeTo(String fileNameSafeTo) {
        this.fileNameSafeTo = fileNameSafeTo;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getEncoding2() {
        return encoding2;
    }

    public void setEncoding2(String encoding2) {
        this.encoding2 = encoding2;
    }

    public Part getPart2() {
        return part2;
    }

    public void setPart2(Part part2) {
        this.part2 = part2;
    }

    public String getWordsOutHtml() {
        return wordsOutHtml;
    }

    public void setWordsOutHtml(String wordsOutHtml) {
        this.wordsOutHtml = wordsOutHtml;
    }

    public Boolean getWordListWithCapsBox() {
        return wordListWithCapsBox;
    }

    public void setWordListWithCapsBox(Boolean wordListWithCapsBox) {
        this.wordListWithCapsBox = wordListWithCapsBox;
    }

    public Boolean getWordListWithNumbersBox() {
        return wordListWithNumbersBox;
    }

    public void setWordListWithNumbersBox(Boolean wordListWithNumbersBox) {
        this.wordListWithNumbersBox = wordListWithNumbersBox;
    }

    public Boolean getWordListWithSpecials1Box() {
        return wordListWithSpecials1Box;
    }

    public void setWordListWithSpecials1Box(Boolean wordListWithSpecials1Box) {
        this.wordListWithSpecials1Box = wordListWithSpecials1Box;
    }

    public Boolean getWordListWithSpecials2Box() {
        return wordListWithSpecials2Box;
    }

    public void setWordListWithSpecials2Box(Boolean wordListWithSpecials2Box) {
        this.wordListWithSpecials2Box = wordListWithSpecials2Box;
    }


    //*************************************//

    public void printWordsToPage() throws IOException {


        //****First we look if there are files in directory (and directory is not omitted in the form)****//
        if (directory != null && directory.length() > 0) {
            String pathRead = typingDir + directory;
            File folder = new File(pathRead);
            listOfFiles = folder.listFiles();

        }
        String composedTextList = "";
        ArrayList<WordAndCount> composedWordlist = new ArrayList<WordAndCount>();
        if (listOfFiles != null && listOfFiles.length > 0) {

            for (File file : listOfFiles) {
                // System.out.println(file.getName());
                if (file.isFile()) {
                    ArrayList<WordAndCount> thisFileList = new Frequences().wordsWhiteSpaces(file, encoding2);

                    //Addind only first 1000 most common words from each file:
                    composedTextList += addUpto1000Words(thisFileList);
                }
            }

            composedWordlist = new Frequences().getWords(composedTextList);
        }

        ArrayList<WordAndCount> wordlist = new ArrayList<WordAndCount>();


        if (composedTextList != null && !composedTextList.isEmpty()) {
            wordlist = composedWordlist;

            //****Second if there are files in directory ww kook if there is a file uploaded ****//

        } else if (part2 != null) {
            wordlist = new Frequences().wordsWhiteSpaces(part2, encoding2);
        }


        if (!wordlist.isEmpty()) {
            wordlist.sort(Comparator.comparing(WordAndCount::getCount).reversed());


            htmlOutPut(wordlist);
            fileOutPut(wordlist);

        }
    }

    private void fileOutPut(ArrayList<WordAndCount> wordlist) throws IOException {

        wordlistModified = wordlist;

        if (wordListWithCapsBox) {
            wordlistModified = getListWithCaps(wordlistModified, 7);
        }

        if (wordListWithNumbersBox){
            wordlistModified = getListWithNumbers(wordlistModified, 3);
        }

        if (wordListWithSpecials1Box){
            wordlistModified = getListWithSpecials1(wordlistModified, 16);
        }

        if (wordListWithSpecials2Box){
            wordlistModified = getListWithSpecials2(wordlistModified, 16);
        }

        wordsOutFormatted = getWordsOutSimpleFormat(wordlistModified);

        if (fileNameSafeTo != null && fileNameSafeTo.length() > 0) {
            String path = typingDir + fileNameSafeTo;
            Files.write(Paths.get(path + "2"+".txt"), wordsOutFormatted.getBytes());
        }

    }

    private ArrayList<WordAndCount> getListWithSpecials2(ArrayList<WordAndCount> listMoreSpecials, int frequency) {
        for (WordAndCount wac :listMoreSpecials) {
            int oneXXRandom = (int) ((Math.random() * (frequency - 1)) + 1);
            String ss = wac.getWord();
            switch (oneXXRandom) {
                case 1:
                    ss = "{" + ss ; ss = ss+ "}";
                    break;

                    case 2:
                    ss =  "[" + ss; ss = ss+ "]";
                    break;
                case 3:
                    ss = "(" + ss;  ss = ss+ ")";
                    break;
                case 4:
                    ss =  "<" + ss;  ss = ss+ ">";
                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
            }
            wac.setWord(ss);
        }
        return listMoreSpecials;
    }

    private ArrayList<WordAndCount> getListWithSpecials1(ArrayList<WordAndCount> listForFewSpecials, int frequency) {

        for (WordAndCount wac : listForFewSpecials) {
            int oneFifthRandom = (int) ((Math.random() * (frequency - 1)) + 1);
            String ss = wac.getWord();
            if (oneFifthRandom == 1) {
                ss = ss+ ".";
            }
            if (oneFifthRandom == 2) {
                ss = ss+ ",";
            }
            wac.setWord(ss);
        }
        return listForFewSpecials;
    }

    private ArrayList<WordAndCount> getListWithNumbers(ArrayList<WordAndCount> listForNumbers, int frequency) {

    int amounOfNumbers = (int) (listForNumbers.size())/(frequency-1);
    for (int i=0; i< amounOfNumbers; i++){
        int position = i*frequency;
        if (position< listForNumbers.size()) {
            Integer randomNumberXXX = (int) ((Math.random() * (999)));
            listForNumbers.add(position, new WordAndCount(randomNumberXXX.toString(), 1));
        }
    }

    return listForNumbers;
    }

    private ArrayList<WordAndCount> getListWithCaps(ArrayList<WordAndCount> listForCaps, int frequency) {
        for (WordAndCount wac :listForCaps) {
            int oneFifthRandom = (int) ((Math.random() * (frequency - 1)) + 1);
            if (oneFifthRandom == 3) {
                String ss = wac.getWord();
                ss = ss.substring(0, 1).toUpperCase() + ss.substring(1);
                wac.setWord(ss);
            }
        }
        return listForCaps;
    }

/*    private ArrayList deepCloneList(ArrayList<WordAndCount> wordlist) {
        ArrayList <WordAndCount> tList = new ArrayList<>();
        for (WordAndCount wac : wordlist) {
           tList.add(new WordAndCount (wac.getWord(),wac.getCount()));
        }
        return tList;
    }*/

    private String getWordsOutSimpleFormat(ArrayList<WordAndCount> wordList) {
        String textSimpleFormat= "";
        for (WordAndCount wac : wordList) {
            textSimpleFormat += wac.getWord() + "|";
        }
        return textSimpleFormat;
    }



    private void htmlOutPut(ArrayList<WordAndCount> wordlist) {
        wordsOutHtml="";
        for (WordAndCount wac : wordlist) {
            wordsOutHtml += wac.getWord() + " " + wac.getCount() + "<br />"; //todo change to <p>
        }
    }



    //********                procedures:                *********//


    //Addind only first 1000 most common words from each file:
    private String addUpto1000Words(ArrayList<WordAndCount> thisFileList) {
        String list = "";
        int i = 0;
        for (WordAndCount wac : thisFileList) {
            i++;
            if (i < 1000) {
                list += wac.getWord() + "|";
            }
        }
        return list;
    }
}


