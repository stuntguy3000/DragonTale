package me.stuntguy3000.java.dragontale;

import me.stuntguy3000.java.dragontale.gameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    public static final int WIDTH = 320;
    private int framesPerSecond = 60;
    private long targetTime = 1000 / framesPerSecond;
    private GameStateManager gameStateManager;
    private Thread gameThread;
    private Graphics2D graphics2D;
    private BufferedImage image;
    private boolean running;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        addKeyListener(this);

        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void draw() {
        gameStateManager.draw(graphics2D);
    }

    private void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        graphics.dispose();
    }

    private void initialize() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = (Graphics2D) image.getGraphics();

        running = true;

        gameStateManager = new GameStateManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameStateManager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameStateManager.keyReleased(e.getKeyCode());
    }

    @Override
    public void run() {
        initialize();

        long start;
        long elapsed;
        long wait;

        while (running) {
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        gameStateManager.update();
    }
}
    