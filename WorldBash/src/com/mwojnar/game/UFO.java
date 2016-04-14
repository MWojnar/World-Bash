package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Input.TouchEvent;

public class UFO extends Enemy {
	
	public double oscillation, originY;
	public int shootTimer;
	public boolean isDead;
	
	public UFO(double originY, boolean isRight) {
		
		super();
		sprite = GameScreen.spriteUFO;
		oscillation = 0;
		y = originY;
		this.originY = originY;
		if (isRight)
			hSpeed = 3;
		else
			hSpeed = -3;
		shootTimer = 60;
		animationLoop = false;
		isDead = false;
		animationSpeed = .25;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			if (!isDead) {
				
				oscillation += .1;
				x += hSpeed;
				y = originY + Math.sin(oscillation) * 15;
				if (x > 400 || x < -sprite.frameWidth)
					super.destroy();
				shootTimer--;
				if (shootTimer <= 0) {
					
					EnemyLaser laser = new EnemyLaser();
					laser.setPos(x + sprite.frameWidth / 2 - GameScreen.spriteEnemyLaser.frameWidth / 2, y + sprite.image.getHeight() / 2 - GameScreen.spriteEnemyLaser.image.getHeight() / 2);
					laser.setDirSpeed(3, Math.atan2(x + sprite.frameWidth / 2 - GameScreen.earth.x - GameScreen.earth.sprite.frameWidth / 2, y + sprite.image.getHeight() - GameScreen.earth.y - GameScreen.earth.sprite.image.getHeight() / 2) + Math.PI / 2);
					GameScreen.entityList.add(0, laser);
					GameScreen.playSound(Assets.enemyLaser, 0.85f);
					shootTimer = 60;
					
				}
				
			}
			
		}
		
	}
	
	public void destroy() {
		
		if (sprite != GameScreen.spriteUFOExplode) {
			
			GameScreen.shakeScreen();
			TextRaise text = new TextRaise(x + sprite.frameWidth / 2, y + sprite.image.getHeight(), "2000");
			GameScreen.entityList.add(text);
			int prevScoreBalance = GameScreen.score / 10000;
			GameScreen.score += 2000;
			if (GameScreen.powerUpTimer <= 0) {
				
				PowerUp powerUp = new PowerUp();
				powerUp.setPos(x + sprite.frameWidth / 2 - GameScreen.spritePowerUp.frameWidth / 2, y + sprite.image.getHeight() / 2 - GameScreen.spritePowerUp.image.getHeight() / 2);
				powerUp.initialX = powerUp.x;
				powerUp.initialY = powerUp.y;
				GameScreen.entityList.add(powerUp);
				GameScreen.powerUpTimer = 900;
				
			}
			setSprite(GameScreen.spriteUFOExplode);
			Random random = new Random();
			if (random.nextBoolean())
				GameScreen.playSound(Assets.enemyExplode, 0.85f);
			else
				GameScreen.playSound(Assets.enemyExplode2, 0.85f);
			isDead = true;
			
		}
		
	}
	
	public void animationEnd() {
		
		if (sprite == GameScreen.spriteUFOExplode)
			super.destroy();
		
	}
	
}
