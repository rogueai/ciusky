package dev.rogueai.ciusky.controller;

/**
 * Custom event to display a toast message.
 */
public class ToastMessage {

    public boolean success;

    public String message;

    public String detail;

    public ToastMessage(boolean success, String message, String detail) {
        this.success = success;
        this.message = message;
        this.detail = detail;
    }
}
