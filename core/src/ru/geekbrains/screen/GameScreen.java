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

import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Smoke;
import ru.geekbrains.utils.EnemiesEmitter;

public class GameScreen extends BaseScreen {

    private Texture img;
    private TextureAtlas textureAtlas;
    private TextureAtlas shipsAtlas;

    private Background background;
    private Smoke[] smoke;
    private Boolean exitButtonPressed;
    private PlayerShip playerShip;
    private BulletPool bulletPool;
    private Music music;
    private Sound mainShipShootSound;
    private EnemyPool enemyPool;

    private EnemiesEmitter enemiesEmitter;

    private static final int SMOKE_COUNT = 50;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        exitButtonPressed = false;
        super.show();
        textureAtlas = new TextureAtlas("menuAtlas.atlas");
        shipsAtlas = new TextureAtlas("ShipsAtlas.atlas");
        img = new Texture("background.png");
        background = new Background(new TextureRegion(img));
        smoke = new Smoke[SMOKE_COUNT];
        for (int i = 0; i < smoke.length; i++) {
            smoke[i] = new Smoke(textureAtlas);
        }
        bulletPool = new BulletPool();
        playerShip = new PlayerShip(shipsAtlas, bulletPool, mainShipShootSound);
        playerShip.resize(worldBounds);
        enemyPool = new EnemyPool(bulletPool, playerShip, worldBounds);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, shipsAtlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < smoke.length; i++) {
            smoke[i].update(delta);
        }
        playerShip.update(delta);
        playerShip.resize(worldBounds);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemiesEmitter.generate(delta);
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < smoke.length; i++) {
            smoke[i].draw(batch);
        }
        playerShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
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
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        playerShip.touchUp(touch, pointer);
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
