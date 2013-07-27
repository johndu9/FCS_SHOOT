package firstEngine;

import java.util.ArrayList;

import firstEngine.polygon.FirstPolygon;

public class TextScreen{
	
	private PreloadText[] textPages;
	private int pages;
	private String name;
	
	private static Menu backnext =
		new Menu(new String[]{"Back", "Next"}, 2, 33, 16, false, 2, true);
	private static Menu pageMenu =
		new Menu(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", ""}, 2, 6, 16, false, 2, false);
	
	public TextScreen(String name, int pages){
		
		textPages = new PreloadText[pages];

		setName(name);
		setPages(pages);
		refresh();
		
	}
	
	public ArrayList<FirstPolygon> placeScreen(){
		
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();

		polygons.addAll(Text.placeText("Page: ", 2, 0, 16));
		polygons.addAll(pageMenu.placeMenu(0, pages));
		polygons.addAll(backnext.placeMenu());
		polygons.addAll(textPages[pageMenu.isSelected()].getPolygons());
		
		return polygons;
		
	}
	
	public boolean done(){
		
		if(backnext.isSelected("Back") || backnext.isSelected("Next")){
			
			if(
				(backnext.isSelected("Back") && pageMenu.isSelected() == 0) ||
				(backnext.isSelected("Next") && pageMenu.isSelected() == pages - 1)){

				backnext.confirmSelect();
				pageMenu.confirmSelect();
				return true;
				
			}else{

				int selected = pageMenu.confirmSelect();
				if(backnext.isSelected("Back")){
					pageMenu.select(selected - 1);
				}
				else if(backnext.isSelected("Next")){
					pageMenu.select(selected + 1);
				}
				backnext.confirmSelect();
				
			}

			
		}
		
		return false;
		
	}
	
	public int getPages(){
		
		return pages;
		
	}
	
	public void setPages(int pages){
		
		this.pages = pages;
		
	}
	
	public String getName(){
		
		return name;
		
	}
	
	public void setName(String name){
		
		this.name = name;
		
	}
	
	public void select(int index){
		
		pageMenu.select(index);
		
	}
	
	public void refresh(){
		
		for(int i = 0; i < textPages.length; i++){
			textPages[i] = new PreloadText(name + i, 2, 0, 1);
		}
		select(0);
		
	}
	
}