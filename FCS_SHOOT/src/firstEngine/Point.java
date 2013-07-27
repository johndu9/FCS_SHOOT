package firstEngine;

public class Point{	//class FirstPofloat
	
	public float x;
	public float y;
	
	public Point(float x, float y){	//constructor
		
		this.x = x;
		this.y = y;
		
	}	//close constructor

	public Point(Point point) {	//constructor

		this.x = point.x;
		this.y = point.y;
		
	}	//close constructor
	
	public double directionTo(Point other){	//method directionTo
		
		double direction;
		
		if(other.x == x && other.y > y) return Math.PI / 2;
		else if(other.x == x && other.y < y) return -Math.PI / 2;
		
		direction = Math.atan(Math.abs(y - other.y) / (x - other.x));
		if(other.x < x) direction += Math.PI;
		if(other.y < y) direction = -direction;
		return -direction;
		
	}	//close method directionTo
	
	public double directionTo(float x, float y){	//method directionTo
		
		return directionTo(new Point(x, y));
		
	}	//close method directionTo
	
	public double distanceTo(Point other){	//method distanceTo
		
		return Math.sqrt(Math.pow((other.x - x), 2) + Math.pow((other.y - y), 2));
		
	}	//close method distanceTo
	
	public double distanceTo(float x, float y){	//method distanceTo
		
		return distanceTo(new Point(x, y));
		
	}	//close method distanceTo
	
	public Point scalePoint(float xScale, float yScale){	//method scalePoint
		
		return new Point(x * xScale, y * yScale);
		
	}	//close method scalePoint
	
	public boolean equals(Point other){	//method equals
		
		return x == other.x && y == other.y;
		
	}	//close method equals
	
	public boolean equals(float x, float y){	//method equals
		
		return equals(new Point(x, y));
		
	}	//close method equals
	
	public String toString(){	//method toString
		
		return "(" + x + "," + y + ")";
		
	}	//close method toString
	
}	//close class FirstPofloat