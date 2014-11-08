package me.stuntguy3000.java.dragontale.tileMap;

import me.stuntguy3000.java.dragontale.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {
    private double dX;
    private double dY;
    private BufferedImage image;
    private double moveScale;
    private double x;
    private double y;

    public Background(String name, double moveScale) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(name));

            this.moveScale = moveScale;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            graphics2D.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }

        if (x > 0) {
            graphics2D.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    public void setVector(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public void update() {
        x += dX;
        y += dY;
    }
}
    