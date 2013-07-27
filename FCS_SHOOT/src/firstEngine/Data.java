package firstEngine;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Data{	//class Data

	private static Scanner fileReader;
	private static StringTokenizer tokens;
	private String path;
	private String[][] data;
	public static final int NAME = 0;
	public static final int VALUE = 1;
	public static final String SEP = "/";//System.getProperty("file.separator");
	public static final String DIR_PATH = System.getProperty("user.dir") + SEP;
	
	public Data(String path){	//constructor
		
		setPath(path);
		readDataFromFile();
		
	}	//close constructor
	
	public int countName(String name){	//method countName
	
		int count = 0;
		
		for(int i = 0; i < data.length; i++){	//for
			
			if(
				data[i][NAME].length() > name.length() &&
				data[i][NAME].length() <= name.length() + 2 &&
				data[i][NAME].substring(0, name.length()).equals(name)){	//if
				count++;
			}	//close if
			
		}	//close for
		
		return count;
		
	}	//close method countName
	
	public String getValue(String name){	//method getValue
		
		for(int i = 0; i < data.length; i++){	//for
			if(data[i][NAME].equals(name)){	//if
				return data[i][VALUE];
			}	//close if
		}	//close for
		return "";
		
	}	//close method getValue
	
	public void setValue(String name, String newValue){	//method setValue
		
		for(int i = 0; i < data.length; i++){	//for
			if(data[i][NAME].equals(name)){	//if
				data[i][VALUE] = newValue;
				break;
			}	//close if
		}	//close for
		
	}	//close method setValue
	
	public void addValue(String name, String newValue){	//method addValue
		
		String[][] newData = new String[data.length + 1][data[0].length];
		for(int i = 0; i < data.length; i++){	//for
			newData[i] = data[i];
		}	//close for
		newData[data.length] = new String[]{name, newValue};
		data = newData;
		
	}	//close method addValue
	
	public void removeValue(String name){	//method removeValue
		
		int index = -1;
		for(int i = 0; i < data.length; i++){	//for
			if(data[i][NAME].equals(name)){	//if
				index = i;
				break;
			}	//close if
		}	//close for
		if(index != -1){	//if
			String[][] newData = new String[data.length - 1][data[0].length];
			for(int i = 0; i < newData.length; i++){	//for
				if(i != index){	//if
					if(i > index){	//if
						newData[i] = data[i - 1];
					}else{	//else
						newData[i] = data[i];
					}	//close else
				}	//close if
			}	//close for
			data = newData;
		}	//close if
		
	}	//close method removeValue
	
	public String getPath(){	//method getPath
		
		return path;
		
	}	//close method getPath
	
	public void setPath(String path){	//method setPath
		
		this.path = path;
		
	}	//close method setPath
	
	private void readDataFromFile(){	//method readDataFromFile
		
		if(!new File(getPath()).exists()) data = new String[][]{{"", ""}};
		
		String[][] returned;
		int size1 = 0;
		int size2;
		String cache = "";
		
		resetFileReader();
		
		while(fileReader.hasNext()){	//while
			cache = fileReader.nextLine();
			size1++;
		}	//close while
		
		tokens = new StringTokenizer(cache, ":");
		size2 = tokens.countTokens();
		returned = new String[size1][size2];
		
		resetFileReader();

		for(int i = 0; fileReader.hasNext(); i++){	//for
			tokens = new StringTokenizer(fileReader.nextLine(), ":");
			for(int j = 0; tokens.hasMoreTokens(); j++){	//for
				returned[i][j] = tokens.nextToken();
			}	//close for
		}	//close for
		
		data = returned;
		
	}	//close method readDataFromFile
	
	public void writeDataToFile() throws IOException{	//method writeDataToFile
		
		String total = "";
		
		for(int i = 0; i < data.length; i++){	//for
			for(int j = 0; j < data[i].length; j++){	//for
				total += data[i][j];
				if(j < data[i].length - 1) total += ":";
			}	//close for
			if(i < data.length - 1) total += "\n";
		}	//close for
		
		Scanner reader = new Scanner(total);
		BufferedWriter writer = new BufferedWriter(new FileWriter(getPath()));
		while(reader.hasNextLine()){	//while
			writer.write(reader.nextLine());
			if(reader.hasNextLine()) writer.newLine();
		}	//close while
		writer.close();
		
	}	//close method writeDataToFile
	
	private void resetFileReader(){	//method resetFileReader
		
		try{	//try
			
			fileReader = new Scanner(new File(getPath()));
		
		}catch(FileNotFoundException e){	//catch
			
			fileReader = null;
			e.printStackTrace();
			
		}	//close catch
		
	}	//close method resetFileReader
	
}	//close class Data