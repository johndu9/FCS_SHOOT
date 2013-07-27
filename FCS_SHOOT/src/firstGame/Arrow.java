package firstGame;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.polygon.FirstPolygon;

public class Arrow extends FirstPolygon{	//class Arrow
	
	private FirstPolygon polygon;
	
	public Arrow(FirstPolygon polygon){	//constructor
		
		super(
			new Point[]{
				new Point(polygon.getWidth(), polygon.getHeight() / 4),
				new Point(polygon.getWidth(), polygon.getHeight()/ -4),
				new Point(polygon.getWidth() + polygon.getHeight() / 4, 0)},
			polygon.getDirection(), 
			polygon.getPosition(), 
			new Color(
				polygon.getColor().getRed(),
				polygon.getColor().getGreen(),
				polygon.getColor().getBlue(),
				polygon.getColor().getAlpha() / 2));
		this.polygon = polygon;
		
	}	//close constructor
	
	public void update(int delta){	//method update
		
		super.update(delta);
		setPosition(polygon.getPosition());
		setDirection(polygon.getDirection());
		
	}	//close method update
	
}	//close class Arrow