package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import org.omg.CORBA.PUBLIC_MEMBER;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class PlayButton extends Sprite {

    private Rect worldBounds;
    private static float PRESSED = 0.08f;
    private static float RELEASED = 0.1f;

    public PlayButton(TextureAtlas atlas) {
        super(atlas.findRegion("playButton"));
        setHeightProportion(RELEASED);
        setTop(getHalfHeight() + 0.1f);
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
