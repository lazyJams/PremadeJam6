package net.lazyio.game.entity;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Response;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.entity.behaviors.IRender;
import net.lazyio.engine.entity.behaviors.ITick;
import net.lazyio.engine.util.TweenUtils;
import net.lazyio.game.Assets;
import net.lazyio.game.Game;
import net.lazyio.game.util.GameCollisions;
import net.lazyio.game.util.Tags;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static net.lazyio.engine.util.gdx.biKeyPressedOr;
import static net.lazyio.engine.util.gdx.createAnimation;

public class Player extends Entity implements IRender, ITick {

    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;

    private Vector2 spawnPoint = new Vector2();

    private static final int VEL_X = 0;
    private static final int VEL_Y = 1;

    public final float gravity = 200f;
    public float tickGravity = gravity;
    public boolean isJumping = false;
    public final float jumpTimeMax = .25f;
    public float jumpTime = 0f;
    public float jumpSpeed = 3f;
    public float maxSpeed = 5f;
    public float acc = 2f;
    public float deceleration = 2f;
    public boolean canUsePower = true;
    public float powerTimer = -1f;
    public final float powerTime = 300f;

    TextureRegion[][] textureRegion;

    public Player(Vector2 pos) {
        super(pos, new Vector2(16f, 16f), Tags.PLAYER);
        this.textureRegion = TextureRegion.split(Assets.all.get(), 16, 16);

        this.animation = createAnimation(Assets.player.get(), 2, 1, .25f, Animation.PlayMode.LOOP);

        TweenUtils.createAndRegAccessor(
                Player.class,
                (player, tweenType, returnValues) -> {
                    if (tweenType == VEL_X) {
                        returnValues[0] = player.vel.x;
                        return 1;
                    }
                    return 0;
                },
                (player, tweenType, newValues) -> {
                    if (tweenType == VEL_X) {
                        player.vel.x = newValues[0];
                    }
                }
        );
    }

    @Override
    public void tick(float delta) {
        this.vel.set(this.vel.x, -100f * delta);

        biKeyPressedOr(
                D, () -> {
                    float per = (this.maxSpeed - this.vel.x) / this.maxSpeed;
                    Tween.to(this, VEL_X, this.acc * per)
                            .target(this.maxSpeed)
                            .ease(TweenEquations.easeOutCirc)
                            .start(Game.INST.tweenMgr);
                },
                A, () -> {
                    float per = Math.abs((-this.maxSpeed - this.vel.x) / this.maxSpeed);
                    Tween.to(this, VEL_X, this.acc * per)
                            .target(-this.maxSpeed)
                            .ease(TweenEquations.easeOutCirc)
                            .start(Game.INST.tweenMgr);
                },
                () -> {
                    float per = Math.abs(this.vel.x / this.maxSpeed);
                    Tween.to(this, VEL_X, this.deceleration * per)
                            .target(0f)
                            .ease(TweenEquations.easeOutCirc)
                            .start(Game.INST.tweenMgr);
                }
        );

        Response.Result move = this.world.move(this.item, this.pos.x + this.vel.x, this.pos.y + this.vel.y, GameCollisions.playerFilter);

        if (move.goalX < 0) move.goalX = 0f;

        this.pos.set(move.goalX, move.goalY);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.stateTime += delta;
        if (this.vel.x == 0) {
            batch.draw(this.textureRegion[0][1], this.pos.x, this.pos.y);
        } else {
            batch.draw(this.animation.getKeyFrame(this.stateTime), this.pos.x, this.pos.y);
        }

    }

    public void setRespawnPoint(float x, float y) {
        this.spawnPoint.set(x, y);
    }
}
