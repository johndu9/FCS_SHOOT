package firstGame.players;

import firstEngine.Point;
import firstEngine.Toggle;
import firstGame.Laser;

public class Cross extends Player{

	private boolean horizontal = false;
	private Toggle diagonal;
	public Cross(Point position, int[] controls){
		super(position, controls);
		diagonal = new Toggle(getAction2());
	}
	
	public void update(int delta){
		
		super.update(delta);
		diagonal.update(getAction2());
		
		if(!frozen){
			
			if(getAction1() && shootDelay.getDelay(150)){
				shoot();
				if(!isCharged()){
					charge += 15;
				}
			}
			
			if(isCharged() && getAction2() && chargedDelay.getDelay(10)){
				shoot();
				charge -= 5;
			}
			
		}
		
	}
	
	private void shoot(){
		playLaserSound();
		double direction;
		for(int j = 0; j < 2; j++){
			direction = (j * Math.PI);
			if(diagonal.getState()){
				direction += Math.PI / 4;
			}
			if(horizontal){
				direction += Math.PI / 2;
			}
			lasers.add(new Laser(direction, getPosition(), getSpeed()));
		}
		horizontal = !horizontal;
	}
	
}