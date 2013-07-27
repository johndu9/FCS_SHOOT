package firstGame.players;

import firstEngine.Point;
import firstGame.Laser;

public class Burst extends Player{

	public Burst(Point position, int[] controls){
		
		super(position, controls);
		
	}
	
	public void update(int delta){
		
		super.update(delta);
		
		if(!frozen){
			if(getAction1() && shootDelay.getDelay(200)){
				playLaserSound();
				lasers.add(new Laser(getDirection(), shootPosition, getSpeed()));
				if(!isCharged()){
					charge += 20;
				}
			}
			for(int i = 0; i < lasers.size(); i++){
				if(
					lasers.get(i).getSpeed() == getSpeed() &&
					lasers.get(i).getPosition().distanceTo(lasers.get(i).getOrigin()) > 96 &&
					getAction2()){
					if(isCharged()){
						charge -= 50;
					}
					playLaserSound();
					for(int j = 0; j < 16; j++){
						lasers.add(
							new Laser(
								j * (Math.PI / 8) + getSpray(),
								lasers.get(i).getPosition(),
								getSpeed() - 1 - random.nextInt(5)));
					}
					lasers.remove(i);
				}else if(lasers.get(i).getSpeed() < getSpeed()){
					if(!isCharged() && lasers.get(i).getPosition().distanceTo(lasers.get(i).getOrigin()) > 96){
						lasers.remove(i);
					}else{
						lasers.get(i).setDirection(lasers.get(i).getDirection() + getSpray());
					}
				}
			}
		}
	}
}