package me.stuntguy3000.java.dragontale.gameState;

import me.stuntguy3000.java.dragontale.tileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private Background background;

    private int currentChoice;
    private String[] options = {"Start", "Help", "Quit"};
    private Font regularFont;
    private Color titleColour;
    private Font titleFont;

    public MenuState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        try {

            background = new Background("/Backgrounds/menubg.gif", 1);
            background.setVector(-0.1, 0);

            titleColour = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);

            regularFont = new Font("Arial", Font.PLAIN, 12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        background.draw(graphics2D);

        graphics2D.setColor(titleColour);
        graphics2D.setFont(titleFont);

        graphics2D.drawString("Dragon Tale", 80, 70);

        graphics2D.setFont(regularFont);

        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                graphics2D.setColor(Color.BLACK);
            } else {
                graphics2D.setColor(Color.RED);
            }

            graphics2D.drawString(options[i], 145, 140 + i * 15);
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void keyPressed(int keyID) {
        if (keyID == KeyEvent.VK_ENTER) {
            select();
        } else if (keyID == KeyEvent.VK_UP) {
            currentChoice--;

            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        } else if (keyID == KeyEvent.VK_DOWN) {
            currentChoice++;

            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int keyID) {

    }

    @Override
    public void update() {
        background.update();
    }

    private void select() {
        if (currentChoice == 0) {
            gameStateManager.setState(GameStateManager.LEVEL1STATE);
        } else if (currentChoice == 1) {
            // Help
        } else if (currentChoice == 2) {
            System.exit(0);
        }
    }
}
    