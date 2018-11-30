package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.BaseScreen;


public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture playerShip;
    private Vector2 playerPosition;
    private Vector2 touch;
    private Vector2 workVector;
    private float playerCurrentSpeed = 1f;
    private List<Integer> currentButtonKeys = new ArrayList<Integer>();
    private Vector2 playerPoint;
    private Vector2 touchPoint;
    private static float PLAYER_SHIP_HEIGHT = 12f;
    int iterator;


    @Override
    public void show() {
        super.show();
        img = new Texture("background.png");
        playerShip = new Texture("playerShip.png");
        playerPosition = new Vector2(0, 0);
        touch = new Vector2();
        playerPoint = new Vector2();
        touchPoint = new Vector2();
        workVector = playerPosition.cpy();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, (-0.5f)*super.HEIGHT_SIZE, (-0.5f)*super.HEIGHT_SIZE, super.HEIGHT_SIZE, super.HEIGHT_SIZE);
        iterator = 1;
        while (iterator * super.HEIGHT_SIZE < super.worldBounds.getWidth()) {
            batch.draw(img, (-0.5f)*super.HEIGHT_SIZE + super.HEIGHT_SIZE , (-0.5f)*super.HEIGHT_SIZE, super.HEIGHT_SIZE, super.HEIGHT_SIZE);
            batch.draw(img, (-0.5f)*super.HEIGHT_SIZE - super.HEIGHT_SIZE , (-0.5f)*super.HEIGHT_SIZE, super.HEIGHT_SIZE, super.HEIGHT_SIZE);
            iterator ++;
        }
        batch.draw(playerShip, playerPosition.x, playerPosition.y, PLAYER_SHIP_HEIGHT, PLAYER_SHIP_HEIGHT);
        batch.end();
        currentPlayerPosition();
        playerImpulseMove();
    }

    @Override
    public void dispose() {
        img.dispose();
        playerShip.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        float worldX = super.worldBounds.getWidth()*(screenX/super.screenBounds.getWidth()) - super.worldBounds.getHalfWidth() - PLAYER_SHIP_HEIGHT/2;
        float worldY = super.worldBounds.getHeight()*((Gdx.graphics.getHeight() - screenY)/super.screenBounds.getHeight()) - super.worldBounds.getHalfHeight() - PLAYER_SHIP_HEIGHT/2;
        touch.set(worldX, worldY);
        workVector.set(touch);
        workVector.sub(playerPosition);
        workVector.nor();
        workVector.scl(playerCurrentSpeed);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        int indexArray = currentButtonKeys.indexOf(keycode);
        currentButtonKeys.remove(indexArray);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        currentButtonKeys.add(keycode);
        return false;
    }

    private void currentPlayerPosition() {
        if (touch.equals(playerPosition)) {
            return;
        }
        playerPoint.set(playerPosition).add(workVector);
        touchPoint.set(touch);
        float newPointLength = playerPoint.sub(playerPosition).len();
        float touchPointLength = touchPoint.sub(playerPosition).len();
        if (newPointLength >= touchPointLength) {
            playerPosition.set(touch);
            return;
        }
        playerPosition.add(workVector);
    }

    private void playerImpulseMove() {
        if (currentButtonKeys.size() == 0) {
            return;
        }
        Vector2 directionVector = new Vector2(0, 0);
        if (currentButtonKeys.indexOf(19) >= 0) {
            directionVector.add(new Vector2(0, 1));
        }
        if (currentButtonKeys.indexOf(20) >= 0) {
            directionVector.add(new Vector2(0, -1));
        }
        if (currentButtonKeys.indexOf(21) >= 0) {
            directionVector.add(new Vector2(-1, 0));
        }
        if (currentButtonKeys.indexOf(22) >= 0) {
            directionVector.add(new Vector2(1, 0));
        }
        directionVector.scl(playerCurrentSpeed);
        playerPosition.add(directionVector);
        touch = playerPosition.cpy();
    }
}
