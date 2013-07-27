package firstEngine;

import java.io.IOException;

public class DataSet{
	
	Data[] dataSet;
	
	public DataSet(Data[] dataSet){
		
		setDataSet(dataSet);
		
	}
	
	public int length(){
		
		return dataSet.length;
		
	}
	
	public Data[] getDataSet(){
		
		return dataSet;
		
	}
	
	public void setDataSet(Data[] dataSet){
		
		this.dataSet = dataSet;
		
	}
	
	public void addData(Data data){
		
		Data[] newDataSet = new Data[dataSet.length + 1];
		for(int i = 0; i < dataSet.length; i++){
			newDataSet[i] = dataSet[i];
		}
		newDataSet[dataSet.length] = data;
		dataSet = newDataSet;
		
	}
	
	public void addData(Data[] data){
		
		for(int i = 0; i < data.length; i++){
			addData(data[i]);
		}
		
	}
	
	public void removeData(int index){
		
		Data[] newDataSet = new Data[dataSet.length - 1];
		for(int i = 0; i < dataSet.length; i++){
			if(i > index){
				newDataSet[i] = dataSet[i - 1];
			}else if(i != index){
				newDataSet[i] = dataSet[i];
			}
		}
		dataSet = newDataSet;
		
	}
	
	public Data getData(int index){
		
		return dataSet[index];
		
	}
	
	public void setData(int index, Data data){
		
		dataSet[index] = data;
		
	}
	
	public int countName(String name){
		
		int count = 0;
		for(int i = 0; i < dataSet.length; i++){
			count += dataSet[i].countName(name);
		}
		return count;
		
	}
	
	public String getValue(String name){	//method getValue
		
		int index = searchForValue(name);
		if(index > -1){	//if
			return dataSet[index].getValue(name);
		}else{	//else
			return "";
		}	//close else
		
	}	//close method getValue
	
	public void setValue(String name, String newValue){	//method setValue
		
		int index = searchForValue(name);
		if(index > -1) dataSet[index].setValue(name, newValue);
		
	}	//close method setValue
	
	public void addValue(String name, String newValue){	//method addValue
		
		dataSet[0].addValue(name, newValue);
		
	}	//close method addValue
	
	public void removeValue(String name){	//method removeValue
		
		int index = searchForValue(name);
		if(index > -1) dataSet[index].removeValue(name);
		
	}	//close method removeValue
	
	public void writeDataToFile(){
		
		for(int i = 0; i < dataSet.length; i++){
			try{
				dataSet[i].writeDataToFile();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	private int searchForValue(String name){	//method searchForValue
		
		for(int i = 0; i < dataSet.length; i++){	//for
			String value = dataSet[i].getValue(name);
			if(!value.equals("")){	//if
				return i;
			}	//close if
		}	//close for
		return -1;
		
	}	//close method searchForValue
	
}