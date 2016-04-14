package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class Asteroid extends Enemy {
	
	double rotation, rotationSpeed;
	public int invincibilityFrames;
	
	public Asteroid() {
		
		super();
		sprite = GameScreen.spriteAsteroid;
		rotation = 0;
		Random theRandom = new Random();
		rotationSpeed = theRandom.nextDouble() * 8 + 1;
		invincibilityFrames = 0;
		if (theRandom.nextBoolean())
			rotationSpeed = -rotationSpeed;
		
	}
	
	public Asteroid(double x, double y, double speed, double direction) {
		
		super();
		sprite = GameScreen.spriteAsteroid;
		rotation = 0;
		Random theRandom = new Random();
		rotationSpeed = theRandom.nextDouble() * 8 + 1;
		invincibilityFrames = 0;
		if (theRandom.nextBoolean())
			rotationSpeed = -rotationSpeed;
		setPos(x, y);
		setDirSpeed(speed, direction);
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			/*rotation += Math.PI / 10;
			if (rotation > Math.PI * 2)
				rotation -= Math.PI * 2;*/
			
			invincibilityFrames--;
			if (invincibilityFrames <= 0)
				invincibilityFrames = 0;
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
		AsteroidParticles asteroidParticle = new AsteroidParticles(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 4 + 1, Math.PI / 4 + random.nextDouble() - .5);
		GameScreen.entityList.add(0, asteroidParticle);
		asteroidParticle = new AsteroidParticles(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 4 + 1, Math.PI * 3 / 4 + random.nextDouble() - .5);
		GameScreen.entityList.add(0, asteroidParticle);
		asteroidParticle = new AsteroidParticles(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 4 + 1, Math.PI * 5 / 4 + random.nextDouble() - .5);
		GameScreen.entityList.add(0, asteroidParticle);
		asteroidParticle = new AsteroidParticles(x + sprite.frameWidth / 2, y + sprite.image.getHeight() / 2, random.nextDouble() * 4 + 1, Math.PI * 7 / 4 + random.nextDouble() - .5);
		GameScreen.entityList.add(0, asteroidParticle);
		
		GameScreen.shakeScreen();
		TextRaise text = new TextRaise(x + sprite.frameWidth / 2, y + sprite.image.getHeight(), "500");
		GameScreen.entityList.add(text);
		int prevScoreBalance = GameScreen.score / 10000;
		GameScreen.score += 500;
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
