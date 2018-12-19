package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class NewGameButton extends Sprite {

    private Rect worldBounds;
    private static float PRESSED = 0.08f;
    private static float RELEASED = 0.1f;

    public NewGameButton(TextureAtlas atlas) {
        super(atlas.findRegion("newGameButton"));
        setHeightProportion(RELEASED);
        setTop(getHalfHeight() - 0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void pressButton(Boolean pressed) {
        if (pressed) setHeightProportion(PRESSED);
        else setHeightProportion(RELEASED);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < this.getRight() && touch.x > this.getLeft() &&
                touch.y < this.getTop() && touch.y > this.getBottom()){
            setHeightProportion(PRESSED);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        setHeightProportion(RELEASED);
        if (touch.x < this.getRight() && touch.x > this.getLeft() &&
                touch.y < this.getTop() && touch.y > this.getBottom()){
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
