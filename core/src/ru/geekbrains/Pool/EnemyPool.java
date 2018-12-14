package ru.geekbrains.pool;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.PlayerShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private PlayerShip playerShip;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, PlayerShip playerShip, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.playerShip = playerShip;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, playerShip, worldBounds);
    }
}
