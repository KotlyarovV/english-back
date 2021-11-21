package english.web.exceptions;

public class WordNotFoundException extends Exception {
    public WordNotFoundException() {
        super("Word not exists");
    }
}
