package firstEngine;

import org.lwjgl.Sys;

public class Timer{	//class Timer
	
	private static long time;
	private static long lastTime;
	private long lastDelay;
	public static double gameSpeed;
	
	public Timer(){	//constructor

		setLastDelay();
		
	}	//close constructor
	
	public static void init(double gameSpeed){	//method init
		
		setGameSpeed(gameSpeed);
		
	}	//close method init
	
	public long getLastDelay(){	//method getLastDelay
		
		return lastDelay;
		
	}	//close method getLastDelay
	
	public void setLastDelay(long time){	//method setLastDelay
		
		lastDelay = time;
		
	}	//close method setLastDelay
	
	public void setLastDelay(){	//method setLastDelay
		
		lastDelay = getTime();
		
	}	//close method setLastDelay
	
	public static long getTime(){	//method getTime
		
		setTime();
		return time;
		
	}	//close method getTime
	
	private static void setTime(){	//method setTime
		
		time = (Sys.getTime() * 1000) / Sys.getTimerResolution();

	}	//close method setTime
	
	public static int getDelta(){	//method getDelta
		
		long time = getTime();
		int delta = (int)(time - lastTime);
		lastTime = time;
		
		return (int)(delta * gameSpeed);
		
	}	//close method getDelta
	
	public boolean getDelay(long milliseconds){	//method getDelay
		
		if(getTime() - getLastDelay() > milliseconds / gameSpeed){	//if
			
			setLastDelay(getTime());
			return true;
		
		}	//close if
		
		else return false;

	}	//close method getDelay
	
	public static double getGameSpeed(){	//method getSpeed
		
		return gameSpeed;
		
	}	//close method getSpeed
	
	public static void setGameSpeed(double gameSpeed){	//method setSpeed
		
		Timer.gameSpeed = gameSpeed;
		
	}	//close method setSpeed
	
}	//close class Timer