package firstGame.players;

import java.util.ArrayList;

import firstEngine.Point;
import firstGame.Comet;
import firstGame.Laser;

public class Clock extends Player{
	
	private ArrayList<Comet> comets;

	public Clock(Point position, int[] controls, ArrayList<Comet> comets) {
		super(position, controls);
		this.comets = comets;
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			
			if(getAction1() && shootDelay.getDelay(150)){
				playLaserSound();
				lasers.add(new Laser(getDirection(), getPosition(), getSpeed() * 3 / 2));
				if(!isCharged()){
					charge += 20;
				}
			}
			if(isCharged() && getAction2()){
				for(Comet c : comets){
					c.setSpeed(c.getSpeed() / 4);
				}
				charge = 0;
			}
			
		}
		
	}
	
}