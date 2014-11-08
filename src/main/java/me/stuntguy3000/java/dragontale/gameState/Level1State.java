package me.stuntguy3000.java.dragontale.gameState;

import me.stuntguy3000.java.dragontale.GamePanel;
import me.stuntguy3000.java.dragontale.entity.Enemy;
import me.stuntguy3000.java.dragontale.entity.Explosion;
import me.stuntguy3000.java.dragontale.entity.HUD;
import me.stuntguy3000.java.dragontale.entity.Player;
import me.stuntguy3000.java.dragontale.entity.enemies.Slugger;
import me.stuntguy3000.java.dragontale.tileMap.Background;
import me.stuntguy3000.java.dragontale.tileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background background;

    private List<Enemy> enemies;
    private List<Explosion> explosions;

    private Player player;

    private HUD hud;

    public Level1State(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        initialize();
    }

    @Override
    public void initialize() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.07);

        background = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

        populateEnemies();

        explosions = new ArrayList<>();

        hud = new HUD(player);
    }

    private void populateEnemies() {
        enemies = new ArrayList<>();
        Slugger s;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200),

        };

        for (Point point : points) {
            s = new Slugger(tileMap);
            s.setPosition(point.getX(), point.getY());
            enemies.add(s);
        }
    }

    @Override
    public void update() {
        player.update();

        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());

        background.setPosition(tileMap.getX(), tileMap.getY());

        player.checkAttack(enemies);

        for (Enemy enemy : new ArrayList<>(enemies)) {
            enemy.update();
            if (enemy.isDead()) {
                explosions.add(new Explosion((int) enemy.getX(), (int) enemy.getY()));
                enemies.remove(enemy);
            }
        }

        for (Explosion explosion : new ArrayList<>(explosions)) {
            explosion.update();
            if (explosion.shouldRemove()) {
                explosions.remove(explosion);
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        background.draw(graphics2D);

        tileMap.draw(graphics2D);

        player.draw(graphics2D);

        for (Enemy enemy : enemies) {
            enemy.draw(graphics2D);
        }

        for (Explosion explosion : explosions) {
            explosion.setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
            explosion.draw(graphics2D);
        }

        hud.draw(graphics2D);
    }

    @Override
    public void keyPressed(int keyID) {
        if (keyID == KeyEvent.VK_LEFT || keyID == KeyEvent.VK_A) {
            player.setLeft(true);
        } else if (keyID == KeyEvent.VK_RIGHT || keyID == KeyEvent.VK_D) {
            player.setRight(true);
        } else if (keyID == KeyEvent.VK_UP || keyID == KeyEvent.VK_W) {
            player.setUp(true);
        } else if (keyID == KeyEvent.VK_DOWN || keyID == KeyEvent.VK_S) {
            player.setDown(true);
        } else if (keyID == KeyEvent.VK_SPACE) {
            player.setJumping(true);
        } else if (keyID == KeyEvent.VK_E) {
            player.setGliding(true);
        } else if (keyID == KeyEvent.VK_R) {
            player.setScratching();
        } else if (keyID == KeyEvent.VK_F) {
            player.setFiring();
        }
    }

    private void populateEnemies() {
        enemies = new ArrayList<>();
        Slugger s;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200),

        };

        for (Point point : points) {
            s = new Slugger(tileMap);
            s.setPosition(point.getX(), point.getY());
            enemies.add(s);
        }
    }    @Override
    public void keyReleased(int keyID) {
        if (keyID == KeyEvent.VK_LEFT || keyID == KeyEvent.VK_A) {
            player.setLeft(false);
        } else if (keyID == KeyEvent.VK_RIGHT || keyID == KeyEvent.VK_D) {
            player.setRight(false);
        } else if (keyID == KeyEvent.VK_UP || keyID == KeyEvent.VK_W) {
            player.setUp(false);
        } else if (keyID == KeyEvent.VK_DOWN || keyID == KeyEvent.VK_S) {
            player.setDown(false);
        } else if (keyID == KeyEvent.VK_SPACE) {
            player.setJumping(false);
        } else if (keyID == KeyEvent.VK_E) {
            player.setGliding(false);
        }
    }
}
    