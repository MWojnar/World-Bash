package com.mwojnar.game;

import java.util.ArrayList;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Image;


public class Sprite {

	public Image image;
	public int frames, frameWidth;

	public Sprite(Image image, int frames) {
		
		this.image = image;
		this.frames = frames;
		this.frameWidth = image.getWidth() / frames;
		
	}
	
	public void drawSprite(int x, int y, int frame, Graphics g) {
		
		g.drawImage(image, x - GameScreen.view.x, y - GameScreen.view.y, frame * frameWidth, 0, frameWidth, image.getHeight());
		
	}
	
	public void drawSprite(int x, int y, double rotation, Graphics g) {
		
		g.drawImage(image, x - GameScreen.view.x, y - GameScreen.view.y, rotation);
		
	}
	
	public int getFrames() {
		
		return frames;
		
	}

	public Image getImage() {
		
		return image;
		
	}
}
