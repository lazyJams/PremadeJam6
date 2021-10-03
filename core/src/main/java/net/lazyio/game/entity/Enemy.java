package net.lazyio.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;
import net.lazyio.engine.ai.Brain;
import net.lazyio.engine.ai.tasks.IdleTask;
import net.lazyio.engine.ai.tasks.MoveRandomlyTask;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.entity.behaviors.IRender;
import net.lazyio.engine.entity.behaviors.ITick;
import net.lazyio.engine.util.gdx;
import net.lazyio.game.Assets;
import net.lazyio.game.entity.ai.GoToPlayerTask;
import net.lazyio.game.util.GameCollisions;
import net.lazyio.game.util.Tags;

public class Enemy extends Entity implements IRender, ITick {

    private final Brain brain;

    private float stateTime = 0f;
    private final Animation<TextureRegion> animation;

    private float speed = 10f;

    public Enemy(Vector2 pos) {
        super(pos, new Vector2(16f, 16f), Tags.ENEMY);

        this.brain = new Brain();
        this.brain.addTask(new IdleTask(this, 2f));
        this.brain.addTask(new MoveRandomlyTask(this, 2f));
        this.brain.addTask(new GoToPlayerTask(this, 2f));

        animation = gdx.createAnimation(Assets.alienAnim.get(), 2, 1, 0.25f, Animation.PlayMode.LOOP);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.stateTime += delta;
        batch.draw(this.animation.getKeyFrame(this.stateTime), this.pos.x, this.pos.y);
    }

    @Override
    public void tick(float delta) {
        this.brain.update(delta, false);

        this.vel.set(this.vel.x * this.speed * delta, 0f);

        Response.Result move = this.world.move(this.item, this.pos.x + this.vel.x, this.pos.y + this.vel.y, GameCollisions.enemyFilter);

        this.pos.set(move.goalX, move.goalY);
    }
}
