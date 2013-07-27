package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Spread extends Player{

	public Spread(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){

			if(getAction1() && shootDelay.getDelay(550)){
				playLaserSound();
				for(int i = 0; i < 4; i++){	//for
					lasers.add(
						new Laser(
							getDirection() - Math.PI / 8 + i * Math.PI / 16,
							shootPosition,
							getSpeed() - random.nextInt(4)));
				}	//close for
				if(!isCharged()){
					charge += 50;
				}
			}

			if(isCharged() && getAction2() && chargedDelay.getDelay(300)){
				playLaserSound();
				for(int i = 0; i < 16; i++){	//for
					lasers.add(
						new Laser(
							getDirection() + i * Math.PI / 8,
							getPosition(),
							getSpeed() / 2 + random.nextInt(8)));
				}	//close for
				charge -= 100;
			}
			for(int i = 0; i < lasers.size(); i++){
				lasers.get(i).setDirection(lasers.get(i).getDirection() + getSpray());
			}
			
		}
		
	}
	
}