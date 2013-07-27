package firstEngine;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import firstEngine.polygon.FirstPolygon;


public class Text{	//class Text
	
	public static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+`-={}|[]\\:\";'<>?,./ ";
	public static final int lMargin = 32;
	public static final int tMargin = 32;
	public static final int columnConstant = 8;
	public static final int rowConstant = 32;
	private static FirstPolygon[][][] text = new FirstPolygon[6][characters.length()][];
	private static Color textColor = new Color(0, 0, 0, 0);
	public static Toggle showText = new Toggle(true);
	
	public static void init(Color color){	//method init
		
		setTextColor(color);
		
		for(int letSize = 0; letSize < text.length; letSize++){	//for
			
			for(int i = 0; i < characters.length(); i++){	//for
				
				ArrayList<FirstPolygon> textPolygons = getTextFromFile(
			    	characters.substring(i, i + 1),
			    	letSize + 1,
			    	new Point(0, 0),
			    	textColor
			    	);
				
				text[letSize][i] = new FirstPolygon[textPolygons.size()];
				
				for(int j = 0; j < text[letSize][i].length; j++){	//for
					
					text[letSize][i][j] = textPolygons.get(j);
									
				}	//close for
				
			}	//close for
			
		}	//close for
		
	}	//close method init
	
	public static int getBottomRow(int letSize){	//method getBottomRow
		
		return (Display.getHeight() - rowConstant / letSize) / rowConstant - 1;
		
	}	//close method getBottomRow
	
	public static int getRightColumn(int length, int letSize){	//method getRightColumn
		
		return (Display.getWidth() - letSize * length * columnConstant) / (columnConstant * letSize) - letSize - 1;
		
	}	//close method getRightColumn
	
	public static Point columnRowToPoint(int letSize, int column, int row){	//method columnRowToPoint
		
		return
			new Point(
//				lMargin + column * columnConstant * letSize,
//				tMargin + row * rowConstant);
				lMargin + column * columnConstant * letSize + letSize + columnConstant,
				tMargin + row * rowConstant + 4 * letSize);
		
	}	//close method columnRowToPoint
	
	public static ArrayList<FirstPolygon> placeText(String string, int letSize, Point position){	//method placeText
		
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
		
		if(showText.getState()){	//if

			polygons.addAll(Text.getText(
				string,
				letSize,
				new Point(position.x + letSize, position.y + letSize),
//				new Point(position.x + 2 * letSize + columnConstant, position.y + 5 * letSize),
				getShadowColor()
				));
			
			polygons.addAll(Text.getText(
				string,
				letSize,
				new Point(position.x, position.y),
//				new Point(position.x + letSize + columnConstant, position.y + 4 * letSize),
				textColor
				));
			
		}	//close if
		
		return polygons;
		
	}	//close method placeText
	
	public static ArrayList<FirstPolygon> placeText(String string, int letSize, int column, int row){	//method placeText
		
		return placeText(string, letSize, columnRowToPoint(letSize, column, row));

	}	//close method placeText
	
	public static ArrayList<FirstPolygon> getText(
		String string,
		int letSize,
		Point position,
		Color color){	//method getText
	
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
		
		for(int i = 0; i < string.length(); i++){	//for
			
			if(!string.substring(i, i + 1).equals(" ")){	//if
				
				int index = characters.indexOf(string.substring(i, i + 1).toUpperCase());
				
				for(int j = 0; j < text[letSize - 1][index].length; j++){	//for
					
					polygons.add(new FirstPolygon(
						text[letSize - 1][index][j].getPoints(),
						0,
						new Point(position.x + 8 * i * letSize, position.y),
						color
						));
					
				}	//close for
				
			}	//close if
			
		}	//close for
		
		return polygons;
		
	}	//close method getText
	
	public static ArrayList<FirstPolygon> getTextFromFile(
		String string,
		int letSize,
		Point position,
		Color color){	//method getTextFromFile
		
		Point[][] points;
		String[] fileNames = checkSymbol(string);
		ArrayList<FirstPolygon> polygons = new ArrayList<FirstPolygon>();
		
		for(int i = 0; i < string.length(); i++){	//for
			
			points = DataHandler.resizePoints(
				DataHandler.dataToPoints(
					Data.DIR_PATH + "res" + Data.SEP + "fnt" + Data.SEP + fileNames[i] + ".txt"),
				letSize);
			
			for(int j = 0; j < points.length; j++){	//for
				
				polygons.add(
					new FirstPolygon(
						points[j],
						0,
						new Point(position.x + 8 * i * letSize, position.y),
						color			
						));
				
			}	//close for
			
		}	//close for
		
		return polygons;
		
	}	//close method getTextFromFile
	
	public static Color getShadowColor(){	//method getShadowColor
		
		return new Color(textColor.getRed() / 2, textColor.getGreen() / 2, textColor.getBlue() / 2, textColor.getAlpha() / 2);
		
	}	//close method getShadowColor

	private static String[] checkSymbol(String string){	//method checkSymbol

		String[] fileNames = new String[string.length()];
		
		for(int i = 0; i < string.length(); i++){	//for
			
			fileNames[i] = string.substring(i, i + 1).toUpperCase();
			
			if(fileNames[i].equals("_")) fileNames[i] = "_underscore";
			else if(fileNames[i].equals(".")) fileNames[i] = "_period";
			else if(fileNames[i].equals(":")) fileNames[i] = "_colon";
			else if(fileNames[i].equals("-")) fileNames[i] = "_dash";
			else if(fileNames[i].equals("!")) fileNames[i] = "_exclamation";
			else if(fileNames[i].equals("'")) fileNames[i] = "_apostrophe";
			else if(fileNames[i].equals("`")) fileNames[i] = "_grave";
			else if(fileNames[i].equals("\"")) fileNames[i] = "_quote";
			else if(fileNames[i].equals(",")) fileNames[i] = "_comma";
			else if(fileNames[i].equals(";")) fileNames[i] = "_semicolon";
			else if(fileNames[i].equals("+")) fileNames[i] = "_plus";
			else if(fileNames[i].equals("*")) fileNames[i] = "_asterisk";
			else if(fileNames[i].equals("?")) fileNames[i] = "_question";
			else if(fileNames[i].equals("/")) fileNames[i] = "_fslash";
			else if(fileNames[i].equals("\\")) fileNames[i] = "_bslash";
			else if(fileNames[i].equals("=")) fileNames[i] = "_equals";
			else if(fileNames[i].equals("(")) fileNames[i] = "_lparen";
			else if(fileNames[i].equals(")")) fileNames[i] = "_rparen";
			else if(fileNames[i].equals("~")) fileNames[i] = "_tilde";
			else if(fileNames[i].equals("@")) fileNames[i] = "_at";
			else if(fileNames[i].equals("#")) fileNames[i] = "_pound";
			else if(fileNames[i].equals("$")) fileNames[i] = "_dollar";
			else if(fileNames[i].equals("<")) fileNames[i] = "_lthan";
			else if(fileNames[i].equals(">")) fileNames[i] = "_mthan";
			else if(fileNames[i].equals("^")) fileNames[i] = "_caret";
			else if(fileNames[i].equals("&")) fileNames[i] = "_ampersand";
			else if(fileNames[i].equals("[")) fileNames[i] = "_lbracket";
			else if(fileNames[i].equals("]")) fileNames[i] = "_rbracket";
			else if(fileNames[i].equals("{")) fileNames[i] = "_lbrace";
			else if(fileNames[i].equals("}")) fileNames[i] = "_rbrace";
			else if(fileNames[i].equals("|")) fileNames[i] = "_bar";
			else if(fileNames[i].equals("%")) fileNames[i] = "_percent";
			else if(fileNames[i].equals(" ") ||
				!new File(
					Data.DIR_PATH +
					"res" + Data.SEP +
					"fnt" + Data.SEP +
					fileNames[i] + ".txt").exists())
				fileNames[i] = "_space";
			
		}	//close for
		
		return fileNames;
		
	}	//close method checkSymbol
	
	public static void setTextColor(Color color){	//method getTextColor 
		
		textColor = color;
		
	}	//close method getTextColor
	
	public static Color getTextColor(){	//method getTextColor 
		
		return textColor;
		
	}	//close method getTextColor
	
}	//close class Text