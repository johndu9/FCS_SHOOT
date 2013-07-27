package firstGame;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import firstEngine.Color;
import firstEngine.Point;
import firstEngine.polygon.FirstPolygon;

public class GameCursor extends FirstPolygon{
	
	int cursorWidth, cursorHeight;
	private static boolean showCursor;
	
	private static Color cursorColor;

	public GameCursor(int width, int height){
		
		super(FirstPolygon.sizeToPoints(20), 0, new Point(0, 0), cursorColor);
		setCursorWidth(width);
		setCursorHeight(height);
		removeOSCursor();
		
	}
	
	public static void init(boolean showCursor, Color cursor){
		
		GameCursor.showCursor = showCursor;
		cursorColor = cursor;
		
	}
	
	public void update(int delta){
		
		setPosition(Mouse.getX(), Display.getHeight() - Mouse.getY());
		super.update(delta);
		
	}
	
	public int getCursorWidth(){
		
		return cursorWidth;
		
	}
	
	public void setCursorWidth(int cursorWidth){
		
		this.cursorWidth = cursorWidth;
		
	}
	
	public int getCursorHeight(){
		
		return cursorHeight;
		
	}
	
	public void setCursorHeight(int cursorHeight){
		
		this.cursorHeight = cursorHeight;
		
	}
	
	public static boolean showCursor(){
		
		return showCursor;
		
	}
	
	public static void toggleShowCursor(){
		
		showCursor = !showCursor;
		removeOSCursor();
		
	}
	
	private static void removeOSCursor(){

		try{
			if(showCursor){
				Mouse.setNativeCursor(new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null));
			}else{
				Mouse.setNativeCursor(null);
			}
		}catch(LWJGLException e){
			e.printStackTrace();
		}
		
	}
	
	public int size(){
		
		return 1;
		
	}
	
	public void render(){
		
		if(showCursor){
			
			GL11.glPushMatrix();
			
				GL11.glColor4f(
					getColor().getRed(),
					getColor().getGreen(),
					getColor().getBlue(),
					getColor().getAlpha());
				
				GL11.glLineWidth(10);
				
				GL11.glBegin(GL11.GL_LINES);
				
					int mx = Mouse.getX();
					int my = Display.getHeight() - Mouse.getY();
					
					GL11.glVertex2f(mx - cursorWidth / 2, my);
					GL11.glVertex2f(mx + cursorWidth / 2, my);
					GL11.glVertex2f(mx, my - cursorHeight / 2);
					GL11.glVertex2f(mx, my + cursorHeight / 2);
					
				GL11.glEnd();
				
			GL11.glPopMatrix();
			
		}
		
	}
	
}