package com.mwojnar.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class Planet2 extends Entity {
	
	public int type, tetherLength, waitBounceSound;
	public double inertia, maxSpeed;
	public Planet anchorPlanet;
	
	public Planet2(Planet anchorPlanet) {
		
		super();
		this.anchorPlanet = anchorPlanet;
		sprite = GameScreen.spriteMars;
		type = 0;
		animated = false;
		tetherLength = 96;
		waitBounceSound = 0;
		inertia = .05;
		maxSpeed = 15;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			waitBounceSound--;
			if (waitBounceSound < 0)
				waitBounceSound = 0;
			setDirSpeed(speed - inertia, direction);
			if (speed < 0)
				setDirSpeed(0, direction);
			if (speed > maxSpeed)
				setDirSpeed(maxSpeed, direction);
			boolean ownMovementCausedPull = false;
			if (Math.sqrt(Math.pow(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), 2) + Math.pow(anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2), 2)) <= tetherLength)
				ownMovementCausedPull = true;
			movePos(hSpeed, vSpeed);
			if (anchorPlanet.chunkTimer <= 0) {
				
				if (sprite == GameScreen.spritePlutoCracked) {
					
					setSprite(GameScreen.spritePluto);
					
				} else if (sprite == GameScreen.spriteJupiterCracked) {
					
					setSprite(GameScreen.spriteJupiter);
					
				} else if (sprite == GameScreen.spriteMarsCracked) {
					
					setSprite(GameScreen.spriteMars);
					
				}
				
			} else {
				
				if (sprite == GameScreen.spritePluto) {
					
					setSprite(GameScreen.spritePlutoCracked);
					
				} else if (sprite == GameScreen.spriteJupiter) {
					
					setSprite(GameScreen.spriteJupiterCracked);
					
				} else if (sprite == GameScreen.spriteMars) {
					
					setSprite(GameScreen.spriteMarsCracked);
					
				}
				
			}
			if (Math.sqrt(Math.pow(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), 2) + Math.pow(anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2), 2)) > tetherLength) {
				
				double extraLength = Math.sqrt(Math.pow(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), 2) + Math.pow(anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2), 2)) - tetherLength;
				double directionAwayFromAnchor = Math.atan2(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2)) + Math.PI / 2;
				setPos(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (sprite.frameWidth / 2) + Math.cos(directionAwayFromAnchor) * tetherLength, anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (sprite.image.getHeight() / 2) - Math.sin(directionAwayFromAnchor) * tetherLength);
				double subtractedAngle = direction - (directionAwayFromAnchor - Math.PI / 2);
				if (subtractedAngle < 0)
					subtractedAngle += Math.PI * 2;
				else if (subtractedAngle >= Math.PI * 2)
					subtractedAngle -= Math.PI * 2;
				if (subtractedAngle < Math.PI)
					subtractedAngle = Math.PI * 2 - subtractedAngle;
				subtractedAngle += directionAwayFromAnchor - Math.PI / 2;
				if (!ownMovementCausedPull) {
					if (extraLength * (maxSpeed / 15) > speed) {
						setDirSpeed(extraLength * (maxSpeed / 15), addAngles(directionAwayFromAnchor - Math.PI, subtractedAngle));
					}
				}
				else {
					
					if (sprite != GameScreen.spriteJupiter)
						setDirSpeed(speed - inertia * 40, addAngles(directionAwayFromAnchor - Math.PI, subtractedAngle));
					else
						setDirSpeed(speed - 2, addAngles(directionAwayFromAnchor - Math.PI, subtractedAngle));
					if (speed < 0) {
						setDirSpeed(0, direction);
					}
					
				}
				
			}
			if (Math.sqrt(Math.pow(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), 2) + Math.pow(anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2), 2)) < sprite.frameWidth / 2 + anchorPlanet.sprite.frameWidth / 2) {
				
				double directionAwayFromAnchor = Math.atan2(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2)) + Math.PI / 2;
				if (speed < anchorPlanet.speed)
					setDirSpeed(anchorPlanet.speed * (maxSpeed / 15), direction);
				double subtractedAngle = direction - (directionAwayFromAnchor - Math.PI / 2);
				if (subtractedAngle < 0)
					subtractedAngle += Math.PI * 2;
				else if (subtractedAngle >= Math.PI * 2)
					subtractedAngle -= Math.PI * 2;
				if (subtractedAngle > Math.PI)
					subtractedAngle = Math.PI * 2 - subtractedAngle;
				subtractedAngle += directionAwayFromAnchor - Math.PI / 2;
				setDirSpeed(speed, addAngles(directionAwayFromAnchor, subtractedAngle));
				setPos(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 + Math.cos(directionAwayFromAnchor) * (anchorPlanet.sprite.frameWidth / 2 + sprite.frameWidth / 2) - sprite.frameWidth / 2, anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - Math.sin(directionAwayFromAnchor) * (anchorPlanet.sprite.frameWidth / 2 + sprite.frameWidth / 2) - sprite.image.getHeight() / 2);
				if (waitBounceSound <= 0) {

					GameScreen.playSound(Assets.friendlyBounce, 1.0f);
					waitBounceSound = 5;
					
				}
				
			}
			for(int i = 0; i < GameScreen.entityList.size(); i++) {
				
				Entity theEntity = GameScreen.entityList.get(i);
				if (theEntity instanceof Enemy || theEntity instanceof EnemyLaser) {

					if(!(theEntity instanceof Asteroid && ((Asteroid)theEntity).invincibilityFrames > 0)) {
						
						if (!(theEntity instanceof UFO && ((UFO)theEntity).isDead)) {
							
							if (Math.sqrt(Math.pow(theEntity.x + theEntity.sprite.frameWidth / 2 - x - sprite.frameWidth / 2, 2) + Math.pow(theEntity.y + theEntity.sprite.image.getHeight() / 2 - y - sprite.image.getHeight() / 2, 2)) < sprite.frameWidth / 2 + theEntity.sprite.frameWidth / 2) {
								
								if (!(theEntity instanceof BlackAsteroid)) {
									
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
									if (anchorPlanet.chunkTimer > 0) {
										
										double newX = x + sprite.frameWidth / 2 + ((theEntity.x + theEntity.sprite.frameWidth / 2) - (x + sprite.frameWidth / 2)) * ((sprite.frameWidth / 2.0) / (sprite.frameWidth / 2.0 + theEntity.sprite.frameWidth / 2.0));
										double newY = y + sprite.image.getHeight() / 2 + ((theEntity.y + theEntity.sprite.image.getHeight() / 2) - (y + sprite.image.getHeight() / 2)) * ((sprite.image.getHeight() / 2.0) / (sprite.image.getHeight() / 2.0 + theEntity.sprite.image.getHeight() / 2.0));
										PlanetChunk planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI / 4 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI * 3 / 4 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI * 5 / 4 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI * 7 / 4 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI / 2 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI * 3 / 2 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										planetChunk = new PlanetChunk(newX, newY, random.nextDouble() * 4 + 1, Math.PI * 2 + random.nextDouble() - .5);
										GameScreen.entityList.add(0, planetChunk);
										
									}
									
								}
								if (theEntity instanceof BigAsteroid) {
									
									double directionAwayFromAnchor = Math.atan2(theEntity.x + theEntity.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), theEntity.y + theEntity.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2)) + Math.PI / 2;
									setDirSpeed(speed, directionAwayFromAnchor);
									
								}
								
							} else if (this.sprite == GameScreen.spriteDeathStar) {
								
								double directionAwayFromAnchor2 = Math.atan2(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2)) + Math.PI / 2;
								double tangent = -Math.tan(directionAwayFromAnchor2);
								double offset = y + sprite.image.getHeight() / 2 - ((x + sprite.frameWidth / 2) * tangent);
								double laserXStart = x + sprite.frameWidth / 2 + Math.cos(directionAwayFromAnchor2) * 8;
								double laserYStart = y + sprite.image.getHeight() / 2 - Math.sin(directionAwayFromAnchor2) * 8;
								double angleDifference = Math.abs(directionAwayFromAnchor2 - (Math.atan2(theEntity.x + theEntity.sprite.frameWidth / 2 - laserXStart, theEntity.y + theEntity.sprite.image.getHeight() / 2 - laserYStart) - Math.PI / 2));
								if (Math.abs((theEntity.y + theEntity.sprite.image.getHeight() / 2) - (tangent) * (theEntity.x + theEntity.sprite.frameWidth / 2) - offset) / Math.sqrt(Math.pow(tangent, 2) + 1) < theEntity.sprite.frameWidth / 2 + 3 && (angleDifference <= Math.PI / 2.0 || angleDifference >= Math.PI * 3.0 / 2.0)) {
									
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
	
	public void draw(Graphics g) {
		
		if (sprite != GameScreen.spriteDeathStar)
			super.draw(g);
		else {
			
			if (visible && sprite != null) {
				
				double directionAwayFromAnchor = Math.atan2(anchorPlanet.x + anchorPlanet.sprite.frameWidth / 2 - (x + sprite.frameWidth / 2), anchorPlanet.y + anchorPlanet.sprite.image.getHeight() / 2 - (y + sprite.image.getHeight() / 2)) + Math.PI / 2;
				sprite.drawSprite((int)x, (int)y, (Math.PI - directionAwayFromAnchor + Math.PI * 5 / 4) * 180 / Math.PI, g);
				List<Integer> rainbow = new ArrayList<Integer>();
				rainbow.add(Color.RED);
				rainbow.add(Color.argb(255, 255, 153, 0));
				rainbow.add(Color.YELLOW);
				rainbow.add(Color.GREEN);
				rainbow.add(Color.BLUE);
				rainbow.add(Color.argb(255, 75, 0, 130));
				rainbow.add(Color.argb(255, 238, 130, 238));
				for (int i = -3; i < 4; i++) {
					
					float theX = (float) (x + sprite.frameWidth / 2 + Math.cos(directionAwayFromAnchor) * 8 + Math.cos(directionAwayFromAnchor + Math.PI / 2) * i);
					float theY = (float) (y + sprite.image.getHeight() / 2 - Math.sin(directionAwayFromAnchor) * 8 - Math.sin(directionAwayFromAnchor + Math.PI / 2) * i);
					g.drawLine(theX, theY, (float) (theX + Math.cos(directionAwayFromAnchor) * 400), (float) (theY - Math.sin(directionAwayFromAnchor) * 400), rainbow.get(i + 3));
					
				}
				
			}
			
		}
		
	}
	
}
