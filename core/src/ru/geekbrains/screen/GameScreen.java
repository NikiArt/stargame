package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.GameOverTitle;
import ru.geekbrains.sprite.NewGameButton;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Smoke;
import ru.geekbrains.utils.EnemiesEmitter;

public class GameScreen extends BaseScreen {

    private Texture img;
    private TextureAtlas textureAtlas;
    private TextureAtlas shipsAtlas;
    private TextureAtlas explosionAtlas;

    private Background background;
    private Smoke[] smoke;
    private Boolean exitButtonPressed;
    private PlayerShip playerShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemiesEmitter enemiesEmitter;

    private Music music;
    private Sound enemyShipShootSound;
    private Sound explosionSound;
    private Sound playerShipShootSound;

    private NewGameButton newGameButton;
    private GameOverTitle gameOverTitle;

    private static final int SMOKE_COUNT = 30;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        exitButtonPressed = false;
        super.show();
        textureAtlas = new TextureAtlas("menuAtlas.atlas");
        shipsAtlas = new TextureAtlas("ShipsAtlas.atlas");
        explosionAtlas = new TextureAtlas("explosion.atlas");
        newGameButton = new NewGameButton(textureAtlas);
        gameOverTitle = new GameOverTitle(textureAtlas);
        img = new Texture("background.png");
        background = new Background(new TextureRegion(img));
        smoke = new Smoke[SMOKE_COUNT];
        for (int i = 0; i < smoke.length; i++) {
            smoke[i] = new Smoke(textureAtlas);
        }
        bulletPool = new BulletPool();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
        music.setLooping(true);
        music.play();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(explosionAtlas, explosionSound);
        playerShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        playerShip = new PlayerShip(shipsAtlas, bulletPool, explosionPool, playerShipShootSound);
        playerShip.resize(worldBounds);
        enemyShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool, explosionPool, playerShip, worldBounds, enemyShipShootSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, shipsAtlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + playerShip.getHalfWidth();
            if (enemy.pos.dst2(playerShip.pos) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                playerShip.damage(playerShip.getHp());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != playerShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == playerShip) {
                continue;
            }
            if (playerShip.isBulletCollision(bullet)) {
                bullet.setDestroyed(true);
                playerShip.damage(bullet.getDamage());
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void update(float delta) {
        for (int i = 0; i < smoke.length; i++) {
            smoke[i].update(delta);
        }
        if (!playerShip.isDestroyed()) {
            playerShip.update(delta);
            playerShip.resize(worldBounds);

        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemiesEmitter.generate(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < smoke.length; i++) {
            smoke[i].draw(batch);
        }
        if (!playerShip.isDestroyed()) {
            playerShip.draw(batch);

        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        } else {
            gameOverTitle.draw(batch);
            newGameButton.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < smoke.length; i++) {
            smoke[i].resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        playerShip.touchDown(touch, pointer);
        newGameButton.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        playerShip.touchUp(touch, pointer);
        if (playerShip.isDestroyed() && newGameButton.touchUp(touch, pointer)) {
            playerShip.setHp(20);
            playerShip.setDestroyed(false);
            enemyPool.dispose();
            bulletPool.dispose();
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }


}
