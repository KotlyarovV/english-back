package english.web.exceptions;

public class WordIsNotOwnedByUserException extends Exception {
    public WordIsNotOwnedByUserException() {
        super("Word is not owned by user");
    }
}
