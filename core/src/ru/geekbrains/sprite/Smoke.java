package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Smoke extends Sprite {

    private Rect worldBounds;
    private Vector2 speed = new Vector2();

    public Smoke(TextureAtlas atlas) {
        super(atlas.findRegion("smoke"));
        setHeightProportion(0.2f);
        speed.set(Rnd.nextFloat(-0.01f, 0.01f), Rnd.nextFloat(-0.5f, -0.1f));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        float positionX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float positionY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(positionX,positionY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        checkEndBounds();
    }

    private void checkEndBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
