package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Glob extends Player{

	public Glob(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			
			if(getAction1() && shootDelay.getDelay(400)){
				
				playLaserSound();
				for(int i = 0; i < 20; i++){	//for

					double direction = i * Math.PI / 10 + getSpray();
					Point position = 
						new Point(
							(float)(shootPosition.x + Math.cos(direction) * 32),
							(float)(shootPosition.y + Math.sin(direction) * 32));
					lasers.add(new Laser(getDirection(), position, getSpeed() - random.nextInt(4)));
				
				}	//close for
				
				if(!isCharged()){
					charge += 25;
				}
				
			}
			
			if(isCharged() && getAction2() && chargedDelay.getDelay(300)){
				
				playLaserSound();
				double originalDirection = getDirection();
				for(int j = 0; j < 8; j++){
					setDirection(j * Math.PI / 4 + getDirection());
					for(int i = 0; i < 20; i++){	//for
	
						double direction = i * Math.PI / 10 + getSpray();
						Point position = 
							new Point(
								(float)(shootPosition.x + Math.cos(direction) * 32),
								(float)(shootPosition.y + Math.sin(direction) * 32));
						lasers.add(new Laser(getDirection(), position, getSpeed() - random.nextInt(4)));
					
					}	//close for
				}
				setDirection(originalDirection);
				charge -= 100;
				
			}
			
		}
		
	}
	
}