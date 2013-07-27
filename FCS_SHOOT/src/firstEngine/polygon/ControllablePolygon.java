package firstEngine.polygon;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import firstEngine.Color;
import firstEngine.Point;

public class ControllablePolygon extends MovablePolygon{	//class ControllablePolygon
	
	protected int[] controls = new int[6];
	private int controlType;
	private boolean action1;
	private boolean action2;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int ACTION1 = 4;
	public static final int ACTION2 = 5;
	
	public static final int[] CONTROL_TYPES = new int[]{1, 2, 3};
	public static final int STANDARD = CONTROL_TYPES[0];
	public static final int RELATIVE = CONTROL_TYPES[1];
	public static final int TANK = CONTROL_TYPES[2];
	
	public ControllablePolygon(
		Point[] points,
		double direction,
		Point position,
		Color color,
		int speedFactor,
		int rotationFactor,
		int[] controls){	//constructor
		
		super(
			points,
			direction,
			position,
			color,
			speedFactor,
			rotationFactor);
		setControls(controls);
		controlType = STANDARD;
		
	}	//close constructor
	
	public static int[] controlsToValues(String[] controls){
		
		int[] values = new int[]{0, 0, 0, 0, 0, 0};
		
		for(int i = 0; i < values.length; i++){
			if(controls[i].length() > "BUTTON".length() && controls[i].substring(0, "BUTTON".length()).equals("BUTTON")){
				values[i] = -Mouse.getButtonIndex(controls[i]);
			}else{
				values[i] = Keyboard.getKeyIndex(controls[i]);
			}
		}
		
		return values;
		
	}
	
	public static int controlTypeToInt(String controlType){
		
		if(controlType.equals("STANDARD")){
			return STANDARD;
		}else if(controlType.equals("RELATIVE")){
			return RELATIVE;
		}else if(controlType.equals("TANK")){
			return TANK;
		}else{
			return 0;
		}
		
	}
	
	public static String controlTypeToString(int controlType){
		
		if(controlType == STANDARD){
			return "STANDARD";
		}else if(controlType == RELATIVE){
			return "RELATIVE";
		}else if(controlType == TANK){
			return "TANK";
		}else{
			return "";
		}
		
	}
	
	public void update(int delta){	//method update
		
		super.update(delta);
		
		if(!frozen){	//if

			boolean up, left, down, right;
			if(controls[UP] <= 0){
				up = Mouse.isButtonDown(-controls[UP]);
			}else{
				up = Keyboard.isKeyDown(controls[UP]);
			}
			if(controls[LEFT] <= 0){
				left = Mouse.isButtonDown(-controls[LEFT]);
			}else{
				left = Keyboard.isKeyDown(controls[LEFT]);
			}
			if(controls[DOWN] <= 0){
				down = Mouse.isButtonDown(-controls[DOWN]);
			}else{
				down = Keyboard.isKeyDown(controls[DOWN]);
			}
			if(controls[RIGHT] <= 0){
				right = Mouse.isButtonDown(-controls[RIGHT]);
			}else{
				right = Keyboard.isKeyDown(controls[RIGHT]);
			}
			if(controls[ACTION1] <= 0){
				action1 = Mouse.isButtonDown(-controls[ACTION1]);
			}else{
				action1 = Keyboard.isKeyDown(controls[ACTION1]);
			}
			if(controls[ACTION2] <= 0){
				action2 = Mouse.isButtonDown(-controls[ACTION2]);
			}else{
				action2 = Keyboard.isKeyDown(controls[ACTION2]);
			}
			
			if(controlType == STANDARD){
				setDirection(getPosition().directionTo(Mouse.getX(), Display.getHeight() - Mouse.getY()));
				
				if(up && right) move(-Math.PI / 4, delta);
				else if(up && left) move(-Math.PI * 3 / 4, delta);
				else if(down && left) move(Math.PI * 3 / 4, delta);
				else if(down && right) move(Math.PI / 4, delta);
				else if(up) move(-Math.PI / 2, delta);
				else if(left) move(Math.PI, delta);
				else if(down) move(Math.PI / 2, delta);
				else if(right) move(0, delta);
			}else if(controlType == RELATIVE){
				setDirection(getPosition().directionTo(Mouse.getX(), Display.getHeight() - Mouse.getY()));
				if(up) move(getDirection(), delta);
				else if(left) move(getDirection() - Math.PI / 2, delta);
				else if(down) move(getDirection() + Math.PI, delta);
				else if(right) move(getDirection() + Math.PI / 2, delta);
			}else if(controlType == TANK){
				if(up) move(getDirection(), delta);
				if(left) turn(false, delta);
				if(down) move(getDirection() + Math.PI, delta);
				if(right) turn(true, delta);
			}
			
		}	//close if
		
	}	//close method update
	
	public int getControlType(){	//method getControlType
		
		return controlType;
		
	}	//close method getControlType
	
	public void setControlType(int controlType){	//method setControlType
		
		this.controlType = controlType;
		
	}	//close method setControlType

	public int[] getControls(){	//method getControls
		
		return controls;
		
	}	//close method getControls
	
	public void setControls(int[] controls){	//method setControls
		
		if(controls.length != 6){
			this.controls = new int[]{0, 0, 0, 0, 0, 0};
			return;
		}
		for(int i = 0; i < 6; i++){
			this.controls[i] = controls[i];
		}
		
	}	//close method setControls
	
	public void setControl(int index, int value){	//method setControl
		
		controls[index] = value;
		
	}	//close method setControl
	
	public boolean getAction1(){
		
		return action1;
		
	}
	
	public boolean getAction2(){
		
		return action2;
		
	}
	
}	//close class ControllablePolygon