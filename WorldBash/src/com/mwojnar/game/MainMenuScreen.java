package com.mwojnar.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.mwojnar.framework.Game;
import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Screen;
import com.mwojnar.framework.Input.TouchEvent;

public class MainMenuScreen extends Screen {
	
	private double oscillation, amplitude;
	private Paint paint;
	
	public MainMenuScreen(Game game) {
		super(game);
		oscillation = 0;
		amplitude = 10;
		
		paint = new Paint();
		paint.setTextSize(12);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				game.setScreen(new GameScreen(game));
				GameScreen.playSound(Assets.menuSound, 0.85f);
				GameScreen.playMusic(Assets.mainTheme);
			}
		}
		oscillation += .05;
		if (oscillation > Math.PI * 2)
			oscillation -= Math.PI * 2;
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.bgSpace, 0, 0);
		double oscillation2 = 0;
		if (oscillation <= Math.PI) {
			
			oscillation2 = oscillation;
			
		}
		else {
			
			oscillation2 = Math.PI * 2 - oscillation;
			
		}
		oscillation2 = Math.cos(oscillation2) * Math.PI;
		g.drawImage(Assets.menu, 200 - Assets.menu.getWidth() / 2, (int) (120 - Assets.menu.getHeight() / 2 + Math.sin(oscillation) * amplitude), oscillation2 * 2 * 180 / Math.PI / 24);
		g.drawString("Made by: Michael Wojnar, Tony Wojnar, Fisher Moses", 0, 238, paint);
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
        android.os.Process.killProcess(android.os.Process.myPid());

	}
}
