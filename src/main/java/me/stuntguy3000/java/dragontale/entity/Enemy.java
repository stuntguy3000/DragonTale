package me.stuntguy3000.java.dragontale.entity;

import me.stuntguy3000.java.dragontale.tileMap.TileMap;

public class Enemy extends MapObject {

    protected int damage;
    protected boolean dead;
    protected boolean flinching;
    protected long flinchingTimer;
    protected int health;
    protected int maxHealth;

    public Enemy(TileMap tileMap) {
        super(tileMap);
    }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) {
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

    public boolean isDead() {
        return dead;
    }

    public void update() {
    }

}
    