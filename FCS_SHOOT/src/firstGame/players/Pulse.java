package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Pulse extends Player{

	public Pulse(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			if(getAction1() && shootDelay.getDelay(200)){
				playLaserSound();
				for(int i = 0; i < 8; i++){	//for
					lasers.add(new Laser(getDirection() - Math.PI / 8 + i * Math.PI / 32, shootPosition, getSpeed()));
				}	//close for
				if(!isCharged()){
					charge += 20;
				}
			}
			if(isCharged() && getAction2() && chargedDelay.getDelay(100)){
				playLaserSound();
				for(int j = 0; j < 4; j++){
					for(int i = 0; i < 8; i++){	//for
						lasers.add(
							new Laser(
								getDirection() + j * 2 * Math.PI / 4 - Math.PI / 8 + i * Math.PI / 32,
								getPosition(),
								getSpeed()));
					}	//close for
				}
				charge -= 30;
			}
			for(int i = 0; i < lasers.size(); i++){
				if(
					lasers.get(i).getPosition().distanceTo(lasers.get(i).getOrigin()) > 192){
					lasers.remove(i);
				}
			}
		}
	}
}