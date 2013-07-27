package firstEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import firstEngine.polygon.FirstPolygon;

public class PreloadText{	//class PreloadText

	private ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
	
	public PreloadText(String name, int size, int column, int row){	//constructor
		
		try{	//try
			
			Scanner reader = new Scanner(new File(Data.DIR_PATH + "res" + Data.SEP + "txt" + Data.SEP + name + ".txt"));
			int length = 0;
			
			while(reader.hasNext()){	//while
				
				polygons.addAll(Text.placeText(reader.nextLine(), size, column, row + length));
				length++;
				
			}	//close while
			
		}catch(Exception e){	//catch
			
			e.printStackTrace();
			
		}	//close catch
		
	}	//close constructor
	
	public ArrayList<FirstPolygon> getPolygons(){	//method getPolygons
		
		return polygons;
		
	}	//close method getPolygons
	
}	//close class PreloadText