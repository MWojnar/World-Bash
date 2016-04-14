package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class PlanetChunk extends Entity {
	
	int invincibleTimer, rotation;
	
	public PlanetChunk(double x, double y, double speed, double direction) {
		
		super();
		rotation = 0;
		if (GameScreen.tetheredPlanet.sprite == GameScreen.spriteMars || GameScreen.tetheredPlanet.sprite == GameScreen.spriteMarsCracked)
			sprite = GameScreen.spritePlanetChunkMars;
		else if (GameScreen.tetheredPlanet.sprite == GameScreen.spriteJupiter || GameScreen.tetheredPlanet.sprite == GameScreen.spriteJupiterCracked)
			sprite = GameScreen.spritePlanetChunkJupiter;
		else
			sprite = GameScreen.spritePlanetChunkPluto;
		setPos(x, y);
		setDirSpeed(speed, direction);
		invincibleTimer = 5;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		rotation += 5;
		if (rotation >= 360)
			rotation -= 360;
		super.update(touchEvents);
		movePos(vSpeed, hSpeed);
		if (x > 400 || x < -sprite.frameWidth || y > 240 || y < -sprite.image.getHeight())
			super.destroy();
		invincibleTimer--;
		if (invincibleTimer <= 0) {
			
			invincibleTimer = 0;
			for(int i = 0; i < GameScreen.entityList.size(); i++) {
				
				Entity theEntity = GameScreen.entityList.get(i);
				if (theEntity instanceof Asteroid || theEntity instanceof BigAsteroid || theEntity instanceof UFO || theEntity instanceof EnemyLaser) {
					
					if(!(theEntity instanceof Asteroid && ((Asteroid)theEntity).invincibilityFrames > 0)) {
						
						if (!(theEntity instanceof UFO && ((UFO)theEntity).isDead)) {
							
							if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
								
								theEntity.destroy();
								destroy();
								Random random = new Random();
								int rand = random.nextInt(3);
								if (!(theEntity instanceof EnemyLaser)) {
									
									if (rand == 0)
										GameScreen.playSound(Assets.enemyHit, 0.85f);
									else if (rand == 1)
										GameScreen.playSound(Assets.enemyHit2, 0.85f);
									else
										GameScreen.playSound(Assets.enemyHit3, 0.85f);
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	public void draw(Graphics g) {
		
		if (visible) {
			
			g.drawImage(sprite.image, (int)x, (int)y, rotation);
			
		}
		
	}
	
}
