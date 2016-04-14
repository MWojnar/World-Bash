package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Graphics;
import com.mwojnar.framework.Image;
import com.mwojnar.framework.Input.TouchEvent;

public class Entity {
	
	public boolean active, visible, animated, animationLoop, endAnimation;
	public double x, y, vSpeed, hSpeed, speed, direction, animationSpeed, animationSpeedCounter;
	public int frame;
	public Sprite sprite;
	
	public Entity() {
		
		x = 0;
		y = 0;
		vSpeed = 0;
		hSpeed = 0;
		speed = 0;
		direction = 0;
		active = true;
		visible = true;
		animated = true;
		frame = 0;
		sprite = null;
		animationLoop = true;
		endAnimation = false;
		animationSpeed = 1;
		animationSpeedCounter = 0;
		
	}
	
	public Entity setSprite(Sprite sprite) {
		
		this.sprite = sprite;
		frame = 0;
		endAnimation = false;
		return this;
		
	}
	
	public Entity setPos(double x, double y) {
		
		this.x = x;
		this.y = y;
		return this;
		
	}
	
	public void movePos(double x, double y) {
		
		this.x += x;
		this.y += y;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		if (active) {
			
			if (animated && sprite != null) {
				
				if (!endAnimation) {
					
					animationSpeedCounter++;
					if (animationSpeedCounter >= 1.0 / animationSpeed) {
						
						frame++;
						animationSpeedCounter -= 1.0 / animationSpeed;
						
					}
					
				}
				if (frame >= sprite.getFrames()) {
					
					if (animationLoop) {
						
						frame = 0;
						animationEnd();
						
					} else {
						
						frame = sprite.getFrames() - 1;
						endAnimation = true;
						animationEnd();
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void draw(Graphics g) {
		
		if (visible && sprite != null) {
			
			sprite.drawSprite((int)x, (int)y, frame, g);
			
		}
		
	}
	
	public void setVHSpeed(double vSpeed, double hSpeed) {
		
		this.vSpeed = vSpeed;
		this.hSpeed = hSpeed;
		speed = Math.sqrt(Math.pow(this.vSpeed, 2) + Math.pow(this.hSpeed, 2));
		direction = Math.atan2(this.hSpeed, this.vSpeed) - Math.PI / 2;
		
	}
	
	public void setDirSpeed(double speed, double direction) {
		
		this.speed = speed;
		this.direction = direction;
		vSpeed = -Math.sin(this.direction) * this.speed;
		hSpeed = Math.cos(this.direction) * this.speed;
		
	}
	
	public void addVHSpeed(double vSpeed, double hSpeed) {
		
		this.vSpeed += vSpeed;
		this.hSpeed += hSpeed;
		speed = Math.sqrt(Math.pow(this.vSpeed, 2) + Math.pow(this.hSpeed, 2));
		direction = Math.atan2(this.hSpeed, this.vSpeed) - Math.PI / 2;
		
	}
	
	public void addDirSpeed(double speed, double direction) {
		
		double tempVSpeed1 = -Math.sin(this.direction) * this.speed;
		double tempHSpeed1 = Math.cos(this.direction) * this.speed;
		double tempVSpeed2 = -Math.sin(direction) * speed;
		double tempHSpeed2 = Math.cos(direction) * speed;
		setVHSpeed(tempVSpeed1 + tempVSpeed2, tempHSpeed1 + tempHSpeed2);
		
	}
	
	public double addAngles(double angle1, double angle2) {
		
		double difference = Math.abs(angle1 - angle2);
		if (difference <= Math.PI) {
			
			if (angle1 > angle2)
				return (angle2 + difference / 2);
			else
				return (angle1 + difference / 2);
			
		} else {
			
			if (angle1 > angle2)
				angle1 -= Math.PI * 2;
			else
				angle2 -= Math.PI * 2;
			
			difference = Math.abs(angle1 - angle2);
			if (angle1 > angle2)
				return (angle2 + difference / 2);
			else
				return (angle1 + difference / 2);
			
		}
		
	}
	
	public void destroy() {
		
		GameScreen.entityList.remove(this);
		
	}
	
	public void animationEnd() {
	}
	
}
