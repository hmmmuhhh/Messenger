package org.mziuri.validation;

public class Validation {
    private static Validation instance;

    private Validation() {}

    public static synchronized Validation getInstance() {
        if (instance == null) instance = new Validation();
        return instance;
    }

    public boolean isMessageValid(String message) {
        return !message.contains("\n");
    }
}