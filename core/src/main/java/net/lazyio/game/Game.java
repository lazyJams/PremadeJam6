package net.lazyio.game;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import net.lazyio.engine.event.EventManager;
import net.lazyio.game.screen.GameScreen;

import static net.lazyio.engine.util.gdx.dt;
import static net.lazyio.engine.util.gdx.fps;

public class Game extends com.badlogic.gdx.Game {

    public static Game INST;

    public TweenManager tweenMgr;
    public EventManager evtManager;

    public boolean debug;

    public Game(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void create() {
        INST = this;

        this.tweenMgr = new TweenManager();
        this.evtManager = new EventManager();

        Assets.init();

        this.setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
        Gdx.graphics.setTitle("FPS: " + fps() + " | DT: " + dt());
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}