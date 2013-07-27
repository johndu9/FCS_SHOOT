package firstEngine.polygon;

import firstEngine.Color;
import firstEngine.Point;

public class MovablePolygon extends FirstPolygon{	//class MovablePolygon
	
	private int speed;//~pixels per second
	private int rotation;//~degrees per second
	protected boolean frozen;
	
	public MovablePolygon(
		Point[] points,
		double direction,
		Point position,
		Color color,
		int speed,
		int rotation){	//constructor
		
		super(points,
			direction,
			position,
			color);
		setSpeed(speed);
		setRotation(rotation);
		setDirection(direction);
		
	}	//close constructor
	
	public void update(int delta){	//method update
		
		super.update(delta);
		
	}	//close method update

	public int getSpeed(){	//method getSpeed
		
		return speed;
		
	}	//close method getSpeed

	public void setSpeed(int speed){	//method setSpeed
		
		this.speed = speed;
		
	}	//close method setSpeed

	public int getRotation(){	//method getRotation
		
		return rotation;
		
	}	//close method getRotation

	public void setRotation(int rotation){	//method setRotation
		
		this.rotation = rotation;
		
	}	//close method setRotation
	
	public boolean isFrozen(){	//method isFrozen
		
		return frozen;
		
	}	//close method isFrozen
	
	public void freeze(){
		frozen = true;
	}
	
	public void unfreeze(){
		frozen = false;
	}
	
	public void move(int speed, double direction, int delta){	//method move

		if(!frozen){
			setPosition(
				getPosition().x + speed * ((float)delta / 1000) * (float)Math.cos(direction),
				getPosition().y + speed * ((float)delta / 1000) * (float)Math.sin(direction));
		}
		
	}	//close method move
	
	public void move(double direction, int delta){
		move(speed, direction, delta);
	}
	
	public void move(int delta){
		move(speed, getDirection(), delta);
	}
	
	public void turn(boolean clockwise, int delta){	//method turn
		
		int i;
		if(clockwise) i = 1;
		else i = -1;
		if(!frozen){
			setDirection(getDirection() + i * Math.toRadians(((float)delta / 1000) * rotation));
		}
		
	}	//close method turn
	
}	//close class MovablePolygon