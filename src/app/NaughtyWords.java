package app;

import java.util.Arrays;
import java.util.List;

/**
 * Set up a list of bad words that can be used to disallow searching of these, to protect the children using the application
 */
public class NaughtyWords {

    private String words;
    private List<String> manualWords;

    // Constructor allows programmer to set their own list of banned words
    public NaughtyWords(String words) {
        this.words=words;
        this.manualWords = Arrays.asList(words.split(","));
    }

    // Return the list of banned words that were specified by programmer
    public List<String> getOwnBadWordsList() {
        return this.manualWords;
    }

    // Return a list of typical/regular banned words built in
    public static List<String> getRegularBadWordsList() {
        String word = "fuck,shit,faggot,damn,crap,sex,fag,ass,dick,pussy,";
        return Arrays.asList(word.split(","));
    }
}
