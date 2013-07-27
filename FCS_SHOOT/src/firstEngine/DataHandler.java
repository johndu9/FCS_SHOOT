package firstEngine;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class DataHandler{	//class FirstReaderWriter

	private static Scanner fileReader;
	private static StringTokenizer tokens;
	
	public static String[][] readData(String path, String delimiter){	//method read

		if(!new File(path).exists()) return null;
		
		String[][] returned;
		int size1 = 0;
		int size2;
		String cache = "";
		
		resetFileReader(path);
		
		while(fileReader.hasNext()){	//while
			cache = fileReader.nextLine();
			size1++;
		}	//close while
		
		setTokens(cache, delimiter);
		size2 = tokens.countTokens();
		returned = new String[size1][size2];
		
		resetFileReader(path);

		for(int i = 0; fileReader.hasNext(); i++){	//for
			setTokens(fileReader.nextLine(), delimiter);
			for(int j = 0; tokens.hasMoreTokens(); j++){	//for
				returned[i][j] = tokens.nextToken();
			}	//close for
		}	//close for
		
		return returned;
		
	}	//close method read
	
	public static int[][][] refineData(String[][] data){	//method refineData
			
		int[][] rawData = new int[data.length][data[0].length];
		
		for(int i = 0; i < data.length; i++){	//for
			
			for(int j = 0; j < data[i].length; j++){	//for
				
				rawData[i][j] = Integer.parseInt(data[i][j]);
				
			}	//close for
			
		}	//close for
		
		int mode[];
		int count = 1;
		
		for(int i = 0; i < rawData.length - 1; i++){	//for
			
			if(rawData[i][0] != rawData[i + 1][0]) count++;
			
		}	//close for
		
		mode = new int[count];
		
		for(int i = 0; i < mode.length; i++){	//for
			
			count = 0;
			
			for(int j = 0; j < rawData.length; j++){	//for
				
				if(rawData[j][0] == i) count++;
				
			}	//close for
			
			mode[i] = count;
			
		}	//close for
		
		int max = 0;		
		
		for(int i = 0; i < mode.length; i++){	//for		
			
			if(mode[i] > max) max = mode[i];
			
		}	//close for
		
		int[][][] newData = new int[rawData[rawData.length - 1][0] + 1][max][rawData[0].length - 1];
		
		for(int i = 0; i < newData.length; i++){	//for
			
			for(int j = 0; j < newData[i].length; j++){	//for
				
				for(int k = 0; k < newData[i][j].length; k++){	//for
					
					newData[i][j][k] = rawData[j + i * max][k + 1];
					
				}	//close for
				
			}	//close for
			
		}	//close for
		
		return newData;
		
	}	//close method refineData
	
	public static int[][][] resizeData(int[][][] data, int size){	//method resizeData
		
		int[][][] newData = data;
		
		for(int i = 0; i < newData.length; i++){	//for
			
			for(int j = 0; j < newData[i].length; j++){	//for
				
				for(int k = 0; k < newData[i][j].length; k++){	//for
					
					newData[i][j][k] = newData[i][j][k] * size;
					
				}	//close for
				
			}	//close for
			
		}	//close for
		
		return newData;
		
	}	//close method resizeData
	
	public static Point[] resizePoints(Point[] points, int size){	//method resizePoints
		
		Point[] newPoints = points;
		
		for(int i = 0; i < newPoints.length; i++){	//for
			
			newPoints[i].x = points[i].x * size;
			newPoints[i].y = points[i].y * size;
			
		}	//close for
		
		return newPoints;
		
	}	//close method resizePoints
	
	public static Point[][] resizePoints(Point[][] points, int size){	//method resizePoints
		
		Point[][] newPoints = points;
		
		for(int i = 0; i < newPoints.length; i++){	//for
			
			newPoints[i] = resizePoints(newPoints[i], size);
			
		}	//close for
		
		return newPoints;
		
	}	//close method resizePoints

	public static Point[][] dataToPoints(int[][][] data){	//method dataToPoints
		
		Point[][] points = new Point[data.length][data[0].length];
		
		for(int i = 0; i < data.length; i++){	//for
			
			for(int j = 0; j < data[i].length; j++){	//for
			
				points[i][j] = new Point(data[i][j][0], data[i][j][1]);
					
			}	//close for
		
		}	//close for
			
		return points;
		
	}	//close method dataToPoints
	
	public static Point[][] dataToPoints(String path){	//method dataToPoints
		
		return dataToPoints(refineData(readData(path, ",")));
		
	}	//close method dataToPoints
	
	private static void resetFileReader(String path){	//method resetFileReader
		
		try{	//try
			
			fileReader = new Scanner(new File(path));
		
		}catch(FileNotFoundException e){	//catch
			
			fileReader = null;
			e.printStackTrace();
			
		}	//close catch
		
	}	//close method resetFileReader
	
	private static void setTokens(String string, String delimiter){	//method resetTokens

		tokens = new StringTokenizer(string, delimiter);

	}	//close method resetTokens
	
}	//close class FirstReaderWriter