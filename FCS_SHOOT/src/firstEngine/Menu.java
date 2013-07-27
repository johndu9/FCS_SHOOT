package firstEngine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import firstEngine.polygon.FirstPolygon;

public class Menu{
	
	private Button[] buttons;
	private int letSize;
	private int column;
	private int row;
	private int space;
	private boolean vertical;
	
	public Menu(String[] buttonNames, int letSize, int column, int row, boolean vertical, int space, boolean selectable){
		
		setVertical(vertical);
		setSpace(space);
		buttons = new Button[buttonNames.length];
		
		int columnAdd = 0;
		int rowAdd = 0;
		
		for(int i = 0; i < buttonNames.length; i++){
			
			if(vertical) rowAdd = (1 + space) * i;
			else if(i > 0) columnAdd += buttonNames[i - 1].length() + space;
			
			buttons[i] = new Button(buttonNames[i], letSize, column + columnAdd, row + rowAdd, selectable);
			
		}
		
	}
	
	public ArrayList<FirstPolygon> placeMenu(int beginIndex, int endIndex){
		
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
		
		for(int i = beginIndex; i < endIndex; i++){
			
			polygons.addAll(buttons[i].placeButton());
			
		}
		
		return polygons;
		
	}
	
	public ArrayList<FirstPolygon> placeMenu(){
		
		return placeMenu(0, buttons.length);
		
	}
	
	public boolean isSelected(String name){
		
		for(int i = 0; i < buttons.length; i++){
			
			if(buttons[i].getString().equals(name)){
				
				return buttons[i].isSelected();
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean isSelected(int index){
		
		if(index < 0 || buttons.length - 1 < index) return false;
		return buttons[index].isSelected();
		
	}
	
	public int isSelected(){
		
		for(int i = 0; i < buttons.length; i++){
			
			if(buttons[i].isSelected()) return i;
			
		}
		
		return -1;
		
	}
	
	public boolean confirmSelect(String name){
		
		for(int i = 0; i < buttons.length; i++){
			
			if(buttons[i].getString().equals(name)){
				
				return buttons[i].confirmSelect();
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean confirmSelect(String name, int key){
		
		if(Keyboard.isKeyDown(key)){
			for(int i = 0; i < buttons.length; i++){
				if(buttons[i].getString().equals(name)) buttons[i].select();
			}
		}
		return confirmSelect(name);
		
	}
	
	public boolean confirmSelect(int index){
		
		if(index < 0 || buttons.length - 1 < index) return false;
		return buttons[index].confirmSelect();
		
	}
	
	public int confirmSelect(){
		
		for(int i = 0; i < buttons.length; i++){
			
			if(buttons[i].confirmSelect()) return i;
			
		}
		
		return -1;
		
	}
	
	public boolean anySelected(int beginIndex, int endIndex){
		
		if(beginIndex < 0 || endIndex > buttons.length) return false;
		
		for(int i = beginIndex; i < endIndex; i++){
			
			if(buttons[i].isSelected()) return true;
			
		}
		
		return false;
		
	}
	
	public boolean anySelected(){
		
		return anySelected(0, buttons.length);
		
	}
	
	public void select(int index){
		
		if(index >= 0 && index < buttons.length - 1){
			
			buttons[index].select();
			
		}
		
	}
	
	public void select(String name){
		
		for(int i = 0; i < buttons.length; i++){
			
			if(buttons[i].getString().equals(name)) buttons[i].select();
			
		}
		
	}
	
	public String getButtonName(int index){
		
		if(index >= 0 && index < buttons.length){
			return getButtonNames()[index];
		}else return "";
		
	}
	
	public String[] getButtonNames(){
		
		String[] names = new String[buttons.length];
		
		for(int i = 0; i < names.length; i++){
			names[i] = buttons[i].getString();
		}
		
		return names;
		
	}
	
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
	
	public boolean isVertical(){
		
		return vertical;
		
	}
	
	public void setVertical(boolean vertical){
		
		this.vertical = vertical;
		
	}
	
	public int getSpace(){	//method getSpace
		
		return space;
		
	}	//close method getSpace
	
	public void setSpace(int space){	//method setSpace
		
		this.space = space;
		
	}	//close method setSpace
	
	public int size(){
		
		return buttons.length;
		
	}
	
}