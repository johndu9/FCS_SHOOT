package firstGame;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.polygon.FirstPolygon;
import firstEngine.polygon.MovingPolygon;

public class Laser extends MovingPolygon{	//class Laser

	private static Color laserColor;
	private static Color overlayColor;
	private static Color glowColor;
	private static Color trailColor;
	
	private Trail trail;
	private FirstPolygon overlay;
	private FirstPolygon glow;
	private Point origin;

	public Laser(double direction, Point position, int speedFactor){	//constructor

		super(sizeToPoints(16), direction, position, laserColor, speedFactor, 0);
		
		overlay = new FirstPolygon(FirstPolygon.sizeToPoints(10), direction, position, overlayColor);
		glow = new FirstPolygon(FirstPolygon.sizeToPoints(24), direction, position, glowColor);
		trail = new Trail(this, trailColor, 50, 0.2f);
		origin = new Point(position);
		
	}	//close constructor
	
	public static void init(Color laser, Color overlay, Color glow, Color trail){
		
		laserColor = laser;
		overlayColor = overlay;
		glowColor = glow;
		trailColor = trail;
		
	}
	
	public void update(int delta){	//method update

		trail.update(delta);
		glow.update(delta);
		super.update(delta);
		overlay.update(delta);
		glow.setPosition(getPosition());
		glow.setDirection(getDirection());
		overlay.setPosition(getPosition());
		overlay.setDirection(getDirection());
		
	}	//close method update
	
	public void freeze(){	//method freeze
		
		super.freeze();
		trail.freeze();
		
	}	//close method freeze
	
	public void unfreeze(){	//method unfreeze
		
		super.unfreeze();
		trail.unfreeze();
		
	}	//close method unfreeze
	
	public int size(){
		
		return 3 + trail.size();
		
	}
	
	public Point getOrigin(){	//method getOrigin
		
		return origin;
		
	}	//close method getOrigin
	
	public void scalePoint(float xScale, float yScale){	//method scalePoint
		
		setPosition(getPosition().scalePoint(xScale, yScale));
		trail.scalePoint(xScale, yScale);
		
	}	//close method scalePoint
	
}	//close class Laser