package net.lazyio.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.entity.behaviors.IRender;
import net.lazyio.game.Assets;
import net.lazyio.game.util.Tags;

public class NonTicking extends Entity implements IRender {

    TextureRegion[][] textureRegion;

    public NonTicking(Vector2 pos, Vector2 size, String tag) {
        super(pos, size, Tags.TREE);

        this.textureRegion = TextureRegion.split(Assets.all.get(), 16, 16);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(this.textureRegion[1][0], this.pos.x, this.pos.y);
    }
}
