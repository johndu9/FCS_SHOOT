package firstEngine;

import java.util.Random;
import java.util.StringTokenizer;

public class Color{	//class Color

	private float[] values = new float[4];
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int ALPHA = 3;
	private static Random random = new Random();
	
	public Color(float[] values){	//constructor
		
		setRed(values[RED]);
		setGreen(values[GREEN]);
		setBlue(values[BLUE]);
		setAlpha(values[ALPHA]);
		
	}	//close constructor
	
	public Color(float red, float green, float blue, float alpha){	//constructor
		
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
		
	}	//close constructor
	
	public static void randomColor(Color color, int index, float minValue, float maxValue){
		
		color.setValue(index, (maxValue - minValue) * random.nextFloat() + minValue);
	}
	
	public static void randomColor(Color color, int beginIndex, int endIndex, float minValue, float maxValue){
		for(int i = beginIndex; i < endIndex; i++){
			randomColor(color, i, minValue, maxValue);
		}
	}
	
	public static Color addColors(Color color1, Color color2, float alpha){
		
		float[] values = new float[4];
		for(int i = 0; i < values.length - 1; i++){
			values[i] = color1.getValue(i) + color2.getValue(i);
		}
		values[3] = alpha;
		return new Color(values);
		
	}
	
	public static Color addColors(Color color1, Color color2){
		return addColors(color1, color2, color1.getAlpha() + color2.getAlpha());
	}
	
	public static Color subtractColors(Color color1, Color color2, float alpha){
		
		float[] values = new float[4];
		for(int i = 0; i < values.length - 1; i++){
			values[i] = color1.getValue(i) - color2.getValue(i);
		}
		values[3] = alpha;
		return new Color(values);
		
	}
	
	public static Color subtractColors(Color color1, Color color2){
		return addColors(color1, color2, color1.getAlpha() - color2.getAlpha());
	}

	public float[] getValues(){	//method getValues

		return values;
		
	}	//close method getValues

	public void setValues(float[] values){	//method setValues
		
		this.values = values;
		
	}	//close method setValues

	public float getValue(int index){	//method getValue
		
		return values[index];
		
	}	//close method getValue

	public void setValue(int index, float value){	//method setValue
		
		values[index] = value;
		
	}	//close method setValue

	public float getRed(){	//method getRed
		
		return values[0];
		
	}	//close method getRed

	public void setRed(float red){	//method setRed
		
		values[0] = red;
		
	}	//close method setRed

	public float getGreen(){	//method getGreen
		
		return values[1];
		
	}	//close method getGreen

	public void setGreen(float green){	//method setGreen
		
		values[1] = green;
		
	}	//close method setGreen

	public float getBlue(){	//method getBlue
		
		return values[2];
		
	}	//close method getBlue

	public void setBlue(float blue){	//method setBlue
		
		values[2] = blue;
		
	}	//close method setBlue

	public float getAlpha(){	//method getAlpha

		return values[3];
		
	}	//close method getAlpha

	public void setAlpha(float alpha){	//method setAlpha
		
		values[3] = alpha;
		
	}	//close method setAlpha
	
	public String toString(){	//method toString
		
		String returned = "";
		for(int i = 0; i < values.length; i++){
			returned += values[i];
			if(i < values.length - 1) returned += ",";
		}
		return returned;
		
	}	//close method toString
	
	public static Color parseColor(String s){	//method parseColor
		
		Color color = new Color(0, 0, 0, 0);
		StringTokenizer tokenizer = new StringTokenizer(s, ",");
		for(int i = 0; i < color.getValues().length; i++){	//for
			color.setValue(i, Float.parseFloat(tokenizer.nextToken()));
		}	//close for
		return color;
		
	}	//close method parseColor
	
}	//close class Color