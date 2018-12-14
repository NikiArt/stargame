package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureRegion explosionRegion;
    private TextureAtlas atlas;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, 7, 15, 97, explosionSound);
    }
}