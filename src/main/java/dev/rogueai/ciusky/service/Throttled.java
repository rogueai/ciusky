package dev.rogueai.ciusky.service;

import java.util.Optional;

public class Throttled<T> {

    public Optional<T> model;

    public int timeToWaitPercent;

    public Throttled(T model, int timeToWaitPercent) {
        this.model = Optional.ofNullable(model);
        this.timeToWaitPercent = timeToWaitPercent;
    }
}
