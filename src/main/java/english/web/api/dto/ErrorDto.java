package english.web.api.dto;

import javax.persistence.Column;

public class ErrorDto {

    public ErrorDto(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
