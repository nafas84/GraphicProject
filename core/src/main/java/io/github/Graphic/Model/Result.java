package io.github.Graphic.Model;

public class Result {
    private final boolean isSuccessful;
    private final String message;

    public Result(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }
}
