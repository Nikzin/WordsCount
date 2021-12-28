package Words;


import java.util.ArrayList;

public class Dictionary {
    private ArrayList <WordAndCount> wordlist= new ArrayList<>();


    public ArrayList<WordAndCount> getWordlist() {
        return wordlist;
    }

    public void setWordlist(ArrayList<WordAndCount> wordlist) {
        this.wordlist = wordlist;
    }


}
