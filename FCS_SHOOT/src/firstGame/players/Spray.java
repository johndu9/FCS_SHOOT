package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Spray extends Player{

	public Spray(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			if(getAction1() && shootDelay.getDelay(75)){
				playLaserSound();
				lasers.add(new Laser(getDirection() + getSpray(), shootPosition, getSpeed() - random.nextInt(4)));
				if(!isCharged()){
					charge += 8;
				}
			}

			if(isCharged() && getAction2() && chargedDelay.getDelay(50)){
				playLaserSound();
				lasers.add(
					new Laser(getDirection() - Math.PI / 3 + getSpray(), shootPosition,getSpeed() - random.nextInt(4)));
				lasers.add(
					new Laser(getDirection() + Math.PI / 3 + getSpray(), shootPosition,getSpeed() - random.nextInt(4)));
				charge -= 15;
			}
			
		}
		
	}
	
}