package me.stuntguy3000.java.dragontale.entity;

import me.stuntguy3000.java.dragontale.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends MapObject {
    private static final int FALLING = 3;
    private static final int FIREBALL = 5;
    private static final int GLIDING = 4;
    private static final int IDLE = 0;
    private static final int JUMPING = 2;
    private static final int SCRATCHING = 6;
    private static final int WALKING = 1;
    private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5};
    private boolean dead;
    private int fire;
    private int fireBallDamage;
    private int fireCost;
    private ArrayList<FireBall> fireballs;
    private boolean firing;
    private boolean flinching;
    private long flinchingTimer;
    private boolean gliding;
    private int health;
    private int maxFire;
    private int maxHealth;
    private int scratchDamage;
    private int scratchRange;
    private boolean scratching;
    private List<BufferedImage[]> sprites;

    public Player(TileMap tileMap) {
        super(tileMap);

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;
        fire = maxFire = 2500;

        fireCost = 200;
        fireBallDamage = 5;
        fireballs = new ArrayList<>();

        scratchDamage = 8;
        scratchRange = 40;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));

            sprites = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                BufferedImage[] spriteList = new BufferedImage[numFrames[i]];

                if (i != 6) {
                    for (int j = 0; j < numFrames[i]; j++) {
                        spriteList[j] = spriteSheet.getSubimage(j * width, i * height, width, height);
                    }
                } else {
                    for (int j = 0; j < numFrames[i]; j++) {
                        spriteList[j] = spriteSheet.getSubimage(j * width * 2, i * height, width * 2, height);
                    }
                }

                sprites.add(spriteList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }

    public void checkAttack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (scratching) {
                if (facingRight) {
                    if (enemy.getX() > x &&
                            enemy.getX() < x + scratchRange &&
                            enemy.getY() > y - height / 2 &&
                            enemy.getY() < y + height / 2) {
                        enemy.hit(scratchDamage);
                    }
                } else {
                    if (enemy.getX() < x &&
                            enemy.getX() > x - scratchRange &&
                            enemy.getY() < y - height / 2 &&
                            enemy.getY() > y + height / 2) {
                        enemy.hit(scratchDamage);
                    }
                }
            }

            for (FireBall fireball : fireballs) {
                if (fireball.intersects(enemy)) {
                    enemy.hit(fireBallDamage);
                    fireball.setHit();
                }
            }

            if (intersects(enemy)) {
                hit(enemy.getDamage());
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        setMapPosition();

        for (FireBall fireball : fireballs) {
            fireball.draw(graphics2D);
        }

        if (flinching) {
            long elapsed = (System.nanoTime() / flinchingTimer) / 1000000;

            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(graphics2D);
    }

    public int getFire() {
        return fire;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxFire() {
        return maxFire;
    }

    public int getMaxHealth() {
        return maxHealth;
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
        } else {
            if (dX > 0) {
                dX -= stopSpeed;
                if (dX < 0) {
                    dX = 0;
                }
            } else if (dX < 0) {
                dX += stopSpeed;
                if (dX > 0) {
                    dX = 0;
                }
            }
        }

        // Cannot move while attacking, except in air
        if ((currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)) {
            dX = 0;
        }

        if (jumping && !falling) {
            dY = jumpStart;
            falling = true;
        }

        if (falling) {
            if (dY > 0 && gliding) {
                dY += fallSpeed * 0.1;
            } else {
                dY += fallSpeed;
            }

            if (dY > 0) {
                jumping = false;
            } else if (dY < 0 && !jumping) {
                dY += stopJumpSpeed;
            }

            if (dY > maxFallSpeed) {
                dY = maxFallSpeed;
            }
        }
    }

    public void hit(int damage) {
        if (flinching) {
            return;
        }

        health -= damage;

        if (health < 0) {
            health = 0;
        }

        if (health == 0) {
            dead = true;
        }

        flinching = true;
        flinchingTimer = System.nanoTime();
    }

    public void setFiring() {
        firing = true;
    }

    public void setGliding(boolean gliding) {
        this.gliding = gliding;
    }

    public void setScratching() {
        scratching = true;
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) {
                scratching = false;
            }
        }

        if (currentAction == FIREBALL) {
            if (animation.hasPlayedOnce()) {
                firing = false;
            }
        }

        fire += 1;
        if (fire > maxFire) {
            fire = maxFire;
        }

        if (firing && currentAction != FIREBALL) {
            if (fire > fireCost) {
                fire -= fireCost;
                FireBall fireBall = new FireBall(tileMap, facingRight);
                fireBall.setPosition(x, y);
                fireballs.add(fireBall);
            }
        }

        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).update();
            if (fireballs.get(i).shouldRemove()) {
                fireballs.remove(i);
                i--;
            }
        }

        if (flinching) {
            long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;

            if (elapsed > 1000) {
                flinching = false;
            }
        }

        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 60;
            }
        } else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dY > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            } else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dY < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }

        animation.update();

        if (currentAction != SCRATCHING && currentAction != FIREBALL) {
            if (right) {
                facingRight = true;
                facingLeft = false;
            }

            if (left) {
                facingLeft = true;
                facingRight = false;
            }
        }
    }
}
    