package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Discharge extends Player{

	public Discharge(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			
			if(getAction1() && shootDelay.getDelay(500)){

				playLaserSound();
				for(int i = 0; i < 36; i++){	//for
	
					double direction = i * Math.PI / 18;
					Point position = 
						new Point(
							(float)(getPosition().x + Math.cos(direction) * 48),
							(float)(getPosition().y + Math.sin(direction) * 48));			
					lasers.add(new Laser(direction, position, getSpeed()));
				
				}	//close for
				
				if(!isCharged()){
					charge += 40;
				}
				
			}
			
			if(isCharged() && getAction2() && chargedDelay.getDelay(300)){

				playLaserSound();
				for(int i = 0; i < 36; i++){	//for
	
					double direction = i * Math.PI / 18;
					Point position = 
						new Point(
							(float)(getPosition().x + Math.cos(direction) * 48),
							(float)(getPosition().y + Math.sin(direction) * 48));			
					lasers.add(new Laser(direction, position, getSpeed() + 3));
				
				}	//close for
				
				charge -= 200;
				
			}
			
			for(int i = 0; i < lasers.size(); i++){
				if(lasers.get(i).getPosition().distanceTo(getPosition()) > 128 && lasers.get(i).getSpeed() == getSpeed()){
					lasers.remove(i);
				}
			}
			
		}
		
	}
	
}