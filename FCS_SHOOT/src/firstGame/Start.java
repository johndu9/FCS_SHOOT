package firstGame;

import firstEngine.Button;
import firstEngine.Data;
import firstEngine.DataSet;
import firstEngine.GameDisplay;
import firstEngine.Sound;
import firstEngine.Timer;

public class Start{	//class Start
	
	static Game game;
	
	public static void main(String [] args){	//method main
		
		Data settings = new Data(Data.DIR_PATH + "settings.txt");
		Data controls = new Data(Data.DIR_PATH + "controls.txt");
		Data skin = new Data(Data.DIR_PATH + "res" + Data.SEP + "skn" + Data.SEP + settings.getValue("skin") + ".txt");
		Data weapons = new Data(Data.DIR_PATH + "res" + Data.SEP + "txt" + Data.SEP + "weapons.txt");
		Data gameModes = new Data(Data.DIR_PATH + "res" + Data.SEP + "txt" + Data.SEP + "gameModes.txt");
		Data tips = new Data(Data.DIR_PATH + "res" + Data.SEP + "txt" + Data.SEP + "tips.txt");
		DataSet dataSet = new DataSet(new Data[]{settings, controls, skin, weapons, gameModes, tips});
		
		Timer.init(Double.parseDouble(dataSet.getValue("gameSpeed")));
		Sound.init(Integer.parseInt(dataSet.getValue("masterVolume")));
		Button.init();
		GameDisplay.init(Integer.parseInt(dataSet.getValue("frameCap")));
		Trail.init(dataSet.getValue("showTrail").equals("true"));

		game = new Game(dataSet);
		
		while(true){	//while

			game.update();
			game.updatePolygons();
			
		}	//close while
		
	}	//close method main
	
}	//close class Start