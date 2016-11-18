package com.mygdx.game;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {

    final Fruit game;
    Texture backgroundImage;
    Rectangle background;
    OrthographicCamera camera;


    public MainMenuScreen(final Fruit gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 520);
        
        backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
        background = new Rectangle();
        background.x = 0; 
        background.y = 0; 
        background.width = 800;
        background.height = 520;
        

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        game.batch.draw(backgroundImage, background.x, background.y);
        game.font.draw(game.batch, "Welcome to Fruit Garden!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}


