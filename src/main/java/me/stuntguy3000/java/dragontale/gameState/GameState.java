package me.stuntguy3000.java.dragontale.gameState;

import java.awt.*;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public abstract void draw(Graphics2D graphics2D);

    public abstract void initialize();

    public abstract void keyPressed(int keyID);

    public abstract void keyReleased(int keyID);

    public abstract void update();
}
    