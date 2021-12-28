package service;


import Words.WordAndCount;

import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;

public class Frequences {

    public void addWord (ArrayList<WordAndCount> words1, String word1) {
        Boolean inList = false;
        for (WordAndCount ww : words1) {
            if (ww.getWord().equals(word1)) {
                int i = ww.getCount();
                i++;
                ww.setCount(i);
                inList = true;
            }
        }
        if (!inList) {words1.add (new WordAndCount(word1, 1)); }//
    }
    public ArrayList <WordAndCount>  wordsWhiteSpaces (Part file, String encoding) throws IOException {
        InputStream input = file.getInputStream();
        return getWords (input, encoding);
    }

    public ArrayList <WordAndCount>  wordsWhiteSpaces (File file, String encoding) throws IOException {
        InputStream input = new FileInputStream(file);
        return getWords (input, encoding);
    }

    public ArrayList <WordAndCount>  getWords ( InputStream input, String encoding) throws IOException {
        ArrayList <WordAndCount> words = new ArrayList<WordAndCount>();
        //System.out.println("Encoding "+encoding+" whatever");

        if ((encoding == null) || (encoding.length()<1)) {encoding="UTF8";}




                BufferedReader br = new BufferedReader(new InputStreamReader(input, encoding));

                String line;

            while ((line = br.readLine()) != null) {
            //    line=line.replaceAll("([A-Z])", "").toLowerCase();
                line=line.toLowerCase();
                String[] arr = line.split("[\\s,.\\|()?;!:;\"]+");

                for ( String ss : arr) {
                    //this regex means: not contain any other letter apart of a-zа-яöäå
                    if(ss.matches("[a-zа-яåöäZ]+")){
                       // ss = ss.substring(0, 1).toUpperCase() + ss.substring(1); // temporal capitalize all words
                        addWord(words, ss);
                    }
                }
            }

            return words;
    }

    public ArrayList <WordAndCount>  getWords ( String text)  {
        ArrayList <WordAndCount> words = new ArrayList<WordAndCount>();
        String encoding="UTF8";
            text=text.toLowerCase();
            String[] arr = text.split("[\\s,.\\|()?;!:;\"]+");
            for ( String ss : arr) {
                //this regex means: not contain any other letter apart of a-zа-яöäå
                if(ss.matches("[a-zа-яåöäZ]+")){
                 //   ss = ss.substring(0, 1).toUpperCase() + ss.substring(1); // temporal capitalize all words
                    addWord(words, ss);
                }
            }
            return words;
    }


}