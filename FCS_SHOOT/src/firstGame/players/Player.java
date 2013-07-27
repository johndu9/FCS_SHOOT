package firstGame.players;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.Sound;
import firstEngine.Timer;
import firstEngine.polygon.ControllablePolygon;
import firstEngine.polygon.FirstPolygon;
import firstGame.Arrow;
import firstGame.Laser;
import firstGame.Trail;

public class Player extends ControllablePolygon{
	
	private static Sound[] laserSounds = new Sound[8];
	private static Color craftColor;
	private static Color trailColor;
	protected static Random random = new Random();
	
	public static final int MAXCHARGE = 1000;
	
	protected ArrayList<Laser> lasers = new ArrayList<Laser>();
	private Trail trail;
	private Arrow arrow = new Arrow(this);
	protected Timer shootDelay = new Timer();
	protected Timer chargedDelay = new Timer();
	protected Point shootPosition;
	
	protected int charge;
	private boolean charged;
	
	private static int playerCount = 0;
	private int playerNum;

	public Player(Point position, int[] controls){
		
		super(FirstPolygon.sizeToPoints(64), 0, position, craftColor, 600, 500, controls);
		trail = new Trail(this, trailColor, 50, 0.1f);
		charge = 0;
		charged = false;
		playerNum = playerCount;
		playerCount++;

	}
	
	public void update(int delta){

		for(int i = 0; i < lasers.size(); i++){
			lasers.get(i).update(delta);
			if(lasers.get(i).outBounds(0, Display.getWidth(), 0, Display.getHeight())){
				lasers.remove(i);
//				lasers.get(i).wrapBounds(0, Display.getWidth(), 0, Display.getHeight());
			}
		}
		trail.update(delta);
		super.update(delta);
		arrow.update(delta);
		wrapBounds(0, Display.getWidth(), 0, Display.getHeight());
		
		shootPosition = new Point(
			getPosition().x + 48 * (float)Math.cos(getDirection()),
			getPosition().y + 48 * (float)Math.sin(getDirection()));
		
		if(charge >= 1000){
			charge = 1000;
			charged = true;
		}else if(charge <= 0){
			charge = 0;
			charged = false;
		}
		
	}
	
	public static void init(Color craft, Color trail){	//method init

		for(int i = 0; i < laserSounds.length; i++){
			laserSounds[i] = new Sound("laser" + i);
			laserSounds[i].setVolume(90);
		}
		
		craftColor = craft;
		trailColor = trail;
		
	}	//close method init
	
	public int size(){
		
		int size = 2 + trail.size();
		
		for(int i = 0; i < lasers.size(); i++){
			size += lasers.get(i).size();
		}
		
		return size;
		
	}
	
	public void freeze(){	//method freeze
		
		super.freeze();
		trail.freeze();
		for(int i = 0; i < lasers.size(); i++){
			lasers.get(i).freeze();
		}
		
	}	//close method freeze
	
	public void unfreeze(){	//method freeze
		
		super.unfreeze();
		trail.unfreeze();
		for(int i = 0; i < lasers.size(); i++){
			lasers.get(i).unfreeze();
		}
		
	}	//close method freeze
	
	public void renderDebug(){
		
		super.renderDebug();
		for(int i = 0; i < lasers.size(); i++){
			lasers.get(i).renderDebug();
		}
		
	}
	
	public static double getSpray(){	//method getSpray
		
		return (random.nextDouble() - 0.5) * Math.PI / 9;
		
	}	//close method getSpray

	public ArrayList<Laser> getLasers(){	//method getLasers
		
		return lasers;
		
	}	//close method getLasers

	public void setLasers(ArrayList<Laser> lasers){	//method setLasers
		
		this.lasers = lasers;
		
	}	//close method setLasers
	
	public void removeLaser(int index){	//method removeLaser
		
		lasers.remove(index);
		
	}	//close method removeLaser
	
	public void scalePoint(float xScale, float yScale){	//method scalePoint
		
		setPosition(getPosition().scalePoint(xScale, yScale));
		trail.scalePoint(xScale, yScale);
		for(int i = 0; i < lasers.size(); i++){
			lasers.get(i).scalePoint(xScale, yScale);
		}
		
	}	//close method scalePoint
	
	public int getCharge(){
		return charge;
	}
	
	public boolean isCharged(){
		return charged;
	}
	
	public int getPlayerNum(){
		return playerNum;
	}
	
	public static int getPlayerCount(){
		return playerCount;
	}
	
	public static void resetPlayerCount(){
		playerCount = 0;
	}
	
	protected void playLaserSound(){
		
		laserSounds[random.nextInt(laserSounds.length)].play();
		
	}
	
}