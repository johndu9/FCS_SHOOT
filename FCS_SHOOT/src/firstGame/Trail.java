package firstGame;

import java.util.ArrayList;
import java.util.Random;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.Timer;
import firstEngine.Toggle;
import firstEngine.polygon.FirstPolygon;
import firstEngine.polygon.MovingPolygon;

public class Trail{	//class Trail
	
	private ArrayList<MovingPolygon> trail = new ArrayList<MovingPolygon>();
	private int decayDelay;
	private Timer decay = new Timer();
	private Color color;
	private FirstPolygon polygon;
	private boolean frozen;
	private Random random = new Random();
	private Point prevPosition = new Point(0, 0);
	private float decayAmount;
	public static Toggle showTrail;
	
	public Trail(FirstPolygon polygon, Color color, int decayDelay, float decayAmount){	//constructor
		
		setPolygon(polygon);
		setColor(color);
		setDecayDelay(decayDelay);
		setDecayAmount(decayAmount);
		
	}	//close constructor
	
	public static void init(boolean showTrail){
		
		Trail.showTrail = new Toggle(showTrail);
		
	}
	
	public void update(int delta){	//method update
		
		if(showTrail.getState()){	//if
			
			for(int i = 0; i < trail.size(); i++){	//for
				trail.get(i).update(delta);
				if(trail.get(i).getColor().getAlpha() < 0){	//if
					trail.remove(i);
				}	//close if
			}	//close for
			if(!frozen){	//if
				if(prevPosition.distanceTo(polygon.getPosition()) > (polygon.getWidth() + polygon.getHeight()) / 4){
					prevPosition = new Point(polygon.getPosition());
					trail.add(
						new MovingPolygon(
							FirstPolygon.sizeToPoints(polygon.getWidth() / 2, polygon.getHeight() / 2),
							random.nextDouble() * 2 * Math.PI,
							polygon.getPosition(),
							new Color(color.getValues()),
							0, 15));
				}	//close if
				
				if(decay.getDelay(decayDelay)){	//if
					for(int i = 0; i < trail.size(); i++){	//for
						trail.get(i).getColor().setAlpha(trail.get(i).getColor().getAlpha() - getDecayAmount());
					}	//close for
				}	//close if
			}	//close if
		}	//close if
		
	}	//close method update
	
	public void scalePoint(float xScale, float yScale){	//method scalePoint
		
		for(int i = 0; i < trail.size(); i++){	//for
			trail.get(i).setPosition(trail.get(i).getPosition().scalePoint(xScale, yScale));
		}	//close for
		
	}	//close method scalePoint
	
	public void freeze(){	//method freeze
		
		frozen = true;
		for(int i = 0; i < trail.size(); i++){	//for
			trail.get(i).freeze();
		}	//close for
		
	}	//close method freeze
	
	public void unfreeze(){	//method unfreeze
		
		frozen = false;
		for(int i = 0; i < trail.size(); i++){	//for
			trail.get(i).unfreeze();
		}	//close for
		
	}	//close method unfreeze
	
	public int size(){	//method size
		
		return trail.size();
		
	}	//close method size
	
	public FirstPolygon getPolygon(){	//method getPolygon
		
		return polygon;
		
	}	//close method getPolygon
	
	public void setPolygon(FirstPolygon polygon){	//method setPolygon
		
		this.polygon = polygon;
		
	}	//close method setPolygon
	
	public Color getColor(){	//method getColor
		
		return color;
		
	}	//close method getColor
	
	public void setColor(Color color){	//method setColor
		
		this.color = color;
		
	}	//close method setColor
	
	public int getDecayDelay(){	//method getDecayDelay
		
		return decayDelay;
		
	}	//close method getDecayDelay
	
	public void setDecayDelay(int decayDelay){	//method setDecayDelay
		
		this.decayDelay = decayDelay;
		
	}	//close method setDecayDelay
	
	public float getDecayAmount(){	//method getDecayAmount
		
		return decayAmount;
		
	}	//close method getDecayAmount
	
	public void setDecayAmount(float decayAmount){	//method setDecayAmount
		
		this.decayAmount = decayAmount;
		
	}	//close method setDecayAmount
	
}	//close class Trail