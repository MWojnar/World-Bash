package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import android.graphics.Point;

import com.mwojnar.framework.Input.TouchEvent;
import com.mwojnar.framework.implementation.AndroidGame;

public class Planet extends Entity {
	
	public Point followPoint;
	public static int health;
	public int invulnerable, blinkTimer, shieldTimer, chunkTimer, fireTimer;
	public static boolean isDead;
	
	public Planet() {
		
		super();
		health = 4;
		sprite = GameScreen.spriteEarth;
		animated = false;
		followPoint = new Point((int)x + sprite.frameWidth / 2, (int)y + sprite.image.getHeight() / 2);
		invulnerable = 0;
		blinkTimer = 0;
		isDead = false;
		animationSpeed = .25;
		shieldTimer = 0;
		chunkTimer = 0;
		fireTimer = 0;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			if (!isDead) {
				
				shieldTimer--;
				if (shieldTimer < 0)
					shieldTimer = 0;
				chunkTimer--;
				if (chunkTimer < 0)
					chunkTimer = 0;
				fireTimer--;
				if (fireTimer < 0)
					fireTimer = 0;
				if (x > 394)
					x = 394;
				if (x < -12)
					x = -12;
				if (y > 234)
					y = 234;
				if (y < -12)
					y = -12;
				if (fireTimer % 2 == 0 && fireTimer > 0) {
					
					Fireball fireball = new Fireball();
					fireball.setPos(GameScreen.tetheredPlanet.x + GameScreen.tetheredPlanet.sprite.frameWidth / 2 - GameScreen.spriteFireball.frameWidth / 2, GameScreen.tetheredPlanet.y + GameScreen.tetheredPlanet.sprite.image.getHeight() / 2 - GameScreen.spriteFireball.image.getHeight() / 2);
					GameScreen.entityList.add(0, fireball);
					
				}
				for (int i = 0; i < touchEvents.size(); i++) {
					
					if (touchEvents.get(i).type == TouchEvent.TOUCH_DRAGGED || touchEvents.get(i).type == TouchEvent.TOUCH_DOWN) {
						
						followPoint.set(touchEvents.get(i).x, touchEvents.get(i).y);
						break;
						
					}
					
				}
				if (Math.sqrt(Math.pow(followPoint.x - x - sprite.frameWidth / 2, 2) + Math.pow(followPoint.y - y - sprite.image.getHeight() / 2, 2)) > 2.5) {
					
					setDirSpeed(speed + 2.5, Math.atan2(followPoint.x - x - sprite.frameWidth / 2, followPoint.y - y - sprite.image.getHeight() / 2) - Math.PI / 2);
					
				} else {
					
					setDirSpeed(0, 0);
					x = followPoint.x - sprite.frameWidth / 2;
					y = followPoint.y - sprite.image.getHeight() / 2;
					
				}
				if (speed > Math.sqrt(Math.pow(followPoint.x - x - sprite.frameWidth / 2, 2) + Math.pow(followPoint.y - y - sprite.image.getHeight() / 2, 2))) {
					
					setDirSpeed(0, 0);
					x = followPoint.x - sprite.frameWidth / 2;
					y = followPoint.y - sprite.image.getHeight() / 2;
					
				}
				if (speed > 15) {
					
					setDirSpeed(15, direction);
					
				}
				movePos(hSpeed, vSpeed);
				if (direction < 0)
					direction += Math.PI * 2;
				if (direction > Math.PI * 2)
					direction -= Math.PI * 2;
				if (speed < 2)
					frame = 0;
				else if (direction < 22.5 * Math.PI / 180)
					frame = 1;
				else if (direction < 67.5 * Math.PI / 180)
					frame = 2;
				else if (direction < 112.5 * Math.PI / 180)
					frame = 3;
				else if (direction < 157.5 * Math.PI / 180)
					frame = 4;
				else if (direction < 202.5 * Math.PI / 180)
					frame = 5;
				else if (direction < 247.5 * Math.PI / 180)
					frame = 6;
				else if (direction < 292.5 * Math.PI / 180)
					frame = 7;
				else if (direction < 337.5 * Math.PI / 180)
					frame = 8;
				else
					frame = 1;
				if (invulnerable <= 0) {
				
					for(int i = 0; i < GameScreen.entityList.size(); i++) {
						
						Entity theEntity = GameScreen.entityList.get(i);
						if (theEntity instanceof Enemy || theEntity instanceof EnemyLaser) {
								
							if (!(theEntity instanceof UFO && ((UFO)theEntity).isDead)) {
								
								if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
									
									if (!(theEntity instanceof BlackAsteroid))
										theEntity.destroy();
									if (shieldTimer <= 0) {
										
										GameScreen.vibrate(300);
										GameScreen.redAlpha = 30;
										health--;
										if (health <= 0)
											isDead = true;
										invulnerable = 40;
										blinkTimer = 5;
										visible = false;
										Random random = new Random();
										int rand = random.nextInt(3);
										if (rand == 0)
											GameScreen.playSound(Assets.earthHit, 0.85f);
										else if (rand == 1)
											GameScreen.playSound(Assets.earthHit2, 0.85f);
										else
											GameScreen.playSound(Assets.earthHit3, 0.85f);
										
									} else {
										
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
							
						} else if (theEntity instanceof PowerUp) {
							
							if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
								
								Random random = new Random();
								theEntity.destroy();
								applyPowerUp(random.nextInt(4));
								
							}
							
						}
						
					}
					
				} else {
					
					invulnerable--;
					blinkTimer--;
					if (blinkTimer <= 0) {
						
						visible = !visible;
						blinkTimer = 5;
						
					}
					if (invulnerable <= 0) {
						
						invulnerable = 0;
						blinkTimer = 0;
						visible = true;
						
					}
					
				}
				/*GameScreen.view.x = (int)x + 8 - 200;
				GameScreen.view.y = (int)y + 8 - 120;
				//if (x > GameScreen.view.x + 400 - 48) {
					
					//GameScreen.view.x = (int)x + 48 - 400;
					if (GameScreen.view.x > 400)
						GameScreen.view.x = 400;
					
				//}
				//else if (x < GameScreen.view.x + 32) {
					
					//GameScreen.view.x = (int)x - 32;
					if (GameScreen.view.x < 0)
						GameScreen.view.x = 0;
					
				//}
				//if (y > GameScreen.view.y + 240 - 48) {
					
					//GameScreen.view.y = (int)y + 48 - 240;
					if (GameScreen.view.y > 240)
						GameScreen.view.y = 240;
					
				//}
				//else if (y < GameScreen.view.y + 32) {
					
					//GameScreen.view.y = (int)y - 32;
					if (GameScreen.view.y < 0)
						GameScreen.view.y = 0;
					
				//}*/
				
			} else {
				
				if (sprite != GameScreen.spriteEarthExploding) {
					
					setPos(x - (GameScreen.spriteEarthExploding.frameWidth / 2 - sprite.frameWidth / 2), y - (GameScreen.spriteEarthExploding.image.getHeight() / 2 - sprite.image.getHeight() / 2));
					setSprite(GameScreen.spriteEarthExploding);
					animated = true;
					animationLoop = false;
					GameScreen.playSound(Assets.earthDestroyed, 0.85f);
					visible = true;
					
				}
				
			}
			
		}
		
	}
	
	public void animationEnd() {
		
		if (sprite == GameScreen.spriteEarthExploding) {
			
			super.animationEnd();
			GameScreen.state = GameScreen.GameState.GameOver;
			
		}
		
	}
	
	public void applyPowerUp(int type) {
		
		GameScreen.displayPowerUp(type);
		
		if (type == 0) {
			
			shieldTimer = 300;
			for (int i = 0; i < GameScreen.entityList.size(); i++) {
				
				if (GameScreen.entityList.get(i) instanceof Shield) {
					
					GameScreen.entityList.remove(i);
					i--;
					
				}
				
			}
			Shield shield = new Shield(this);
			GameScreen.entityList.add(shield);
			
		} else if (type == 1) {
			
			chunkTimer = 600;
			
		} else if (type == 2) {
			
			for (int i1 = 0; i1 < GameScreen.entityList.size(); i1++) {
				
				if (GameScreen.entityList.get(i1) instanceof Satellite) {
					
					GameScreen.entityList.remove(i1);
					i1--;
					
				}
				
			}
			for(double i = 0; i < Math.PI * 5.0 / 3.0; i += Math.PI / 3.0) {
				
				Satellite satellite = new Satellite(this, i);
				GameScreen.entityList.add(satellite);
				
			}
			
		} else {
			
			fireTimer = 600;
			
		}
		
	}

}
