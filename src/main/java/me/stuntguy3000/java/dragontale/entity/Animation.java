package me.stuntguy3000.java.dragontale.entity;

import java.awt.image.BufferedImage;

public class Animation {
    private int currentFrame;
    private long delay;
    private BufferedImage[] frames;
    private boolean playedOnce;
    private long startTime;

    public Animation() {
        playedOnce = false;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public BufferedImage[] getFrames() {
        return frames;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean hasPlayedOnce() {
        return playedOnce;
    }

    public void setFrame(int id) {
        currentFrame = id;
    }

    public void update() {
        if (delay == -1) {
            return;
        }

        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }

        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }
}
    