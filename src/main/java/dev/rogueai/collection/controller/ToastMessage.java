package dev.rogueai.collection.controller;

/**
 * Custom event to display a toast message.
 */
public class ToastMessage {

    public boolean success;

    public String message;

    public ToastMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
