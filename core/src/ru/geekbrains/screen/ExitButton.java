package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class ExitButton extends Sprite {

    private Rect worldBounds;
    private static float PRESSED = 0.14f;
    private static float RELEASED = 0.15f;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("exitButton"));
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
    public void update(float delta) {
        super.update(delta);
    }
}
