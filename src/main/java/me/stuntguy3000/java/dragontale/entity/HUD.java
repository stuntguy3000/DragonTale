package me.stuntguy3000.java.dragontale.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
    private Font font;
    private BufferedImage image;
    private Player player;

    public HUD(Player p) {
        player = p;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));

            font = new Font("Arial", Font.PLAIN, 14);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, 5, null);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 20);
        graphics2D.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 40);
    }
}
    