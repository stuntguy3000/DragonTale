package me.stuntguy3000.java.dragontale.gameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    public static final int LEVEL1STATE = 1;
    public static final int MENUSTATE = 0;
    private int currentState;
    private List<GameState> gameStates;

    public GameStateManager() {
        gameStates = new ArrayList<>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
    }

    public void draw(Graphics2D graphics2D) {
        gameStates.get(currentState).draw(graphics2D);
    }

    public void keyPressed(int keyID) {
        gameStates.get(currentState).keyPressed(keyID);
    }

    public void keyReleased(int keyID) {
        gameStates.get(currentState).keyReleased(keyID);
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).initialize();
    }

    public void update() {
        gameStates.get(currentState).update();
    }
}
    