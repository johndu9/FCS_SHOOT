package firstGame;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.polygon.FirstPolygon;
import firstEngine.polygon.MovingPolygon;

public class Comet extends MovingPolygon{	//class Comet
	
	private static Random random = new Random();
	private Arrow arrow = new Arrow(this);
	private Trail trail;
	private FirstPolygon goal;
	public boolean showTracker;
	protected String name = "comet";

	private static Color cometColor;
	private static Color trailColor;
	private static Color trackerColor;

	public Comet(int size, int speed, Point position, FirstPolygon goal, boolean showTracker){	//constructor
		
		super(sizeToPoints(size, size), random.nextDouble() * 2 * Math.PI, new Point(0, 0), cometColor, speed, 0);
		if(position.equals(0, 0)){
			int xPos = 0;
			int yPos = 0;
			if(random.nextDouble() > 0.75){
				xPos = random.nextInt(Display.getWidth());
			}
			else if(random.nextDouble() > 0.5){
				xPos = random.nextInt(Display.getWidth());
				yPos = Display.getHeight();
			}else if(random.nextDouble() > 0.25){
				yPos = random.nextInt(Display.getHeight());
			}
			else{
				xPos = Display.getWidth();
				yPos = random.nextInt(Display.getHeight());
			}
			setPosition(xPos, yPos);
		}else{
			setPosition(position);
		}
		setGoal(goal);
		trail = new Trail(this, trailColor, 75, 0.2f);
		this.showTracker = showTracker;
		
	}	//close constructor
	
	public static void init(Color comet, Color trail, Color tracker){
		
		cometColor = comet;
		trailColor = trail;
		trackerColor = tracker;
		
	}
	
	public void update(int delta){	//method update

		trail.update(delta);
		if(!getGoal().equals(FirstPolygon.EMPTY)){
			if(showTracker){
				renderTracker();
			}
			setDirection(getPosition().directionTo(getGoal().getPosition()));
		}else{
			wrapBounds(0, Display.getWidth(), 0, Display.getHeight());
		}
		super.update(delta);
		arrow.update(delta);
		
	}	//close method update
	
	public void freeze(){	//method freeze
		
		super.freeze();
		trail.freeze();
		
	}	//close method freeze
	
	public void unfreeze(){	//method unfreeze
		
		super.unfreeze();
		trail.unfreeze();
		
	}	//close method unfreeze
	
	public int size(){	//method size
		
		return 2 + trail.size();
		
	}	//close method size
	
	public FirstPolygon getGoal(){	//method getGoal
		
		return goal;
		
	}	//close method getGoal
	
	public void setGoal(FirstPolygon goal){	//method setGoal
		
		this.goal = goal;
		
	}	//close method setGoal
	
	public void scalePoint(float xScale, float yScale){	//method scalePoint
		
		setPosition(getPosition().scalePoint(xScale, yScale));
		trail.scalePoint(xScale, yScale);
		
	}	//close method scalePoint
	
	public static void setRandom(int levelSeed){
		random = new Random(levelSeed);
	}
	
	public void renderTracker(){	//method renderTracker
		
		GL11.glPushMatrix();
			
			GL11.glColor4f(
				trackerColor.getRed(),
				trackerColor.getGreen(),
				trackerColor.getBlue(),
				trackerColor.getAlpha());
			
			GL11.glLineWidth(8);
			GL11.glBegin(GL11.GL_LINES);
			
				GL11.glVertex2f(getPosition().x, getPosition().y);
				GL11.glVertex2f(getGoal().getPosition().x, getGoal().getPosition().y);
				
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}	//close method renderTracker
	
}	//close class Comet