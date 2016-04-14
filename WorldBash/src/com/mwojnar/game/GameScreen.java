package com.mwojnar.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Vibrator;

import com.mwojnar.framework.Game;
import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Image;
import com.mwojnar.framework.Music;
import com.mwojnar.framework.Screen;
import com.mwojnar.framework.Input.TouchEvent;
import com.mwojnar.framework.Sound;
import com.mwojnar.framework.implementation.AndroidGame;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	static GameState state = GameState.Running;
	public static Sprite spriteEarth, spriteMars, spriteBackgroundSpace, spriteTile, spriteAsteroid, spriteAsteroidParticle;
	public static Sprite spriteHealth, spriteEarthExploding, spriteBlackAsteroid, spriteBigAsteroid, spriteUFO;
	public static Sprite spriteEnemyLaser, spriteUFOExplode, spriteJupiter, spritePluto, spriteDeathStar, spritePowerUp;
	public static Sprite spriteSatellite, spritePlanetChunkMars, spritePlanetChunkJupiter, spritePlanetChunkPluto, spriteShield;
	public static Sprite spriteFireball, spritePlutoCracked, spriteJupiterCracked, spriteMarsCracked, spriteSatelliteExplode;
	public static Planet earth;
	public static Planet2 tetheredPlanet;
	public static Point view;
	public static int score;
	private static int shakeIntensity = 4;
	private static int shakeIntensityMax = 12;
	private static int shakeIntensityAdd = 2;
	private static int shakeIntensityCurrent = 4;
	private static int shakeTimer = 0;
	private static boolean isShaking = false;
	private static int phase, phaseTimer;
	private static double waveMultiplier;
	public static int powerUpTimer;
	public static int redAlpha;
	public static boolean isPressed;
	
	public static boolean planetSelect;
	public static double marsRotate, jupiterRotate, plutoRotate, marsRotateSpeed, jupiterRotateSpeed, plutoRotateSpeed;
	public static double marsOscillation, jupiterOscillation, plutoOscillation;
	public static int planetSelected;
	
	public static List<Entity> entityList;
	public static List<Entity> entitiesToBeAdded;
			
	Paint paint, paint2, paint3, paint4, paint5;

	public GameScreen(Game game) {
		super(game);

		view = new Point(0, 0);
		
		powerUpTimer = 900;
		score = 0;
		phase = 1;
		waveMultiplier = 1;
		phaseTimer = 20 * 60;
		redAlpha = 0;
		
		isPressed = false;
		planetSelect = true;
		marsRotate = 0;
		jupiterRotate = 0;
		plutoRotate = 0;
		planetSelected = 0;
		Random random = new Random();
		marsRotateSpeed = random.nextDouble() * 2 + .5;
		if (random.nextBoolean())
			marsRotateSpeed = -marsRotateSpeed;
		jupiterRotateSpeed = random.nextDouble() * 2 + .5;
		if (random.nextBoolean())
			jupiterRotateSpeed = -jupiterRotateSpeed;
		plutoRotateSpeed = random.nextDouble() * 2 + .5;
		if (random.nextBoolean())
			plutoRotateSpeed = -plutoRotateSpeed;
		marsOscillation = random.nextDouble() * 2 * Math.PI;
		jupiterOscillation = random.nextDouble() * 2 * Math.PI;
		plutoOscillation = random.nextDouble() * 2 * Math.PI;
		
		spriteEarth = new Sprite(Assets.earth, 9);
		spriteMars = new Sprite(Assets.mars, 1);
		spriteBackgroundSpace = new Sprite(Assets.bgSpace, 1);
		spriteTile = new Sprite(Assets.tile, 1);
		spriteAsteroid = new Sprite(Assets.asteroid, 1);
		spriteAsteroidParticle = new Sprite(Assets.asteroidParticle, 1);
		spriteHealth = new Sprite(Assets.health, 4);
		spriteEarthExploding = new Sprite(Assets.explodingEarth, 9);
		spriteBlackAsteroid = new Sprite(Assets.blackAsteroid, 1);
		spriteBigAsteroid = new Sprite(Assets.bigAsteroid, 1);
		spriteUFO = new Sprite(Assets.UFO, 1);
		spriteEnemyLaser = new Sprite(Assets.enemyLaserImage, 2);
		spriteUFOExplode = new Sprite(Assets.UFOExplode, 6);
		spriteJupiter = new Sprite(Assets.jupiter, 1);
		spritePluto = new Sprite(Assets.pluto, 1);
		spriteDeathStar = new Sprite(Assets.deathStar, 1);
		spritePowerUp = new Sprite(Assets.powerUp, 1);
		spriteSatellite = new Sprite(Assets.satellite, 1);
		spritePlanetChunkMars = new Sprite(Assets.planetChunkMars, 1);
		spritePlanetChunkJupiter = new Sprite(Assets.planetChunkJupiter, 1);
		spritePlanetChunkPluto = new Sprite(Assets.planetChunkPluto, 1);
		spriteShield = new Sprite(Assets.shield, 7);
		spriteFireball = new Sprite(Assets.fireball, 5);
		spriteMarsCracked = new Sprite(Assets.marsCracked, 1);
		spriteJupiterCracked = new Sprite(Assets.jupiterCracked, 1);
		spritePlutoCracked = new Sprite(Assets.plutoCracked, 1);
		spriteSatelliteExplode = new Sprite(Assets.satelliteExplosion, 8);
		
		entityList = new ArrayList<Entity>();
		entitiesToBeAdded = new ArrayList<Entity>();
		
		earth = new Planet();
		earth.setPos(200 - earth.sprite.frameWidth / 2, 50);
		earth.followPoint = new Point((int)earth.x + earth.sprite.frameWidth / 2, (int)earth.y + earth.sprite.image.getHeight() / 2);
		tetheredPlanet = new Planet2(earth);
		tetheredPlanet.setPos(200 - tetheredPlanet.sprite.frameWidth / 2, 100);
		Health health = new Health();
		health.setPos(400 - health.sprite.frameWidth, 0);
		
		entityList.add(earth);
		entityList.add(tetheredPlanet);
		entityList.add(health);

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(15);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setTextSize(50);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
		
		paint3 = new Paint();
		paint3.setTextSize(12);
		paint3.setTextAlign(Paint.Align.LEFT);
		paint3.setAntiAlias(true);
		paint3.setColor(Color.WHITE);
		
		paint4 = new Paint();
		paint4.setTextSize(30);
		paint4.setTextAlign(Paint.Align.CENTER);
		paint4.setAntiAlias(true);
		paint4.setColor(Color.WHITE);

		paint5 = new Paint();
		paint5.setTextSize(12);
		paint5.setTextAlign(Paint.Align.RIGHT);
		paint5.setAntiAlias(true);
		paint5.setColor(Color.WHITE);

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		
		if (!planetSelect) {
			
			powerUpTimer--;
			if (powerUpTimer <= 0)
				powerUpTimer = 0;
			phaseTimer--;
			if (phaseTimer <= 0) {
				
				if (phase == 1) {
					
					phaseTimer = 20 * 60;
					phase = 2;
					spawnBlackAsteroid(1);
					
				} else if (phase == 2) {
					
					phaseTimer = 40 * 60;
					phase = 3;
					spawnBigAsteroid(1);
					
				} else if (phase == 3) {
					
					phase = 4;
					phaseTimer = 10 * 60;
					spawnUFO(1);
					
				} else if (phase == 4) {
					
					phaseTimer = 10 * 60;
					waveMultiplier += .2;
					
				}
				
			}
			
			if (phase >= 1)
				spawnAsteroid((int)(60 / waveMultiplier));
			if (phase >= 2)
				spawnBlackAsteroid((int)(120 * 1.30 / waveMultiplier));
			if (phase >= 3)
				spawnBigAsteroid((int)(150 * 1.30 / waveMultiplier));
			if (phase >= 4)
				spawnUFO((int)(300 * 1.30 / waveMultiplier));
			
			for (int i = 0; i < entityList.size(); i++) {
				
				Entity currentEntity = entityList.get(i);
				entityList.get(i).update(touchEvents);
				if (entityList.contains(currentEntity))
					i = entityList.indexOf(currentEntity);
				else
					i--;
				
			}
			
			if (isShaking) {
				
				shakeTimer--;
				Random random = new Random();
				view.x = random.nextInt(shakeIntensityCurrent * 2) - shakeIntensityCurrent;
				view.y = random.nextInt(shakeIntensityCurrent * 2) - shakeIntensityCurrent;
				if (shakeTimer <= 0) {
					
					isShaking = false;
					
				}
				
			} else {
				
				view.x = 0;
				view.y = 0;
				
			}
			
		} else {
			
			marsOscillation += .1;
			jupiterOscillation += .1;
			plutoOscillation += .1;
			if (marsOscillation > Math.PI * 2)
				marsOscillation -= Math.PI * 2;
			if (jupiterOscillation > Math.PI * 2)
				jupiterOscillation -= Math.PI * 2;
			if (plutoOscillation > Math.PI * 2)
				plutoOscillation -= Math.PI * 2;
			marsRotate += marsRotateSpeed;
			jupiterRotate += jupiterRotateSpeed;
			plutoRotate += plutoRotateSpeed;
			if (marsRotate > 360)
				marsRotate -= 360;
			if (jupiterRotate > 360)
				jupiterRotate -= 360;
			if (plutoRotate > 360)
				plutoRotate -= 360;
			if (marsRotate < 0)
				marsRotate += 360;
			if (jupiterRotate < 0)
				jupiterRotate += 360;
			if (plutoRotate < 0)
				plutoRotate += 360;
			
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				if (isPressed) {
					
					if (event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {
	
						if (Math.sqrt(Math.pow(100 - event.x, 2) + Math.pow((120 + (int)(Math.sin(marsOscillation) * 10)) - event.y, 2)) < 32)
							planetSelected = 1;
						else if (Math.sqrt(Math.pow(200 - event.x, 2) + Math.pow((120 + (int)(Math.sin(jupiterOscillation) * 10)) - event.y, 2)) < 32)
							planetSelected = 2;
						else if (Math.sqrt(Math.pow(300 - event.x, 2) + Math.pow((120 + (int)(Math.sin(plutoOscillation) * 10)) - event.y, 2)) < 32)
							planetSelected = 3;
						else 
							planetSelected = 0;
	
					} else if (event.type == TouchEvent.TOUCH_UP) {
						
						if (Math.sqrt(Math.pow(100 - event.x, 2) + Math.pow((120 + (int)(Math.sin(marsOscillation) * 10)) - event.y, 2)) < 32 && planetSelected == 1) {
							
							planetSelect = false;
							state = GameState.Ready;
							
						} else if (Math.sqrt(Math.pow(200 - event.x, 2) + Math.pow((120 + (int)(Math.sin(jupiterOscillation) * 10)) - event.y, 2)) < 32 && planetSelected == 2) {
	
							tetheredPlanet.setSprite(spriteJupiter);
							tetheredPlanet.x -= 16;
							tetheredPlanet.y -= 16;
							tetheredPlanet.tetherLength = 80;
							tetheredPlanet.inertia = .4;
							planetSelect = false;
							state = GameState.Ready;
							
						} else if (Math.sqrt(Math.pow(300 - event.x, 2) + Math.pow((120 + (int)(Math.sin(plutoOscillation) * 10)) - event.y, 2)) < 32 && planetSelected == 3) {
	
							tetheredPlanet.setSprite(spritePluto);
							tetheredPlanet.x += 8;
							tetheredPlanet.y += 8;
							tetheredPlanet.tetherLength = 188;
							tetheredPlanet.inertia = .001;
							tetheredPlanet.maxSpeed = 22.5;
							planetSelect = false;
							state = GameState.Ready;
							
						}/* else if (Math.sqrt(Math.pow(-event.x, 2) + Math.pow(-event.y, 2)) < 48) {
							
							tetheredPlanet.setSprite(spriteDeathStar);
							tetheredPlanet.x += 4;
							tetheredPlanet.y += 4;
							planetSelect = false;
							playMusic(Assets.imperialMarch);
							state = GameState.Ready;
							
						}*/ else
							planetSelected = 0;
						
					}
					
				} else if (event.type == TouchEvent.TOUCH_DOWN) {
					
					isPressed = true;
					
				}

			}
			
		}
		
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 400, 120)) {

					if (!inBounds(event, 0, 0, 17, 17)) {
						resume();
					}
				} else if (inBounds(event, 50, 120, 300, 120)) {
					nullify();
					goToMenu();
					playMusic(Assets.mainMenuTheme);
				} else if (inBounds(event, 0, 120, 50, 60)) {
					
					AndroidGame.muteMusic = !AndroidGame.muteMusic;
					if (AndroidGame.muteMusic)
						stopMusic();
					else if (tetheredPlanet.sprite != spriteDeathStar)
						playMusic(Assets.mainTheme);
					else
						playMusic(Assets.imperialMarch);
					
				} else if (inBounds(event, 0, 180, 50, 60)) {
					
					AndroidGame.muteSound = !AndroidGame.muteSound;
					
				} else if (inBounds(event, 350, 120, 50, 120)) {
					
					AndroidGame.stopVibration = !AndroidGame.stopVibration;
					
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 400, 240)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					state = GameState.Running;
					playMusic(Assets.mainMenuTheme);
					return;
				}
			}
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawRect(0, 0, 401, 241, Color.BLACK);
		
		//DRAW THE BACKGROUNDS
		spriteBackgroundSpace.drawSprite(0, 0, 0, g);
		
		// First draw the game elements.
		
		if (!planetSelect) {
			
			g.drawLine((int)earth.x + earth.sprite.frameWidth / 2 - GameScreen.view.x, (int)earth.y + earth.sprite.image.getHeight() / 2 - GameScreen.view.y, (int)tetheredPlanet.x + tetheredPlanet.sprite.frameWidth / 2, (int)tetheredPlanet.y + tetheredPlanet.sprite.image.getHeight() / 2, Color.LTGRAY);
			for (int i = 0; i < entityList.size(); i++) {
				
				entityList.get(i).draw(g);
				
			}
			redAlpha--;
			if (redAlpha > 0) {
				
				g.drawARGB(redAlpha * 255 / 120, 255, 0, 0);
				
			} else
				redAlpha = 0;
			
		}
		
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}

	private void nullify() {

		//set all variables used to null for garbage collection purposes, don't forget to check here after every new object is added.
		paint = null;
		paint2 = null;
		paint3 = null;
		paint4 = null;
		entityList = null;
		spriteEarth = null;
		spriteMars = null;
		spriteBackgroundSpace = null;
		spriteTile = null;
		spriteAsteroid = null;
		spriteAsteroidParticle = null;
		spriteHealth = null;
		spriteEarthExploding = null;
		spriteBlackAsteroid = null;
		spriteBigAsteroid = null;
		spriteUFO = null;
		spriteEnemyLaser = null;
		spriteUFOExplode = null;
		spriteJupiter = null;
		spritePluto = null;
		spriteDeathStar = null;
		spriteSatellite = null;
		spritePlanetChunkMars = null;
		spritePlanetChunkJupiter = null;
		spritePlanetChunkPluto = null;
		spriteShield = null;
		spritePlutoCracked = null;
		spriteJupiterCracked = null;
		spriteMarsCracked = null;
		view = null;
		earth = null;
		tetheredPlanet = null;
		spritePowerUp = null;
		spriteFireball = null;
		spriteSatelliteExplode = null;
		

		// Call garbage collector to clean up memory.
		System.gc();

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 200, 120, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		/*g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
		g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
		g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
		g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);*/
		
		if (!planetSelect) {
			
			g.drawString(Integer.toString(score), 0, 12, paint3);
			
		} else {
			
			g.drawImage(spriteMars.image, 100 - spriteMars.frameWidth / 2, 120 - spriteMars.image.getHeight() / 2 + (int)(Math.sin(marsOscillation) * 10), marsRotate, planetSelected == 1 ? 1.5 : 1);
			g.drawImage(spriteJupiter.image, 200 - spriteJupiter.frameWidth / 2, 120 - spriteJupiter.image.getHeight() / 2 + (int)(Math.sin(jupiterOscillation) * 10), jupiterRotate, planetSelected == 2 ? 1.5 : 1);
			g.drawImage(spritePluto.image, 300 - spritePluto.frameWidth / 2, 120 - spritePluto.image.getHeight() / 2 + (int)(Math.sin(plutoOscillation) * 10), plutoRotate, planetSelected == 3 ? 1.5 : 1);
			g.drawString("Mars", 100, 200, paint);
			g.drawString("Jupiter", 200, 200, paint);
			g.drawString("Pluto", 300, 200, paint);
			g.drawString("Choose your weapon!", 200, 50, paint4);
			
		}
		//g.drawString(Integer.toString(timer / 30), 0, 12, paint3);

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 200, 82, paint2);
		g.drawString("Menu", 200, 180, paint2);
		
		g.drawLine(10, 140, 40, 140, Color.WHITE);
		g.drawLine(10, 140, 10, 170, Color.WHITE);
		g.drawLine(40, 170, 40, 140, Color.WHITE);
		g.drawLine(40, 170, 10, 170, Color.WHITE);

		g.drawLine(10, 190, 40, 190, Color.WHITE);
		g.drawLine(10, 190, 10, 220, Color.WHITE);
		g.drawLine(40, 220, 40, 190, Color.WHITE);
		g.drawLine(40, 220, 10, 220, Color.WHITE);

		g.drawLine(390, 165, 360, 165, Color.WHITE);
		g.drawLine(390, 165, 390, 195, Color.WHITE);
		g.drawLine(360, 195, 360, 165, Color.WHITE);
		g.drawLine(360, 195, 390, 195, Color.WHITE);
		
		if (!AndroidGame.muteMusic) {
			
			g.drawLine(10, 140, 40, 170, Color.WHITE);
			g.drawLine(40, 140, 10, 170, Color.WHITE);
			
		}
		
		if (!AndroidGame.muteSound) {
			
			g.drawLine(10, 190, 40, 220, Color.WHITE);
			g.drawLine(40, 190, 10, 220, Color.WHITE);
			
		}

		if (!AndroidGame.stopVibration) {
			
			g.drawLine(390, 165, 360, 195, Color.WHITE);
			g.drawLine(360, 165, 390, 195, Color.WHITE);
			
		}
		
		g.drawString("Music", 45, 161, paint3);
		g.drawString("Sound", 45, 211, paint3);
		g.drawString("Vibrate", 355, 186, paint5);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 200, 120, paint2);
		g.drawString("Score : " + Integer.toString(score), 200, 145, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));

	}
	
	public static void shakeScreen() {
		
		if (!isShaking) {
			
			isShaking = true;
			shakeIntensityCurrent = shakeIntensity;
			
		} else {
			
			shakeIntensityCurrent += shakeIntensityAdd;
			if (shakeIntensityCurrent > shakeIntensityMax) {
				
				shakeIntensityCurrent = shakeIntensityMax;
				
			}
		}
		shakeTimer = 30;
		vibrate(100);
		
	}
	
	public static void vibrate(int milliseconds) {
		
		if (!AndroidGame.stopVibration)
			AndroidGame.vibrator.vibrate(milliseconds);
		
	}
	
	public static void playSound(Sound sound, float volume) {
		
		if (!AndroidGame.muteSound)
			sound.play(volume);
		
	}
	
	public static void playMusic(Music music) {
		
		stopMusic();
		if (!AndroidGame.muteMusic) {
			
			music.play();
			music.seekBegin();
			
		}
		
	}
	
	public static void stopMusic() {
		
		if (Assets.mainMenuTheme.isPlaying())
			Assets.mainMenuTheme.stop();
		if (Assets.mainTheme.isPlaying())
			Assets.mainTheme.stop();
		if (Assets.imperialMarch.isPlaying())
			Assets.imperialMarch.stop();
		
	}
	
	private static void spawnAsteroid(int spawnRate) {
		
		Random theRandom = new Random();
		if (theRandom.nextInt(spawnRate) == 0) {
			
			boolean isRight = theRandom.nextBoolean();
			Asteroid theAsteroid = new Asteroid();
			if (isRight) {
				
				theAsteroid.setPos(-theAsteroid.sprite.frameWidth + 1, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			} else {
				
				theAsteroid.setPos(399, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			}
			entityList.add(0, theAsteroid);
			
		}
		
	}
	
	private static void spawnBlackAsteroid(int spawnRate) {
		
		Random theRandom = new Random();
		if (theRandom.nextInt(spawnRate) == 0) {
			
			boolean isRight = theRandom.nextBoolean();
			BlackAsteroid theAsteroid = new BlackAsteroid();
			if (isRight) {
				
				theAsteroid.setPos(-theAsteroid.sprite.frameWidth + 1, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			} else {
				
				theAsteroid.setPos(399, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			}
			entityList.add(0, theAsteroid);
			
		}
		
	}

	private static void spawnBigAsteroid(int spawnRate) {
	
		Random theRandom = new Random();
		if (theRandom.nextInt(spawnRate) == 0) {
			
			boolean isRight = theRandom.nextBoolean();
			BigAsteroid theAsteroid = new BigAsteroid();
			if (isRight) {
				
				theAsteroid.setPos(-theAsteroid.sprite.frameWidth + 1, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			} else {
				
				theAsteroid.setPos(399, theRandom.nextInt(224));
				theAsteroid.setDirSpeed(theRandom.nextDouble() * 2 + 1, Math.atan2(200 - theAsteroid.x - theAsteroid.sprite.frameWidth / 2, theRandom.nextInt(240) - theAsteroid.y - theAsteroid.sprite.image.getHeight()) - Math.PI / 2);
				
			}
			entityList.add(0, theAsteroid);
		
		}
	
	}
	
	private static void spawnUFO(int spawnRate) {
		
		Random theRandom = new Random();
		if (theRandom.nextInt(spawnRate) == 0) {
			
			boolean isRight = theRandom.nextBoolean();
			UFO theAsteroid = new UFO(theRandom.nextInt(224), isRight);
			if (isRight) {
				
				theAsteroid.movePos(-theAsteroid.sprite.frameWidth + 1, theAsteroid.y);
				
			} else {
				
				theAsteroid.movePos(399, theAsteroid.y);
				
			}
			entityList.add(0, theAsteroid);
			
		}
		
	}
	
	public static void displayPowerUp(int type) {
		
		PowerUpText text = new PowerUpText("Default!");
		if (type == 0) {
			
			text = new PowerUpText("Shield!");
			
		} else if (type == 1) {

			text = new PowerUpText("Planet Chunks!");
			
		} else if (type == 2) {

			text = new PowerUpText("Satellites!");
			
		} else if (type == 3) {

			text = new PowerUpText("Flame Trail!");
			
		}
		entityList.add(text);
		
	}

}