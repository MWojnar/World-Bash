package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Input.TouchEvent;

public class Fireball extends Entity {

	int timer;
	
	public Fireball() {
		
		super();
		sprite = GameScreen.spriteFireball;
		timer = 30;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		timer--;
		if (timer <= 0)
			destroy();
		if (active) {

			for(int i = 0; i < GameScreen.entityList.size(); i++) {
				
				Entity theEntity = GameScreen.entityList.get(i);
				if (theEntity instanceof Enemy && !(theEntity instanceof BlackAsteroid)) {
					
					if(!(theEntity instanceof Asteroid && ((Asteroid)theEntity).invincibilityFrames > 0)) {
						
						if (!(theEntity instanceof UFO && ((UFO)theEntity).isDead)) {
							
							if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
								
								theEntity.destroy();
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
	
}
