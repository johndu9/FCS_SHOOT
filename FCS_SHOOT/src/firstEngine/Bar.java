package firstEngine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import firstEngine.polygon.FirstPolygon;

public class Bar extends FirstPolygon{	//class Bar

	private float value;
	private FirstPolygon background;
	
	public Bar(int x, int y, double direction, Point position, float curValue){	//constructor
		
		super(sizeToPoints(x, y), direction, new Point(position.x, position.y), Text.getTextColor());
		background =
			new FirstPolygon(
				sizeToPoints(x, y),
				direction,
				getPosition(),
				Text.getShadowColor());
		setValue(curValue);
		
	}	//close constructor
	
	public void update(int delta){

		background.update(delta);
		if(!background.getPosition().equals(getPosition())){
			background.setPosition(getPosition());
		}
		if(background.getDirection() != getDirection()){
			background.setDirection(getDirection());
		}
		super.update(delta);
		
	}
	
	public float getValue(){
		
		return value;
		
	}
	
	public void setValue(float value){
		
		this.value = value;
		
	}
	
	public void setValue(){
		
		if(isInPolygon(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)){
			setValue((Mouse.getX() - getPosition().x - getPoints()[0].x) / (getWidth()));
		}
		
	}
	
	public int size(){
		
		return 2;
		
	}
	
	public void render(){	//method render
		
		GL11.glPushMatrix();
		
			GL11.glColor4f(
				getColor().getRed(),
				getColor().getGreen(),
				getColor().getBlue(),
				getColor().getAlpha());

			GL11.glTranslatef(renderPosition.x + getPosition().x, renderPosition.y + getPosition().y, 0);
			GL11.glRotatef((float)Math.toDegrees(getDirection()), 0f, 0f, 1.0f);
			GL11.glTranslatef(-(renderPosition.x + getPosition().x), -(renderPosition.y + getPosition().y), 0);
			
			GL11.glBegin(GL11.GL_POLYGON);

				for(int i = 0; i < 2; i++){	//for
					GL11.glVertex2f(
						renderPosition.x + getPosition().x + getPoints()[i].x,
						renderPosition.y + getPosition().y + getPoints()[i].y);
				}	//close for
				for(int i = 2; i < 4; i++){	//for
					GL11.glVertex2f(
						renderPosition.x + getPosition().x + getPoints()[i - 2].x + (getPoints()[i].x - getPoints()[i - 2].x) * value,
						renderPosition.y + getPosition().y + getPoints()[i].y);
				}	//close for
				
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}	//close method render
	
}	//close class Bar