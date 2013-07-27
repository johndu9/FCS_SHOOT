package firstEngine.polygon;

import java.awt.*;
import java.awt.geom.Area;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import firstEngine.Color;
import firstEngine.Point;

public class FirstPolygon{	//class FirstPolygon
	
	private Point[] points;
	private Point position;
	private Point origin;
	private Color color;
	private Polygon hitbox = new Polygon();
	private double direction;
	protected static Point renderPosition = new Point(0, 0);
	public static final FirstPolygon EMPTY = new FirstPolygon(
		sizeToPoints(0),
		0,
		new Point(0, 0),
		new Color(0, 0, 0, 0));
	
	public FirstPolygon(
		Point[] points,
		double direction,
		Point position,
		Color color){	//constructor
		
		setPoints(points);
		setDirection(direction);
		setPosition(position);
		setColor(color);
		setHitbox();
		origin = new Point(position);
		
	}	//close constructor
	
	public boolean isInPolygon(int x, int y){	//method isInPolygon
		
		return getHitbox().contains(x, y);
		
	}	//close method isInPolygon
	
	public boolean hits(FirstPolygon other){	//method hits
		
		Area thisArea = new Area(getHitbox());
		Area thatArea = new Area(other.getHitbox());
		
		thisArea.intersect(thatArea);
		
		return !thisArea.isEmpty();
		
	}	//close method hits
	
	public void update(int delta){	//method update
		
		setHitbox();
		if(getColor().getAlpha() > 0) render();

	}	//close method update
	
	public void wrapBounds(int minX, int maxX, int minY, int maxY){	//method wrapBounds
		
		if(getPosition().x < minX) getPosition().x = maxX;
		if(getPosition().x > maxX) getPosition().x = minX;
		if(getPosition().y < minY) getPosition().y = maxY;
		if(getPosition().y > maxY) getPosition().y = minY;
		
	}	//close method wrapBounds
	
	public boolean outBounds(int minX, int maxX, int minY, int maxY){	//method outBounds
		
		if(
			getPosition().x < minX ||
			getPosition().x > maxX ||
			getPosition().y < minY || 
			getPosition().y > maxY)
			return true;
		else
			return false;
		
	}	//close method outBounds
	
	public int getHeight(){	//method getHeight
		
		int[] ypoints = new int[getPoints().length];
		for(int i = 0; i < ypoints.length; i++) ypoints[i] = (int)getPoints()[i].y;
		return difmaxmin(ypoints);
		
	}	//close method getHeight
	
	public int getWidth(){	//method getWidth
		
		int[] xpoints = new int[getPoints().length];
		for(int i = 0; i < xpoints.length; i++) xpoints[i] = (int)getPoints()[i].x;
		return difmaxmin(xpoints);
		
	}	//close method getWidth
	
	public Point[] getPoints(){	//method getPoints
		
		return points;
		
	}	//close method getPoints
	
	public void setPoints(Point[] points){	//method setPoints
		
		this.points = points;
		
	}	//close method setPoints
	
	public Point getOrigin(){
		return origin;
	}
	
	public Point getPosition(){	//method getPosition
		
		return position;
		
	}	//close method getPosition

	public void setPosition(Point position){	//method setPosition
		
		this.position = position;
		
	}	//close method setPosition

	public void setPosition(float x, float y){	//method setPosition
		
		setPosition(new Point(x, y));
		
	}	//close method setPosition
	
	public static Point getRenderPosition(){	//method getRenderPosition
		
		return renderPosition;
		
	}	//close method getRenderPosition

	public static void setRenderPosition(Point newRenderPosition){	//method setRenderPosition
		
		renderPosition = newRenderPosition;
		
	}	//close method setRenderPosition

	public static void setRenderPosition(float x, float y){	//method setRenderPosition
		
		setRenderPosition(new Point(x, y));
		
	}	//close method setRenderPosition

	public Color getColor(){	//method getColor
		
		return color;
		
	}	//close method getColor

	public void setColor(Color color){	//method setColor
		
		this.color = color;
		
	}	//close method setColor

	public Polygon getHitbox(){	//method getHitbox
		
		return hitbox;
		
	}	//close method getControl

	public void setHitbox(Polygon hitbox){	//method setHitbox
		
		this.hitbox = hitbox;
		
	}	//close method setHitbox
	
	public void setHitbox(){	//method setHitbox

		int[] xpoints = new int[getPoints().length];
		int[] ypoints = new int[getPoints().length];
		
		for(int i = 0; i < getPoints().length; i++){
			xpoints[i] =
				(int)(getPosition().x +
				getPoints()[i].x * Math.cos(getDirection()) - getPoints()[i].y * Math.sin(getDirection()));
			ypoints[i] =
				Display.getHeight() -
				(int)(getPosition().y +
				getPoints()[i].y * Math.cos(getDirection()) + getPoints()[i].x * Math.sin(getDirection()));
		}
		setHitbox(new Polygon(xpoints, ypoints, getPoints().length));		
		
	}	//close method setHitbox

	public double getDirection(){	//method getDirection
		
		return direction;
		
	}	//close method getDirection

	public void setDirection(double direction){	//method setDirection
		
		this.direction = direction;
		
	}	//close method setDirection
	
	public void wrapDirection(){	//method wrapDirection

		if(getDirection() < -2 * Math.PI || getDirection() > 2 * Math.PI){
			setDirection(getDirection() - (int)(getDirection() / (2 * Math.PI)) * 2 * Math.PI);
		}
		
	}	//close method wrapDirection

	public static Point[] sizeToPoints(int x, int y){	//method sizeToPoints

		Point[] points = {
			new Point(-x / 2, -y / 2),
			new Point(-x / 2, y / 2),
			new Point(x / 2, y / 2),
			new Point(x / 2, -y / 2)};
		
		return points;
		
	}	//close method sizeToPoints

	public static Point[] sizeToPoints(int size){	//method sizeToPoints

		return sizeToPoints(size, size);
		
	}	//close method sizeToPoints
	
	public void renderDebug(){	//method renderDebug
		
		renderHitbox();
		renderDirection();
		renderPosition();
		
	}	//close method renderDebug
	
	public void renderHitbox(){	//method renderHitbox

		GL11.glPushMatrix();
			GL11.glColor4f(
				1.0f,
				0.0f,
				0.0f,
				0.5f);
			GL11.glTranslatef(getPosition().x, getPosition().y, 0);
			GL11.glTranslatef(-getPosition().x, -getPosition().y, 0);
			
			GL11.glBegin(GL11.GL_POLYGON);
			
				for(int i = 0; i < getHitbox().npoints; i++){
					GL11.glVertex2f(getHitbox().xpoints[i], Display.getHeight() - getHitbox().ypoints[i]);
				}
			
			GL11.glEnd();
		GL11.glPopMatrix();
		
	}	//close method renderHitbox
	
	public void renderDirection(){	//method renderDirection
		
		GL11.glPushMatrix();
			
			GL11.glColor4f(
				1.0f,
				0.0f,
				0.0f,
				0.5f);
			
			GL11.glTranslatef(getPosition().x, getPosition().y, 0);
			GL11.glRotatef((float)Math.toDegrees(getDirection() - Math.PI / 2), 0f, 0f, 1.0f);
			
			GL11.glLineWidth(10);
			GL11.glBegin(GL11.GL_LINES);
			
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(0, (float)Math.sqrt(Math.pow(Display.getWidth(), 2) + Math.pow(Display.getHeight(), 2)));
				
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}	//close method renderDirection
	
	public void renderPosition(){	//method renderPosition
		
		GL11.glPushMatrix();
			GL11.glColor4f(
				1.0f,
				0.0f,
				0.0f,
				0.5f);
			
			GL11.glPointSize(10);
			GL11.glBegin(GL11.GL_POINTS);
			
					GL11.glVertex2f(getPosition().x, getPosition().y);
			
			GL11.glEnd();
		GL11.glPopMatrix();
		
	}	//close method renderPosition
	
	public void renderCoord(){	//method renderCoord
		
		GL11.glPushMatrix();

			GL11.glColor4f(
				1.0f,
				0.0f,
				0.0f,
				0.5f);
			
			GL11.glLineWidth(10);
			GL11.glBegin(GL11.GL_LINES);
				
				GL11.glVertex2f(0, getPosition().y);
				GL11.glVertex2f(Display.getWidth(), getPosition().y);
				GL11.glVertex2f(getPosition().x, 0);
				GL11.glVertex2f(getPosition().x, Display.getHeight());
				
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}	//close method renderCoord
	
	public void render(){	//method render
		
		GL11.glPushMatrix();
		
			GL11.glColor4f(
				getColor().getRed(),
				getColor().getGreen(),
				getColor().getBlue(),
				getColor().getAlpha());
				
			GL11.glTranslatef(renderPosition.x + getPosition().x, renderPosition.y + getPosition().y, 0);
			GL11.glRotatef((float)Math.toDegrees(getDirection()), 0f, 0f, 1.0f);
			GL11.glTranslatef(-(renderPosition.x + getPosition().x), -(renderPosition.y + getPosition().y), 0);
			
			GL11.glBegin(GL11.GL_POLYGON);
			
				for(int i = 0; i < getPoints().length; i++){	//for
							
					GL11.glVertex2f(
						renderPosition.x + getPosition().x + getPoints()[i].x,
						renderPosition.y + getPosition().y + getPoints()[i].y);
					
				}	//close for
				
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}	//close method render
	
	private int difmaxmin(int[] numbers){	//method difmaxmin
		
		int max = 0;
		int min = 0;
		for(int i = 0; i < numbers.length; i++){	//for
			if(numbers[i] > max) max = numbers[i];
			if(numbers[i] < min) min = numbers[i];
		}	//close for
		return max - min;
		
	}	//close method difmaxmin
	
}	//close class FirstPolygon