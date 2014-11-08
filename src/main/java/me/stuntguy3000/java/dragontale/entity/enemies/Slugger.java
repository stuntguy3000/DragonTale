package me.stuntguy3000.java.dragontale.entity.enemies;

import me.stuntguy3000.java.dragontale.entity.Animation;
import me.stuntguy3000.java.dragontale.entity.Enemy;
import me.stuntguy3000.java.dragontale.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Slugger extends Enemy {

    private BufferedImage[] sprites;

    public Slugger(TileMap tileMap) {
        super(tileMap);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 2;
        damage = 1;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));

            sprites = new BufferedImage[3];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
    }

    public void draw(Graphics2D graphics2D) {
        /*if (notOnScreen()) {
            return;
        }*/

        setMapPosition();
        super.draw(graphics2D);
    }

    private void getNextPosition() {
        if (left) {
            dX -= moveSpeed;

            if (dX < -maxSpeed) {
                dX = -maxSpeed;
            }
        } else if (right) {
            dX += moveSpeed;

            if (dX > maxSpeed) {
                dX = maxSpeed;
            }
        }

        if (falling) {
            dY += fallSpeed;
        }
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        if (flinching) {
            long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;

            if (elapsed > 400) {
                flinching = false;
            }
        }

        if (right && dX == 0) {
            right = false;
            left = true;
        } else if (left && dX == 0) {
            right = true;
            left = false;
        }
    }
}
    