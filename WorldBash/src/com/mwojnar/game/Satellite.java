package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class Satellite extends Entity {
	
	public double rotation;
	public Entity earth;
	public boolean isDead;
	
	Satellite(Entity earth, double rotation) {
		
		super();
		sprite = GameScreen.spriteSatellite;
		this.rotation = rotation;
		this.earth = earth;
		x = earth.x + earth.sprite.frameWidth / 2 + Math.cos(rotation) * 30 - sprite.frameWidth / 2;
		y = earth.y + earth.sprite.image.getHeight() / 2 - Math.sin(rotation) * 30 - sprite.image.getHeight() / 2;
		isDead = false;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		rotation += .05;
		x = earth.x + earth.sprite.frameWidth / 2 + Math.cos(rotation) * 30 - sprite.frameWidth / 2;
		y = earth.y + earth.sprite.image.getHeight() / 2 - Math.sin(rotation) * 30 - sprite.image.getHeight() / 2;
		if (!GameScreen.entityList.contains(earth)) {
			
			destroy();
			
		}
		if (!isDead) {
			
			for(int i = 0; i < GameScreen.entityList.size(); i++) {
				
				Entity theEntity = GameScreen.entityList.get(i);
				if (theEntity instanceof Enemy || theEntity instanceof EnemyLaser) {
					
					if(!(theEntity instanceof Asteroid && ((Asteroid)theEntity).invincibilityFrames > 0)) {
						
						if (!(theEntity instanceof UFO && ((UFO)theEntity).isDead)) {
							
							if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
								
								if (!(theEntity instanceof BlackAsteroid))
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
	
	public void destroy() {
		
		if (sprite != GameScreen.spriteSatelliteExplode) {
			
			setSprite(GameScreen.spriteSatelliteExplode);
			movePos(-6, -6);
			Random random = new Random();
			if (random.nextBoolean())
				GameScreen.playSound(Assets.enemyExplode, 0.85f);
			else
				GameScreen.playSound(Assets.enemyExplode2, 0.85f);
			isDead = true;
			
		}
		
	}
	
	public void animationEnd() {
		
		if (sprite == GameScreen.spriteSatelliteExplode)
			super.destroy();
		
	}
	
	public void draw(Graphics g) {
		
		if (sprite != GameScreen.spriteSatelliteExplode) {
			
			if (visible) {
				
				g.drawImage(sprite.image, (int)x, (int)y, rotation * 180 / Math.PI);
				
			}
			
		} else {
			
			super.draw(g);
			
		}
		
	}
	
}
