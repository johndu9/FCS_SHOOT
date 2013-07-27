package firstEngine;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import firstEngine.polygon.FirstPolygon;

public class Button{	//class Button
	
	private FirstPolygon hitbox;
	private Toggle selected;
	private static Sound select;
	private String string;
	private int letSize;
	private int column;
	private int row;
	private boolean selectable;
	public static Toggle showButtons = Text.showText;
	
	public Button(String string, int letSize, int column, int row, boolean selectable){	//constructor
		
		setString(string);
		setLetSize(letSize);
		setColumn(column);
		setRow(row);
		setSelectable(selectable);
		selected = new Toggle(false);
		
	}	//close constructor
	
	public static void init(){	//method init
		
		select = new Sound("select");
		
	}	//close method init
	
	public ArrayList<FirstPolygon> placeButton(){	//method placeButton
		
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
		setHitbox();
		if(isSelectable()) clickHitbox();
		if(showButtons.getState()){
			if(selected.getState() || (isSelectable() && inHitbox(Mouse.getX(), Mouse.getY()))){
				polygons.add(hitbox);
			}
			polygons.add(hitbox);
		}
		polygons.addAll(Text.placeText(string, letSize, column, row));
		return polygons;
		
	}	//close method placeButton
	
	public boolean inHitbox(int x, int y){	//method inHitbox
			
		return hitbox.isInPolygon(x, y);
		
	}	//close method inHitbox
	
	public void clickHitbox(){	//method clickHitbox
		
		if(inHitbox(Mouse.getX(), Mouse.getY())) selected.update(Mouse.isButtonDown(0));
		
	}	//close method clickHitbox
	
	public boolean confirmSelect(){	//method confirmSelect
		
		if(selected.getState()){	//if
			
			selected.toggle();
			select.play();
			return true;
			
		}	//close if
		
		else return false;
		
	}	//close method confirmSelect
	
	public void select(){	//method select
		
		selected.setState(true);
		
	}	//close method select
	
	public boolean isSelected(){	//method isSelected
		
		return selected.getState();
		
	}	//close method isSelected
	
	public String getString(){	//method getString
		
		return string;
		
	}	//close method getString
	
	public void setString(String string){	//method setString
		
		this.string = string;
		
	}	//close method setString
	
	public int getLetSize(){	//method getLetSize
		
		return letSize;
		
	}	//close method 
	
	public void setLetSize(int letSize){	//method setLetSize
		
		this.letSize = letSize;
		
	}	//close method setLetSize
	
	public int getColumn(){	//method getColumn
		
		return column;
		
	}	//close method getColumn
	
	public void setColumn(int column){	//method setColumn
		
		this.column = column;
		
	}	//close method setColumn
	
	public int getRow(){	//method getRow
		
		return row;
		
	}	//close method getRow
	
	public void setRow(int row){	//method setRow
		
		this.row = row;
		
	}	//close method setRow
	
	public boolean isSelectable(){	//method isSelectable
		
		return selectable;
		
	}	//close method isSelectable
	
	public void setSelectable(boolean selectable){	//method setSelectable
		
		this.selectable = selectable;
		
	}	//close method setSelectable
	
	private void setHitbox(){	//method setHitbox
		
		hitbox =
			new FirstPolygon(
				FirstPolygon.sizeToPoints(
					string.length() * Text.columnConstant * letSize,
					Text.rowConstant * letSize / 2),
				0,
				new Point(
					Text.lMargin + string.length() * Text.columnConstant * letSize / 2 -
						Text.columnConstant * letSize / 2 + Text.columnConstant * column * letSize +
						5 * letSize,
				Text.rowConstant * (row + 1) + 4 * letSize),
				Text.getShadowColor()
				);
		
	}	//close method setHitbox
	
}	//close class Button