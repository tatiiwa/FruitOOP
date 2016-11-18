package com.mygdx.game;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final Fruit game;

    
    Texture bucketImage;
    Texture backgroundImage;
    Texture orangeImage;
    Texture starImage;
    Texture watermelonImage;
    Texture strawberryImage;
    Sound dropSound;
    Music fruitMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Rectangle background;
    Array<Rectangle> oranges;
    Array<Rectangle> stars;
    Array<Rectangle> watermelons;
    Array<Rectangle> strawberrys;
    long orangeTime;
    long starTime;
    long watermelonTime;
    long strawberryTime;
    int dropsGathered;
    public static boolean endGame;
    public long startTime = TimeUtils.millis();
	public long currentTime ;
	public long countTime;
	public long gameTime = 125*500; 

    public GameScreen(final Fruit gam) {
        this.game = gam;
        

        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
        orangeImage = new Texture(Gdx.files.internal("orange.png"));
        strawberryImage = new Texture(Gdx.files.internal("strawberry.png"));
        starImage = new Texture(Gdx.files.internal("star.png"));
        watermelonImage = new Texture(Gdx.files.internal("watermelon.png"));
        

                        
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        fruitMusic = Gdx.audio.newMusic(Gdx.files.internal("fruit.mp3"));
        fruitMusic.setLooping(true);

                        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

                        
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; 
        bucket.y = 0; 
        bucket.width = 64;
        bucket.height = 64;
        
        background = new Rectangle();
        background.x = 800 / 2 - 800 / 2; 
        background.y = 0; 
        background.width = 600;
        background.height = 800;

       oranges = new Array<Rectangle>();
       spawnOrange();
      
       strawberrys = new Array<Rectangle>();
       spawnStrawberry();
       
       stars = new Array<Rectangle>();
       spawnStar();
       
       watermelons = new Array<Rectangle>();
       spawnWatermelon();
       startTime = TimeUtils.millis(); 
        
    }

    private void spawnOrange() {
    	Rectangle orange = new Rectangle();
        orange.x = MathUtils.random(0, 800 - 64);
        orange.y = 480;
        orange.width = 64;
        orange.height = 64;
        oranges.add(orange);
        orangeTime = TimeUtils.nanoTime();
    }
    
    private void spawnStrawberry() {
    	Rectangle strawberry = new Rectangle();
    	strawberry.x = MathUtils.random(0, 800 - 64);
    	strawberry.y = 480;
    	strawberry.width = 64;
    	strawberry.height = 64;
    	strawberrys.add(strawberry);
    	strawberryTime = TimeUtils.nanoTime();
    }
    
    private void spawnStar() {
    	Rectangle star = new Rectangle();
    	star.x = MathUtils.random(0, 800 - 64);
    	star.y = 480;
    	star.width = 64;
    	star.height = 64;
    	stars.add(star);
    	starTime = TimeUtils.nanoTime();
    }
    
    private void spawnWatermelon() {
    	Rectangle watermelon = new Rectangle();
    	watermelon.x = MathUtils.random(0, 800 - 64);
    	watermelon.y = 480;
    	watermelon.width = 64;
    	watermelon.height = 64;
    	watermelons.add(watermelon);
    	watermelonTime = TimeUtils.nanoTime();
    }
    
    
    
    @Override
    public void render(float delta) {
                
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        
        currentTime = TimeUtils.timeSinceMillis(startTime);
		countTime = gameTime-currentTime;
		countTime = TimeUnit.MILLISECONDS.toSeconds(countTime);
		if(countTime > 0) {}
			 else {
			countTime = 0;
			endGame = true;
			 }
        
        
        game.batch.begin();
        game.batch.draw(backgroundImage, background.x, background.y);
        game.font.draw(game.batch, "Fruit: " + dropsGathered, 0, 480);
        game.font.draw(game.batch, "Time: " + countTime, 400, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle orange : oranges) {
            game.batch.draw(orangeImage, orange.x, orange.y);
        }
        for (Rectangle strawberry : strawberrys) {
            game.batch.draw(strawberryImage, strawberry.x, strawberry.y);
        }
        for (Rectangle star : stars) {
            game.batch.draw(starImage, star.x, star.y);
        }
        for (Rectangle watermelon : watermelons) {
            game.batch.draw(watermelonImage, watermelon.x, watermelon.y);
        }
        game.batch.end();

                        
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

                
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 1000 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 1000 * Gdx.graphics.getDeltaTime();

               
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - orangeTime > 900000000)
            spawnOrange();
        
        if (TimeUtils.nanoTime() - strawberryTime > 800000000)
            spawnStrawberry();
        
        if (TimeUtils.nanoTime() - watermelonTime > 600000000)
            spawnWatermelon();
        
        if (TimeUtils.nanoTime() - starTime > 1000000000)
            spawnStar();
        
        Iterator<Rectangle> iter = oranges.iterator();
        while (iter.hasNext()) {
            Rectangle orange = iter.next();
            orange.y -= 200 * Gdx.graphics.getDeltaTime();
            if (orange.y + 64 < 0)
                iter.remove();
            if (orange.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
        
        Iterator<Rectangle> iter1 = strawberrys.iterator();
        while (iter1.hasNext()) {
            Rectangle strawberry = iter1.next();
            strawberry.y -= 200 * Gdx.graphics.getDeltaTime();
            if (strawberry.y + 64 < 0)
                iter1.remove();
            if (strawberry.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter1.remove();
            }
        }
        
        Iterator<Rectangle> iter11 = stars.iterator();
        while (iter11.hasNext()) {
            Rectangle star = iter11.next();
            star.y -= 200 * Gdx.graphics.getDeltaTime();
            if (star.y + 64 < 0)
                iter11.remove();
            if (star.overlaps(bucket)) {
                dropsGathered+=10;
                dropSound.play();
                iter11.remove();
            }
        }
        
        Iterator<Rectangle> iter111 = watermelons.iterator();
        while (iter111.hasNext()) {
            Rectangle watermelon = iter111.next();
            watermelon.y -= 200 * Gdx.graphics.getDeltaTime();
            if (watermelon.y + 64 < 0)
                iter111.remove();
            if (watermelon.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter111.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
                
        fruitMusic.play();
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
        
        orangeImage.dispose();
        starImage.dispose();
        watermelonImage.dispose();
        strawberryImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        fruitMusic.dispose();
    }
}
