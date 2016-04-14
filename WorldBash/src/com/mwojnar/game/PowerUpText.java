package com.mwojnar.game;

import java.util.List;

import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

public class PowerUpText extends Entity {
	
	public String text;
	public int opacity;
	public Paint paint;
	
	PowerUpText(String text) {
		
		super();
		paint = new Paint();
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(50);
		this.text = text;
		opacity = 255;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			opacity -= 5;
			if (GameScreen.entityList.get(GameScreen.entityList.size() - 1) != this) {
				
				GameScreen.entityList.remove(this);
				GameScreen.entityList.add(this);
				
			}
			if (opacity <= 0)
				destroy();
			
		}
		
	}
	
	public void draw(Graphics g) {
		
		super.draw(g);
		if (visible) {
			
			paint.setARGB(opacity, 255, 255, 255);
			g.drawString(text, 200, 120 + 25, paint);
			
		}
		
	}
	
}
