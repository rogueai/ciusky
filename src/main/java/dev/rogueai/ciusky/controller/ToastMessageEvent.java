package dev.rogueai.ciusky.controller;

public class ToastMessageEvent {

    public String html;

    public int timeout;

    public ToastMessageEvent(String html, int timeout) {
        this.html = html;
        this.timeout = timeout;
    }
}
