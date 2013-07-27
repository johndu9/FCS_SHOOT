package firstEngine;

public class Toggle{	//class Toggle
	
	private boolean state;
	private boolean wasPressed;
	
	public Toggle(boolean state){	//constructor
		
		this.state = state;
		wasPressed = false;
		
	}	//close constructor
	
	public void update(boolean pressed){	//method update
		
		if(pressed != wasPressed){	//if
			
			if(pressed) state = !state;
			wasPressed = pressed;
			
		}	//close if
		
	}	//close method update
	
	public void toggle(){	//method toggle
		
		state = !state;
		
	}	//close method toggle
	
	public boolean getState(){	//method getState
		
		return state;
		
	}	//close method getState
	
	public void setState(boolean state){	//method setState
		
		this.state = state;
		
	}	//close method setState
	
}	//close class Toggle