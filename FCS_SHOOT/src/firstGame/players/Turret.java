package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Turret extends Player{

	public Turret(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			
			if(getAction1() && shootDelay.getDelay(200)){
				shoot();
				if(!isCharged()){
					charge += 15;
				}
			}
			if(isCharged() && getAction2() && chargedDelay.getDelay(10)){
				shoot();
				charge -= 10;
			}
			
		}
		
	}
	
	private void shoot(){

		playLaserSound();
		Point beamPosition1 = 
			new Point(
				(float)(getPosition().x + Math.cos(getDirection() + Math.PI / 8) * 64),
				(float)(getPosition().y + Math.sin(getDirection() + Math.PI / 8) * 64));
		Point beamPosition2 = 
			new Point(
				(float)(getPosition().x + Math.cos(getDirection() - Math.PI / 8) * 64),
				(float)(getPosition().y + Math.sin(getDirection() - Math.PI / 8) * 64));
		lasers.add(new Laser(getDirection(), beamPosition1, getSpeed()));
		lasers.add(new Laser(getDirection(), beamPosition2, getSpeed()));
		
	}
	
}