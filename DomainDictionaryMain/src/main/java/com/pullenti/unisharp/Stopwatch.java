package com.pullenti.unisharp;

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public void reset() {
	    this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public long getElapsedMilliseconds() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public java.time.Duration getElapsed() {
        long elapsed;
        if (running) {
            elapsed = System.currentTimeMillis() - startTime;
        } else {
            elapsed = stopTime - startTime;
        }
        return java.time.Duration.ofMillis(elapsed);
    }
}