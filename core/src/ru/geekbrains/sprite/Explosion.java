package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;

    private Sound explosionSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound explosionSound) {
        super(region, rows, cols, frames);
        this.explosionSound = explosionSound;
    }

    public Explosion(TextureAtlas atlas, int rows, int cols, int frames, Sound explosionSound) {
        super();
        getExplosion(atlas, rows, cols, frames);
        this.explosionSound = explosionSound;
    }

    private void getExplosion(TextureAtlas atlas, int rows, int cols, int frames) {
        if (atlas == null) throw new RuntimeException("Split null atlas");
        this.regions = new TextureRegion[frames];
        int i = 0;
        for (TextureRegion region : atlas.getRegions()) {
            if (i >= regions.length) break;
            this.regions[i] = region;
            i++;
        }
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
       // explosionSound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                setDestroyed(true);
                frame = 0;
            }
        }
    }

}