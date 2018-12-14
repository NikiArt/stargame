package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOverTitle  extends Sprite {

    private Rect worldBounds;

    public GameOverTitle(TextureAtlas atlas) {
        super(atlas.findRegion("gameOverTitle"));
        setHeightProportion(0.08f);
        setTop(getHalfHeight() + 0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
