package homework_2.data.enums;

public enum ErrorTextsForLogin {

    MISSING_PASSWORD("Missing password"),
    MISSING_EMAIL_OR_USERNAME("Missing email or username"),
    USER_NOT_FOUND("user not found");

    private String text;

    ErrorTextsForLogin(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}