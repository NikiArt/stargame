package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;


public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture playerShip;
    private Vector2 playerPosition;
    private Vector2 touch;
    private Vector2 workVector;
    private float playerCurrentSpeed = 8f;

    @Override
    public void show() {
        super.show();
        img = new Texture("background.png");
        playerShip = new Texture("playerShip.png");
        playerPosition = new Vector2(0, 0);
        touch = new Vector2();
        workVector = playerPosition.cpy();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(playerShip, playerPosition.x, playerPosition.y, 100, 100);
        batch.end();
        currentPlayerPosition();
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
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        workVector = touch.cpy();
        workVector.sub(playerPosition);
        workVector.nor();
        workVector.scl(playerCurrentSpeed);
        return false;
    }

    private void currentPlayerPosition() {
        if (touch.equals(playerPosition)) {
            return;
        }
        Vector2 playerPoint = playerPosition.cpy().add(workVector);
        Vector2 touchPoint = touch.cpy();
        float newPointLength = playerPoint.sub(playerPosition).len();
        float touchPointLength = touchPoint.sub(playerPosition).len();
        if (newPointLength >= touchPointLength) {
            playerPosition = touch.cpy();
            return;
        }
        playerPosition.add(workVector);
    }
}
