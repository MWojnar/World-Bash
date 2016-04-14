package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class BigAsteroid extends Enemy {
	
	double rotation, rotationSpeed;
	
	public BigAsteroid() {
		
		super();
		sprite = GameScreen.spriteBigAsteroid;
		rotation = 0;
		Random theRandom = new Random();
		rotationSpeed = theRandom.nextDouble() * 8 + 1;
		if (theRandom.nextBoolean())
			rotationSpeed = -rotationSpeed;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			/*rotation += Math.PI / 10;
			if (rotation > Math.PI * 2)
				rotation -= Math.PI * 2;*/
			
			rotation += rotationSpeed;
			if (rotation > 360)
				rotation -= 360;
			if (rotation < 0)
				rotation += 360;
			if (x > 400 || x < -sprite.frameWidth || y > 240 || y < -sprite.image.getHeight())
				super.destroy();
			movePos(hSpeed, vSpeed);
			
		}
		
	}
	
	public void draw(Graphics g) {
		
		if (visible && sprite != null) {
			
			sprite.drawSprite((int)x, (int)y, rotation, g);
			
		}
		
	}
	
	public void destroy() {
		
		Random random = new Random();
		Asteroid asteroid = new Asteroid(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 2 + 1, Math.PI / 4 + random.nextDouble() - .5);
		asteroid.invincibilityFrames = 5;
		GameScreen.entityList.add(0, asteroid);
		asteroid = new Asteroid(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 2 + 1, Math.PI * 3 / 4 + random.nextDouble() - .5);
		asteroid.invincibilityFrames = 5;
		GameScreen.entityList.add(0, asteroid);
		asteroid = new Asteroid(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 2 + 1, Math.PI * 5 / 4 + random.nextDouble() - .5);
		asteroid.invincibilityFrames = 5;
		GameScreen.entityList.add(0, asteroid);
		asteroid = new Asteroid(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 2 + 1, Math.PI * 7 / 4 + random.nextDouble() - .5);
		asteroid.invincibilityFrames = 5;
		GameScreen.entityList.add(0, asteroid);
		
		GameScreen.shakeScreen();
		TextRaise text = new TextRaise(x + sprite.frameWidth / 2, y + sprite.image.getHeight(), "1000");
		GameScreen.entityList.add(text);
		int prevScoreBalance = GameScreen.score / 10000;
		GameScreen.score += 1000;
		if (GameScreen.powerUpTimer <= 0) {
			
			PowerUp powerUp = new PowerUp();
			powerUp.setPos(x + sprite.frameWidth / 2 - GameScreen.spritePowerUp.frameWidth / 2, y + sprite.image.getHeight() / 2 - GameScreen.spritePowerUp.image.getHeight() / 2);
			powerUp.initialX = powerUp.x;
			powerUp.initialY = powerUp.y;
			GameScreen.entityList.add(powerUp);
			GameScreen.powerUpTimer = 900;
			
		}
		super.destroy();
		
	}
	
}
