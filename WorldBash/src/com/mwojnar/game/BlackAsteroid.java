package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class BlackAsteroid extends Enemy {
	
	double rotation, rotationSpeed;
	
	public BlackAsteroid() {
		
		super();
		sprite = GameScreen.spriteBlackAsteroid;
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
	
	/*public void destroy() {
		
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
		super.destroy();
		
	}*/
	
}
