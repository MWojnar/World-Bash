package com.mwojnar.game;

import com.mwojnar.framework.Image;
import com.mwojnar.framework.Music;
import com.mwojnar.framework.Sound;

public class Assets {
	
	public static Image menu, bgSpace, earth, mars, tile, asteroid, asteroidParticle, health, explodingEarth, blackAsteroid;
	public static Image bigAsteroid, UFO, enemyLaserImage, UFOExplode, jupiter, pluto, deathStar, powerUp;
	public static Image satellite, planetChunkMars, planetChunkJupiter, planetChunkPluto, shield, fireball;
	public static Image plutoCracked, jupiterCracked, marsCracked, satelliteExplosion;
	public static Sound menuSound, earthHit, earthHit2, earthHit3, earthDestroyed, enemyLaser, enemyHit, enemyHit2, enemyHit3, friendlyBounce;
	public static Sound enemyExplode, enemyExplode2;
	public static Music mainMenuTheme, mainTheme, imperialMarch;
	
	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		mainTheme = sampleGame.getAudio().createMusic("WorldBashMain.mp3");
		mainTheme.setLooping(true);
		mainTheme.setVolume(0.5f);
		imperialMarch = sampleGame.getAudio().createMusic("ImperialMarch.mp3");
		imperialMarch.setLooping(true);
		imperialMarch.setVolume(0.5f);
		mainMenuTheme = sampleGame.getAudio().createMusic("WorldBashMenu.mp3");
		mainMenuTheme.setLooping(true);
		mainMenuTheme.setVolume(0.5f);
		mainMenuTheme.play();
		menuSound = sampleGame.getAudio().createSound("MenuSound.mp3");
		earthHit = sampleGame.getAudio().createSound("EarthHit1.mp3");
		earthHit2 = sampleGame.getAudio().createSound("EarthHit2.wav");
		earthHit3 = sampleGame.getAudio().createSound("EarthHit3.mp3");
		earthDestroyed = sampleGame.getAudio().createSound("EarthExplosion.mp3");
		enemyLaser = sampleGame.getAudio().createSound("FirinMahLAYZAH.mp3");
		enemyHit = sampleGame.getAudio().createSound("Crunch1.mp3");
		enemyHit2 = sampleGame.getAudio().createSound("Crunch2.mp3");
		enemyHit3 = sampleGame.getAudio().createSound("Crunch3.mp3");
		friendlyBounce = sampleGame.getAudio().createSound("FriendlyCollision.wav");
		enemyExplode = sampleGame.getAudio().createSound("EnemyExplosion1.mp3");
		enemyExplode2 = sampleGame.getAudio().createSound("EnemyExplosion2.mp3");
	}
	
}
