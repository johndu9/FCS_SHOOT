package firstEngine.polygon;

import firstEngine.Color;
import firstEngine.Point;

public class MovingPolygon extends MovablePolygon{	//class MovingPolygon

	public MovingPolygon(
		Point[] points,
		double direction,
		Point position,
		Color color,
		int speedFactor,
		int rotationFactor){	//constructor
		
		super(
			points,
			direction,
			position,
			color,
			speedFactor,
			rotationFactor);
		
	}	//close constructor
	
	public void update(int delta){	//method update
		
		super.update(delta);
		move(getDirection(), delta);
		turn(true, delta);
		
	}	//close method update
	
}	//close class MovingPolygon