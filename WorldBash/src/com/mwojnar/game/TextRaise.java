package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Input.TouchEvent;

import android.graphics.Paint;
import android.graphics.Paint.Align;

public class TextRaise extends Entity {
	
	private Paint paint;
	private String text;
	private int transparency;
	
	public TextRaise(double x, double y, String text) {
		
		super();
		setPos(x, y);
		paint = new Paint();
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(10);
		vSpeed = -1;
		this.text = text;
		transparency = 255;
		
	}
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			movePos(hSpeed, vSpeed);
			transparency -= 5;
			if (transparency <= 0) {
				
				transparency = 0;
				destroy();
				
			}
			
		}
		
	}
	
	public void draw(Graphics g) {

		if (visible) {
			
			paint.setARGB(transparency, 255, 255, 255);
			g.drawString(text, (int)x, (int)y, paint);
			
		}
		
	}
	
}
