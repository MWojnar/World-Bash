package com.mwojnar.game;

import com.mwojnar.framework.Game;
import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Screen;
import com.mwojnar.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("spr_title.png", ImageFormat.RGB565);
		Assets.bgSpace = g.newImage("background.png", ImageFormat.RGB565);
		Assets.earth = g.newImage("spr_earth.png", ImageFormat.RGB565);
		Assets.mars = g.newImage("spr_mars.png", ImageFormat.RGB565);
		Assets.tile = g.newImage("ts_loonyland.png", ImageFormat.RGB565);
		Assets.asteroid = g.newImage("spr_asteroid.png", ImageFormat.RGB565);
		Assets.asteroidParticle = g.newImage("spr_asteroid_particle.png", ImageFormat.RGB565);
		Assets.health = g.newImage("spr_health_strip4.png", ImageFormat.RGB565);
		Assets.explodingEarth = g.newImage("spr_earth_explosion_strip9.png", ImageFormat.RGB565);
		Assets.blackAsteroid = g.newImage("spr_black_asteroid.png", ImageFormat.RGB565);
		Assets.bigAsteroid = g.newImage("spr_asteroid_big.png", ImageFormat.RGB565);
		Assets.UFO = g.newImage("spr_ufo.png", ImageFormat.RGB565);
		Assets.enemyLaserImage = g.newImage("spr_ufo_shot_strip2.png", ImageFormat.RGB565);
		Assets.UFOExplode = g.newImage("spr_ufo_explosion_strip6.png", ImageFormat.RGB565);
		Assets.jupiter = g.newImage("spr_jupiter.png", ImageFormat.RGB565);
		Assets.pluto = g.newImage("spr_pluto.png", ImageFormat.RGB565);
		Assets.deathStar = g.newImage("spr_deathstar.png", ImageFormat.RGB565);
		Assets.powerUp = g.newImage("spr_power_up.png", ImageFormat.RGB565);
		Assets.satellite = g.newImage("spr_satellite.png", ImageFormat.RGB565);
		Assets.planetChunkMars = g.newImage("spr_mars_chunk.png", ImageFormat.RGB565);
		Assets.planetChunkJupiter = g.newImage("spr_jupiter_chunk.png", ImageFormat.RGB565);
		Assets.planetChunkPluto = g.newImage("spr_pluto_chunk.png", ImageFormat.RGB565);
		Assets.shield = g.newImage("spr_invincibility_strip7.png", ImageFormat.RGB565);
		Assets.fireball = g.newImage("spr_fire_trail_strip5.png", ImageFormat.RGB565);
		Assets.marsCracked = g.newImage("spr_mars_cracked.png", ImageFormat.RGB565);
		Assets.jupiterCracked = g.newImage("spr_jupiter_cracked.png", ImageFormat.RGB565);
		Assets.plutoCracked = g.newImage("spr_pluto_cracked.png", ImageFormat.RGB565);
		Assets.satelliteExplosion = g.newImage("spr_satellite_explosion_strip8.png", ImageFormat.RGB565);

		//This is how you would load a sound if you had one.
		//Assets.click = game.getAudio().createSound("explode.ogg");

		
		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint(float deltaTime) {
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}