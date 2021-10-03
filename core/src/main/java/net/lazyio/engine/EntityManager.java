package net.lazyio.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.World;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.entity.behaviors.IRender;
import net.lazyio.engine.entity.behaviors.ITick;

public class EntityManager {

    private final World<Entity> world;

    public EntityManager(int cellSize) {
        this.world = new World<>(cellSize);
    }

    public void tick(float dt) {
        world.getItems()
                .stream()
                .filter(item -> item.userData instanceof ITick)
                .map(item -> (ITick) item.userData)
                .forEach(render -> render.tick(dt));
    }

    public void render(SpriteBatch batch, float dt) {
        world.getItems()
                .stream()
                .filter(item -> item.userData instanceof IRender)
                .map(item -> (IRender) item.userData)
                .forEach(render -> render.render(batch, dt));
    }

    public World<Entity> getWorld() {
        return this.world;
    }
}