package firstGame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import firstEngine.Bar;
import firstEngine.Button;
import firstEngine.Data;
import firstEngine.DataSet;
import firstEngine.Menu;
import firstEngine.Color;
import firstEngine.GameDisplay;
import firstEngine.Point;
import firstEngine.Sound;
import firstEngine.Text;
import firstEngine.TextScreen;
import firstEngine.Timer;
import firstEngine.Toggle;
import firstEngine.polygon.ControllablePolygon;
import firstEngine.polygon.FirstPolygon;
import firstEngine.polygon.MovingPolygon;
import firstGame.players.*;

public class Game{	//class Game
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Comet> comets = new ArrayList<Comet>();
	private ArrayList<FirstPolygon> ghosts = new ArrayList<FirstPolygon>();
	private ArrayList<MovingPolygon> stars = new ArrayList<MovingPolygon>();
	private ArrayList<MovingPolygon> explosion = new ArrayList<MovingPolygon>();
	private ArrayList<FirstPolygon> text = new ArrayList<FirstPolygon>();
	private FirstPolygon background;
	private FirstPolygon foreground;
	private GameCursor cursor;
	private int polygonCount;
	private Timer ghostDelay = new Timer();
	private Timer cometDelay = new Timer();
	private Timer volumeDelay = new Timer();
	private Timer diffDelay = new Timer();
	private String[] weaponSelect = new String[]{"Turret", "Spray", "Cross", "Clock", "Glob", "Spread", "Burst", "Pulse", "Discharge"};
	private String[] playerSelect = new String[]{"1Player", "2Player", "3Player", "4Player"};
	private String[] gameModeSelect = new String[]{"Standard", "Simple", "PVP"};
	private String[] skinSelect = new File(Data.DIR_PATH + "res" + Data.SEP + "skn" + Data.SEP).list();
	private String[] gameModeDescriptions;
	private String[] weaponDescriptions;
	private String[] tips;
	private Menu mainMenu =
		new Menu(new String[]{"Play", "Options", "", "Help", "Lore", "Credits", "Exit"}, 2, 0, 2, true, 1, true);
	private Menu playMenu = new Menu(new String[]{"Start Game", "Game Mode", "Weapon", "Players"}, 2, 10, 2, true, 1, true);
	private Menu options = new Menu(new String[]{"Volume", "Display", "Controls", "Game", "Skin"}, 2, 10, 2, true, 1, true);
	private Menu skinMenu = new Menu(new String[]{"Skin", "Confirm"}, 2, 20, 2, true, 1, true);
	private Menu displayMenu =
		new Menu(new String[]{"Debug View", "Fullscreen", "Show Shake", "Show Trail", "Show Number", "Show Cursor"},
		2, 20, 2, true, 1, true);
	private Menu gameOptionMenu =
		new Menu(new String[]{"Use Seed", "Refresh Seed", "Background Run"}, 2, 20, 4, true, 1, true);
	private Menu controlsMenu =
		new Menu(new String[]{"Player", "Type", "Up", "Left", "Down", "Right", "Action1", "Action2"}, 2, 20, 2, true, 1, true);
	private Menu scoreMenu = new Menu(new String[]{"Yes", "No"}, 2, 11, 16, false, 1, true);
	private Menu pausedMenu = new Menu(new String[]{"Return", "Options", "Main Menu", "Exit"}, 2, 0, 2, true, 1, true);
	private TextScreen help, lore, credits;
	private Sound[] explosionSound = new Sound[8];
	private int gameMode, difficulty, difHunted, huntedNormal, huntedFaster, huntedHunter, huntedSeeker, score, tipNumber;
	private int weaponType, playerCount;
	private int noSpawnKey, invincibleKey, diffUpKey, diffDownKey, debugKey;
	private int frameCapKey, showTextKey, showNumberKey, showTrailKey, pausedKey, fullscreenKey;
	private int quickYesKey, quickNoKey, volUpKey, volDownKey, muteKey;
	private int skinIndex, controlType, curPlayer;
	private int shakes = 0;
	private int shakeIntensity = 0;
	private int maxShake = 0;
	private int levelSeed;
	private Toggle noSpawn = new Toggle(false);
	private Toggle invincible = new Toggle(false);
	private Toggle fullscreen = new Toggle(false);
	private Toggle debug = new Toggle(false);
	private Toggle mute = new Toggle(false);
	private Toggle paused = new Toggle(false);
	private Toggle frameCap = new Toggle(true);
	private Toggle showShake;
	private Toggle showNumber;
	private Toggle useSeed;
	private Toggle refreshSeed;
	private Toggle backgroundRun;
	private boolean mainMenuState, gameState, playMenuState, optionsState;
	private boolean volumeState, displayState, controlState, gameOptionState, skinState;
	private boolean helpState, loreState, creditsState;
	private Random random = new Random();
	private Random cometType;
	private int explosionCount, minExplosionSize, maxExplosionSize;
	private int starCount, minStarSize, maxStarSize;
	private Color explosionColor[], starsColor[];
	private Bar volumeBar =
		new Bar(
			288, 16,
			0,
			Text.columnRowToPoint(2, 30, 2),
			(float)Sound.getMasterVolume() / 100);
	private ArrayList<Bar> chargeBars = new ArrayList<Bar>();
	private Button masterVolumePlus = new Button("+", 2, 40, 2, true);
	private Button masterVolumeMinus = new Button("-", 2, 20, 2, true);
	private DataSet dataSet;
	
	public static final int STANDARD = 0;
	public static final int SIMPLE = 1;
	public static final int PVP = 2;

	public static final int TURRET = 0;
	public static final int SPRAY = 1;
	public static final int CROSS = 2;
	public static final int CLOCK = 3;
	public static final int GLOB = 4;
	public static final int SPREAD = 5;
	public static final int BURST = 6;
	public static final int PULSE = 7;
	public static final int DISCHARGE = 8;
	
	public Game(DataSet dataSet){	//constructor

		this.dataSet = dataSet;
		reload();
		for(int i = 0; i < explosionSound.length; i++){	//for
			explosionSound[i] = new Sound("explosion" + i);
		}	//close for
		
		showShake = new Toggle(dataSet.getValue("showShake").equals("true"));
		shakeIntensity = Integer.parseInt(dataSet.getValue("shakeIntensity"));
		maxShake = Integer.parseInt(dataSet.getValue("maxShake"));
		
		showNumber = new Toggle(dataSet.getValue("showNumber").equals("true"));
		
		backgroundRun = new Toggle(dataSet.getValue("backgroundRun").equals("true"));

		help = new TextScreen("help", 3);
		lore = new TextScreen("lore", 2);
		credits = new TextScreen("credits", 1);

		tips = new String[dataSet.countName("tip")];
		for(int i = 0; i < tips.length; i++){
			tips[i] = dataSet.getValue("tip" + i);
		}

		weaponDescriptions = new String[dataSet.countName("weapon")];
		for(int i = 0; i < weaponDescriptions.length; i++){
			weaponDescriptions[i] = dataSet.getValue("weapon" + i);
		}
		gameModeDescriptions = new String[dataSet.countName("gameMode")];
		for(int i = 0; i < gameModeDescriptions.length; i++){
			gameModeDescriptions[i] = dataSet.getValue("gameMode" + i);
		}

		noSpawnKey = Keyboard.getKeyIndex(dataSet.getValue("noSpawnKey"));
		invincibleKey = Keyboard.getKeyIndex(dataSet.getValue("invincibleKey"));
		diffUpKey = Keyboard.getKeyIndex(dataSet.getValue("diffUpKey"));
		diffDownKey = Keyboard.getKeyIndex(dataSet.getValue("diffDownKey"));
		debugKey = Keyboard.getKeyIndex(dataSet.getValue("debugKey"));
		
		frameCapKey = Keyboard.getKeyIndex(dataSet.getValue("frameCapKey"));
		showTextKey = Keyboard.getKeyIndex(dataSet.getValue("showTextKey"));
		showNumberKey = Keyboard.getKeyIndex(dataSet.getValue("showNumberKey"));
		showTrailKey = Keyboard.getKeyIndex(dataSet.getValue("showTrailKey"));
		fullscreenKey = Keyboard.getKeyIndex(dataSet.getValue("fullscreenKey"));
		pausedKey = Keyboard.getKeyIndex(dataSet.getValue("pausedKey"));

		quickYesKey = Keyboard.getKeyIndex(dataSet.getValue("quickYesKey"));
		quickNoKey = Keyboard.getKeyIndex(dataSet.getValue("quickNoKey"));
		
		volUpKey = Keyboard.getKeyIndex(dataSet.getValue("volUpKey"));
		volDownKey = Keyboard.getKeyIndex(dataSet.getValue("volDownKey"));
		muteKey = Keyboard.getKeyIndex(dataSet.getValue("muteKey"));
		
		useSeed = new Toggle(dataSet.getValue("useSeed").equals("true"));
		refreshSeed = new Toggle(dataSet.getValue("refreshSeed").equals("true"));
		try{
			levelSeed = Integer.parseInt(String.valueOf(dataSet.getValue("levelSeed")));
		}catch(NumberFormatException e){
			levelSeed = dataSet.getValue("levelSeed").hashCode();
		}
		if(useSeed.getState()){
			cometType = new Random(levelSeed);
			Comet.setRandom(levelSeed);
		}else{
			cometType = random;
		}
		
		mainMenuState = true;
		playMenuState = false;
		playerCount = 1;
		reset();
		gameState = false;
		players.clear();
		comets.clear();
		
	}	//close constructor
	
	public void update(){	//method update
		
		text.clear();
		
		for(int i = 0; i < players.size(); i++){
			polygonCount += players.get(i).size();
		}
		for(int i = 0; i < chargeBars.size(); i++){
			polygonCount += chargeBars.get(i).size();
		}
		for(int i = 0; i < comets.size(); i++){
			polygonCount += comets.get(i).size();
		}
		
		if(showShake.getState() && shakes > 0){
			shake();
			shakes--;
		}else if(FirstPolygon.getRenderPosition().distanceTo(0, 0) > 1){
			Point oldPos = FirstPolygon.getRenderPosition();
			Point newPos = new Point(
				oldPos.x + (float)(Math.cos(oldPos.directionTo(new Point (0, 0))) * oldPos.distanceTo(0, 0) / 4),
				oldPos.y + (float)(Math.sin(oldPos.directionTo(new Point (0, 0))) * oldPos.distanceTo(0, 0) / 4));
			FirstPolygon.setRenderPosition(newPos);
		}else if(!FirstPolygon.getRenderPosition().equals(0, 0)){
			FirstPolygon.setRenderPosition(0, 0);
		}
		
		if(debug.getState()){
			GameDisplay.setDebugTitle(polygonCount);
		}else{
			GameDisplay.setTitle("FCS_SHOOT");
		}
		
		if(frameCap.getState()){
			if(GameDisplay.frameCap != GameDisplay.setFrameCap){
				GameDisplay.frameCap = GameDisplay.setFrameCap;
			}
		}else{
			if(GameDisplay.frameCap != -1){
				GameDisplay.frameCap = -1;
			}
		}
		
		boolean prevFullscreen = fullscreen.getState();
		
		noSpawn.update(Keyboard.isKeyDown(noSpawnKey) && Keyboard.isKeyDown(debugKey));
		invincible.update(Keyboard.isKeyDown(invincibleKey) && Keyboard.isKeyDown(debugKey));
		if(diffDelay.getDelay(100)){
			if(Keyboard.isKeyDown(debugKey) && Keyboard.isKeyDown(diffUpKey)){
				difficulty++;
			}
			if(Keyboard.isKeyDown(debugKey) && Keyboard.isKeyDown(diffDownKey)){
				difficulty--;
			}
		}
		debug.update(Keyboard.isKeyDown(debugKey));
		frameCap.update(Keyboard.isKeyDown(frameCapKey));
		Text.showText.update(Keyboard.isKeyDown(showTextKey));
		Trail.showTrail.update(Keyboard.isKeyDown(showTrailKey));
		showNumber.update(Keyboard.isKeyDown(showNumberKey));
		paused.update(Keyboard.isKeyDown(pausedKey));
		fullscreen.update(Keyboard.isKeyDown(fullscreenKey));
		
		if(!Display.isActive() && !backgroundRun.getState()){
			if(!paused.getState()){
				paused.setState(true);
			}
		}
		
		if(prevFullscreen != fullscreen.getState()){
			fullscreen();
		}
		
		if(volumeDelay.getDelay(100)){
			if(Keyboard.isKeyDown(volUpKey)){
				Sound.setMasterVolume(Sound.getMasterVolume() + 1);
			}
			if(Keyboard.isKeyDown(volDownKey)){
				Sound.setMasterVolume(Sound.getMasterVolume() - 1);
			}
		}
		
		mute.update(Keyboard.isKeyDown(muteKey));
		if(mute.getState()){
			Sound.mute();
		}else{
			Sound.unmute();
		}
		
		for(int i = 0; i < explosion.size(); i ++){	//for
			if(explosion.get(i).outBounds(0, Display.getWidth(), 0, Display.getHeight())){
				explosion.remove(i);
			}
		}	//close for
		
		if(mainMenuState){	//if
			updateMainMenu();
		}
		
		if(playMenuState){	//if
			updatePlayMenu();
		}
		
		if(optionsState){
			
			text.addAll(options.placeMenu());
			
			if(options.isSelected(0)){
				volumeState = true;
				displayState = false;
				controlState = false;
				gameOptionState = false;
				skinState = false;
				options.confirmSelect();
			}else if(options.isSelected(1)){
				volumeState = false;
				displayState = true;
				controlState = false;
				gameOptionState = false;
				skinState = false;
				options.confirmSelect();
			}else if(options.isSelected(2)){
				volumeState = false;
				displayState = false;
				controlState = true;
				gameOptionState = false;
				skinState = false;
				options.confirmSelect();
			}else if(options.isSelected(3)){
				volumeState = false;
				displayState = false;
				controlState = false;
				gameOptionState = true;
				skinState = false;
				options.confirmSelect();
			}else if(options.isSelected(4)){
				volumeState = false;
				displayState = false;
				controlState = false;
				gameOptionState = false;
				skinState = true;
				options.confirmSelect();
			}
			
			if(volumeState){
				updateVolumeMenu();
			}else if(displayState){
				updateDisplayMenu();
			}else if(controlState){
				updateControlsMenu();
			}else if(gameOptionState){
				updateGameOptionMenu();
			}else if(skinState){
				updateSkinMenu();
			}
			
		}else{
			volumeState = false;
			displayState = false;
			controlState = false;
			skinState = false;
		}
		
		if(helpState){

			text.addAll(help.placeScreen());
			
			if(help.done()){	//if
				
				helpState = false;
				mainMenuState = true;
				
			}	//close if
			
		}
		
		if(loreState){
			
			text.addAll(lore.placeScreen());
			
			if(lore.done()){	//if
		
				loreState = false;
				mainMenuState = true;
			
			}	//close if
			
		}
		
		if(creditsState){

			text.addAll(credits.placeScreen());
			
			if(credits.done()){	//if
				
				creditsState = false;
				mainMenuState = true;
				
			}	//close if
			
		}
		
		if(gameState){	//if
			
			if(paused.getState()){	//if
	
				text.addAll(Text.placeText("Paused", 2, 0, 0));
				
				if((players.size() > 0 && gameMode != PVP) || (players.size() > 1 && gameMode == PVP)){
					text.addAll(pausedMenu.placeMenu());
				}
				
				freezeAll();
				
				if(pausedMenu.confirmSelect("Return")){
					paused.setState(false);
					optionsState = false;
				}else if(pausedMenu.confirmSelect("Options")){
					optionsState = true;
				}else if(pausedMenu.confirmSelect("Main Menu")){
					comets.clear();
					players.clear();
					explosion.clear();
					gameState = false;
					optionsState = false;
					mainMenuState = true;
					paused.setState(false);
					unfreezeAll();
				}else if(pausedMenu.confirmSelect("Exit")){
					close();
				}
				
			}else{	//else
				
				optionsState = false;
				
				unfreezeAll();
				
				if(difHunted >= 10 * difficulty && gameMode != PVP){	//if
					
					difHunted = 0;
					difficulty++;
					
				}	//close if
				
			}	//close else
			
			if(players.size() > 0 && showNumber.getState()){
				for(int i = 0; i < players.size(); i++){
					Point newPos = new Point(
						players.get(i).getPosition().x +
							3 * players.get(i).getHeight() / 4 * (float)Math.cos(players.get(i).getDirection()),
						players.get(i).getPosition().y +
							3 * players.get(i).getHeight() / 4 * (float)Math.sin(players.get(i).getDirection()));
					text.addAll(Text.placeText("" + (players.get(i).getPlayerNum() + 1), 2, newPos));
				}
			}
				
			if(players.size() > 0 && gameMode != PVP){	//if				
				updatePVE();
			}
			if(players.size() == 0 && gameMode != PVP){
				generatePVEScore();
			}	//close if
			if(players.size() > 1 && gameMode == PVP){
				updatePVP();
			}
			if(players.size() == 1 && gameMode == PVP){
				generatePVPScore();
			}
				
		}	//close else
		
		polygonCount = stars.size() + explosion.size() + text.size() + cursor.size();
		
	}	//close method update

	public void updatePolygons(){	//method updatePolygons
		
		GameDisplay.update();
		int delta = GameDisplay.getDelta();
		background.update(delta);
		for(int i = 0; i < stars.size(); i++){	//for
			stars.get(i).update(delta);
		}	//close for
		for(int i = 0; i < explosion.size(); i++){	//for
			explosion.get(i).update(delta);
			if(!paused.getState() && explosion.get(i).getPosition().distanceTo(explosion.get(i).getOrigin()) > 64){
				Color newExplosionColor = new Color(explosion.get(i).getColor().getValues());
				newExplosionColor.setAlpha(explosion.get(i).getColor().getAlpha() - 0.05f);
				explosion.get(i).setColor(newExplosionColor);
			}
			if(explosion.get(i).getColor().getAlpha() <= 0){
				explosion.remove(i);
			}
		}	//close for
		for(int i = 0; i < ghosts.size(); i++){
			ghosts.get(i).update(delta);
			if(ghosts.get(i).getColor().getAlpha() <= 0){
				ghosts.remove(i);
			}
		}
		if(!paused.getState() && ghostDelay.getDelay(10)){
			for(int i = 0; i < ghosts.size(); i++){
				ghosts.get(i).getColor().setAlpha(ghosts.get(i).getColor().getAlpha() - 0.05f);
			}
		}
		for(int i = 0; i < comets.size(); i++){	//for
			comets.get(i).update(delta);
			if(debug.getState()){
				comets.get(i).renderDebug();
			}
		}	//close for
		for(int i = 0; i < players.size(); i++){	//for
			players.get(i).update(delta);
			if(debug.getState()){
				players.get(i).renderDebug();
			}
		}	//close for
		for(int i = 0; i < text.size(); i++){	//for
			text.get(i).update(delta);
			if(debug.getState()){
				text.get(i).renderHitbox();
			}
		}	//close for
		if(volumeState){
			volumeBar.update(delta);
		}
		if(gameState){
			for(int i = 0; i < chargeBars.size(); i++){
				chargeBars.get(i).update(delta);
			}
		}
		cursor.update(delta);
		foreground.update(delta);
		
	}	//close method updatePolygons
	
	private void updateMainMenu(){

		text.addAll(Text.placeText("FCS_SHOOT", 4, 0, 0));
		text.addAll(mainMenu.placeMenu());
		
		if(mainMenu.confirmSelect("Play")){
			optionsState = false;
			playMenuState = true;
		}else if(mainMenu.confirmSelect("Options")){
			playMenuState = false;
			optionsState = true;
			for(int i = 0; i < skinSelect.length; i++){
				if(skinSelect[i].substring(0, skinSelect[i].length() - 4).equals(dataSet.getValue("skin"))){
					skinIndex = i;
					break;
				}
			}
		}else if(mainMenu.confirmSelect("Help")){
			mainMenuState = false;
			playMenuState = false;
			optionsState = false;
			helpState = true;
			help.refresh();
		}else if(mainMenu.confirmSelect("Lore")){
			mainMenuState = false;
			playMenuState = false;
			optionsState = false;
			loreState = true;
			lore.refresh();
		}else if(mainMenu.confirmSelect("Credits")){
			mainMenuState = false;
			playMenuState = false;
			optionsState = false;
			creditsState = true;
			credits.refresh();
		}else if(mainMenu.confirmSelect("Exit")){
			close();
		}
		
	}
	
	private void updateVolumeMenu(){

		text.addAll(masterVolumePlus.placeButton());
		text.addAll(masterVolumeMinus.placeButton());
		if(masterVolumePlus.confirmSelect()){
			Sound.setMasterVolume(Sound.getMasterVolume() + 1);
		}
		if(masterVolumeMinus.confirmSelect()){
			Sound.setMasterVolume(Sound.getMasterVolume() - 1);
		}
		volumeBar.setValue((float)Sound.getMasterVolume() / 100);
		volumeBar.setValue();
		if(Sound.getMasterVolume() != volumeBar.getValue() && !Sound.isMute()){
			Sound.setMasterVolume(Math.round(volumeBar.getValue() * 100));
		}
		String vol = "" + Sound.getMasterVolume();
		if(Sound.isMute()) vol = "MUTE";
		text.addAll(Text.placeText(vol, 2, 43, 2));
		
	}
	
	private void updateControlsMenu(){

		text.addAll(controlsMenu.placeMenu());
		text.addAll(Text.placeText("[" + (curPlayer + 1) + "/" + playerSelect.length + "] " + (curPlayer + 1), 2, 30, 2));
		String type = dataSet.getValue("craft" + curPlayer + "type");
		text.addAll(Text.placeText("[" + typeToInt(type) + "/" + Player.CONTROL_TYPES.length + "] " + type, 2, 30, 4));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "up"), 2, 30, 6));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "left"), 2, 30, 8));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "down"), 2, 30, 10));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "right"), 2, 30, 12));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "action1"), 2, 30, 14));
		text.addAll(Text.placeText(dataSet.getValue("craft" + curPlayer + "action2"), 2, 30, 16));
		
		if(controlsMenu.isSelected(0)){
			if(curPlayer == 3){
				curPlayer = 0;
			}else{
				curPlayer++;
			}
			controlsMenu.confirmSelect();
		}else if(controlsMenu.isSelected(1)){
			controlType = ControllablePolygon.controlTypeToInt(dataSet.getValue("craft" + curPlayer + "type"));
			if(controlType == ControllablePolygon.STANDARD){
				controlType = ControllablePolygon.RELATIVE;
			}else if(controlType == ControllablePolygon.RELATIVE){
				controlType = ControllablePolygon.TANK;
			}else if(controlType == ControllablePolygon.TANK){
				controlType = ControllablePolygon.STANDARD;
			}
			dataSet.setValue("craft" + curPlayer + "type", ControllablePolygon.controlTypeToString(controlType));
			if(players.size() != 0 && players.size() <= curPlayer + 1){
				players.get(curPlayer).setControlType(controlType);
			}
			controlsMenu.confirmSelect();
		}else if(controlsMenu.anySelected(2, controlsMenu.size())){

			text.addAll(Text.placeText(" Press", 2, 40, 2 * controlsMenu.isSelected() + 2));
			text.addAll(Text.placeText("any key", 2, 40, 2 * controlsMenu.isSelected() + 3));
			
			Mouse.next();
			if(Mouse.isButtonDown(Mouse.getEventButton())){
				dataSet.setValue(
					"craft" + curPlayer + controlsMenu.getButtonName(controlsMenu.isSelected()).toLowerCase(),
					Mouse.getButtonName(Mouse.getEventButton()));
				if(players.size() != 0 && players.size() <= curPlayer + 1){
					players.get(curPlayer).setControl(controlsMenu.isSelected() - 2, -Mouse.getEventButton());
				}
				controlsMenu.confirmSelect();
			}
			Keyboard.next();
			if(Keyboard.isKeyDown(Keyboard.getEventKey())){
				dataSet.setValue(
					"craft" + curPlayer + controlsMenu.getButtonName(controlsMenu.isSelected()).toLowerCase(),
					Keyboard.getKeyName(Keyboard.getEventKey()));
				Keyboard.next();
				if(players.size() != 0 && players.size() <= curPlayer + 1){
					players.get(curPlayer).setControl(controlsMenu.isSelected() - 2, Keyboard.getEventKey());
				}
				controlsMenu.confirmSelect();
			}
			
		}
		
	}
	
	private void updateDisplayMenu(){

		text.addAll(displayMenu.placeMenu());
		text.addAll(Text.placeText("" + debug.getState(), 2, 35, 2));
		text.addAll(Text.placeText("" + fullscreen.getState(), 2, 35, 4));
		text.addAll(Text.placeText(dataSet.getValue("showShake"), 2, 35, 6));
		text.addAll(Text.placeText("" + Trail.showTrail.getState(), 2, 35, 8));
		text.addAll(Text.placeText("" + showNumber.getState(), 2, 35, 10));
		text.addAll(Text.placeText(dataSet.getValue("showCursor"), 2, 35, 12));
		
		if(displayMenu.confirmSelect(0)){
			debug.toggle();
		}else if(displayMenu.confirmSelect(1)){
			fullscreen.toggle();
			fullscreen();
		}else if(displayMenu.confirmSelect(2)){
			showShake.toggle();
			dataSet.setValue("showShake", "" + showShake.getState());
		}else if(displayMenu.confirmSelect(3)){
			Trail.showTrail.toggle();
			dataSet.setValue("showTrail", "" + Trail.showTrail.getState());
		}else if(displayMenu.confirmSelect(4)){
			showNumber.toggle();
			dataSet.setValue("showNumber", "" + showNumber.getState());
		}else if(displayMenu.confirmSelect(5)){
			GameCursor.toggleShowCursor();
			dataSet.setValue("showCursor", "" + GameCursor.showCursor());
		}
		
	}
	
	private void updateGameOptionMenu(){

		text.addAll(gameOptionMenu.placeMenu());
		text.addAll(Text.placeText("Seed:          " + levelSeed, 2, 20, 2));
		
		text.addAll(Text.placeText("" + useSeed.getState(), 2, 35, 4));
		text.addAll(Text.placeText("" + refreshSeed.getState(), 2, 35, 6));
		text.addAll(Text.placeText("" + backgroundRun.getState(), 2, 35, 8));
		
		if(gameOptionMenu.confirmSelect(0)){
			useSeed.toggle();
			dataSet.setValue("useSeed", "" + useSeed.getState());
		}else if(gameOptionMenu.confirmSelect(1)){
			refreshSeed.toggle();
			dataSet.setValue("refreshSeed", "" + refreshSeed.getState());
		}else if(gameOptionMenu.confirmSelect(2)){
			backgroundRun.toggle();
			dataSet.setValue("backgroundRun", "" + backgroundRun.getState());
		}
		
	}
	
	private void updateSkinMenu(){

		text.addAll(skinMenu.placeMenu());
		text.addAll(Text.placeText("[" + (skinIndex + 1) + "/" + skinSelect.length + "] " +
			skinSelect[skinIndex].substring(0, skinSelect[skinIndex].length() - 4),
			2, 25, 2));
		
		if(skinMenu.confirmSelect(0)){
			if(skinIndex < skinSelect.length - 1){
				skinIndex++;
			}else{
				skinIndex = 0;
			}
		}else if(skinMenu.confirmSelect(1)){
			dataSet.setValue("skin", skinSelect[skinIndex].substring(0, skinSelect[skinIndex].length() - 4));
			dataSet.setData(
				2,
				new Data(
					Data.DIR_PATH + "res" + Data.SEP + "skn" + Data.SEP + dataSet.getValue("skin") + ".txt"));
			reload();
		}
		
	}
	
	private void updatePlayMenu(){

		text.addAll(playMenu.placeMenu());

		text.addAll(Text.placeText("[" + (gameMode + 1) + "/" + gameModeSelect.length + "] " + gameModeSelect[gameMode],
			2, 20, 4));
		text.addAll(Text.placeText(gameModeDescriptions[gameMode], 2, 12, 5));
		text.addAll(Text.placeText("[" + (weaponType + 1) + "/" + weaponSelect.length + "] " + weaponSelect[weaponType],
			2, 20, 6));
		text.addAll(Text.placeText(weaponDescriptions[weaponType], 2, 12, 7));
		text.addAll(Text.placeText("[" + playerCount + "/" + playerSelect.length + "] " + playerSelect[playerCount - 1],
			2, 20, 8));
		
		if(playMenu.isSelected("Start Game")){
			mainMenuState = false;
			playMenuState = false;
			if(gameMode == PVP && playerCount < 2){
				playerCount = 2;
			}
			reset();
			playMenu.confirmSelect();
		}else if(playMenu.isSelected("Weapon")){
			if(weaponType < weaponSelect.length - 1){
				weaponType++;
			}else{
				weaponType = 0;
			}
			playMenu.confirmSelect();
		}else if(playMenu.isSelected("Players")){
			if(playerCount < playerSelect.length){
				playerCount++;
			}else{
				playerCount = 1;
			}
			playMenu.confirmSelect();
		}else if(playMenu.isSelected("Game Mode")){
			if(gameMode < gameModeSelect.length - 1){
				gameMode++;
			}else{
				gameMode = STANDARD;
			}
			playMenu.confirmSelect();
		}
		
	}
	
	private void updatePVE(){

		String difficultyCounter = "Difficulty: " + difficulty;
		text.addAll(
			Text.placeText(
				difficultyCounter, 2,
				Text.getRightColumn(difficultyCounter.length(), 2),
				Text.getBottomRow(2) - playerCount - 1));
		
		if(!noSpawn.getState()){	//if
			
			if(difficulty > 0 && !paused.getState() && players.size() > 0 && cometDelay.getDelay(1000 / difficulty)){	//if
				
				float cometProb = cometType.nextFloat();
				if(gameMode == STANDARD && difficulty > 1 && cometProb < 0.25f){
					comets.add(new Comet(40, 450, new Point(0, 0), FirstPolygon.EMPTY, false));
				}else if(gameMode == STANDARD && difficulty > 2 && cometProb < 0.4f){
					comets.add(new Comet(36, 350, new Point(0, 0), players.get(0), true));
				}else if(gameMode == STANDARD && difficulty > 4 && cometProb < 0.5f){
					comets.add(new Comet(44, 450, new Point(0, 0), players.get(0), true));
				}else if(gameMode != PVP){
					comets.add(new Comet(32, 350, new Point(0, 0), FirstPolygon.EMPTY, false));
				}
				
			}	//close if
			
		}else{	//else
			
			comets.clear();
		
		}	//close else
		
		for(int i = 0; i < players.size(); i++){	//for
			
			chargeBars.get(i).setValue((float)players.get(i).getCharge() / Player.MAXCHARGE);
			
			for(int j = 0; j < comets.size(); j++){	//for
				
				if(!invincible.getState() && players.get(i).hits(comets.get(j))){	//if
					killPlayer(i);
					comets.remove(j);
					break;
					
				}	//close if
				
				for(int k = 0; k < players.get(i).getLasers().size(); k++){	//for
					
					if(players.get(i).getLasers().get(k).hits(comets.get(j))){	//if

						if(comets.get(j).getHeight() == 32){
							huntedNormal++;
						}else if(comets.get(j).getHeight() == 40){
							huntedFaster++;
						}else if(comets.get(j).getHeight() == 36){
							huntedHunter++;
						}else if(comets.get(j).getHeight() == 44){
							huntedSeeker++;
						}
						difHunted++;
						generateExplosion(
							(int)comets.get(j).getPosition().x, (int)comets.get(j).getPosition().y);
						generateGhost(comets.get(j));
						comets.remove(j);
						players.get(i).removeLaser(k);
						break;
						
					}	//close if
					
				}	//close for
				
			}	//close for
			
		}	//close for
		
	}
	
	private void updatePVP(){

		for(int i = 0; i < players.size(); i++){
			
			boolean dead = false;
			chargeBars.get(i).setValue((float)players.get(i).getCharge() / Player.MAXCHARGE);
			
			for(int j = 0; j < players.get(i).getLasers().size(); j++){
				for(int k = 0; k < players.size(); k++){
					if(i != k && players.get(i).getLasers().get(j).hits(players.get(k))){
						players.get(i).removeLaser(j);
						killPlayer(k);
						dead = true;
						break;
					}
				}
				if(dead){
					break;
				}
			}
		}
		
	}
	
	private void generatePVEScore(){

		comets.clear();
		String[] lines1;
		String[] lines2;
		
		if(gameMode == STANDARD){
			score = huntedNormal + 2 * huntedFaster + 3 * huntedHunter +  4 * huntedSeeker + 10 * (difficulty - 1);
			lines1 = new String[]{
				"Normal Comets(" + huntedNormal + "):",
				"Faster Comets(" + huntedFaster + "):",
				"Hunter Comets(" + huntedHunter + "):",
				"Seeker Comets(" + huntedSeeker + "):",
				"Difficulty(" + difficulty + "):",
				"",
				"Score:              " + score,
				"",
				"Tip " + (tipNumber + 1) + ":",
				"  " + tips[tipNumber]};
			lines2 = new String[]{
				"                    " + (1 * huntedNormal),
				"                   +" + (2 * huntedFaster),
				"                   +" + (3 * huntedHunter),
				"                   +" + (4 * huntedSeeker),
				"                   +" + (10 * (difficulty - 1)),
			};
		}else{
			score = huntedNormal + 10 * (difficulty - 1);
			lines1 = new String[]{
				"Normal Comets(" + huntedNormal + "):",
				"Difficulty(" + difficulty + "):",
				"",
				"Score:              " + score,
				"",
				"Tip " + (tipNumber + 1) + ":",
				"  " + tips[tipNumber]};
			lines2 = new String[]{
				"                    " + (1 * huntedNormal),
				"                   +" + (10 * (difficulty - 1)),
			};
		}
		for(int i = 0; i < lines1.length; i++){
			text.addAll(Text.placeText(lines1[i], 2, 0, i + 1));
		}
		for(int i = 0; i < lines2.length; i++){
			text.addAll(Text.placeText(lines2[i], 2, 0, i + 1));
		}
		
		text.addAll(Text.placeText("Try Again?", 2, 0, 16));
		text.addAll(scoreMenu.placeMenu());
		
		if(scoreMenu.confirmSelect("Yes", quickYesKey)){	//if
			
			reset();
			
		}else if(scoreMenu.confirmSelect("No", quickNoKey)){	//if

			chargeBars.clear();
			gameState = false;
			mainMenuState = true;
			
		}	//close if
		
	}
	
	private void generatePVPScore(){
		
		text.addAll(Text.placeText("In The Bloody Civil War,", 2, 0, 1));
		text.addAll(Text.placeText("There Can Be No Winners.", 2, 0, 2));
		text.addAll(Text.placeText("Tip " + (tipNumber + 1) + ":", 2, 0, 4));
		text.addAll(Text.placeText("  " + tips[tipNumber], 2, 0, 5));
		text.addAll(Text.placeText("Try Again?", 2, 0, 16));
		text.addAll(scoreMenu.placeMenu());
		
		if(scoreMenu.confirmSelect("Yes", quickYesKey)){	//if
			
			players.clear();
			reset();
			
		}else if(scoreMenu.confirmSelect("No", quickNoKey)){	//if

			players.clear();
			chargeBars.clear();
			gameState = false;
			mainMenuState = true;
			
		}	//close if
		
	}
	
	private void generateExplosion(int x, int y){	//method generateExplosion
		
		explosionSound[random.nextInt(explosionSound.length)].play();
		
		if(showShake.getState() && shakes <= 20){
			shakes += 4;
		}
		
		for(int i = 0; i < explosionCount; i++){	//for
			
			int explosionSize = random.nextInt(maxExplosionSize - minExplosionSize) + minExplosionSize;
			Color color = explosionColor[random.nextInt(explosionColor.length)];
			Color trailColor = new Color(color.getValues());
			trailColor.setAlpha(color.getAlpha() / 2);
			explosion.add(
				new MovingPolygon(
					FirstPolygon.sizeToPoints(explosionSize, explosionSize),
					random.nextDouble() * 2 * Math.PI,
					new Point(x, y),
					color,
					random.nextInt(32) * 10 + 160,
					0));
			
		}	//close for
		
	}	//close method generateExplosion
	
	private void generateStars(){	//method generateStars
		
		stars.clear();
		for(int i = 0; i < starCount; i++){	//for
			int size = random.nextInt(maxStarSize - minStarSize) + minStarSize;
			stars.add(new MovingPolygon(
				FirstPolygon.sizeToPoints(size, size),
				random.nextDouble() * 2 * Math.PI,
				new Point(random.nextInt(Display.getWidth()), random.nextInt(Display.getHeight())),
				starsColor[random.nextInt(starsColor.length)],
				0,
				random.nextInt(10) * 10 + 50));
		}	//close for
		
	}	//method generateStars
	
	private void fullscreen(){
		
		paused.setState(true);
		int oldX = Display.getWidth();
		int oldY = Display.getHeight();
		GameDisplay.setFullscreen(fullscreen.getState());
		try {
			GameDisplay.loadIcons();
		} catch (IOException e) {
			e.printStackTrace();
		}
		float newX = (float)Display.getWidth() / (float)oldX;
		float newY = (float)Display.getHeight() / (float)oldY;
		background.setPosition(background.getPosition().scalePoint(newX, newY));
		background.setPoints(FirstPolygon.sizeToPoints(Display.getWidth(), Display.getHeight()));
		foreground.setPosition(background.getPosition().scalePoint(newX, newY));
		foreground.setPoints(FirstPolygon.sizeToPoints(Display.getWidth(), Display.getHeight()));
		
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).scalePoint(newX, newY);
		}
		chargeBars.clear();
		for(int i = 0; i < players.size(); i++){
			players.get(i).scalePoint(newX, newY);
			chargeBars.add(
				new Bar(
					128, 16, 0,
					Text.columnRowToPoint(2, Text.getRightColumn(5, 2), Text.getBottomRow(2) - playerCount + i),
					(float)players.get(i).getCharge() / Player.MAXCHARGE));
		}
		for(int i = 0; i < explosion.size(); i++){
			explosion.get(i).setPosition(explosion.get(i).getPosition().scalePoint(newX, newY));
		}
		for(int i = 0; i < stars.size(); i++){
			stars.get(i).setPosition(stars.get(i).getPosition().scalePoint(newX, newY));
		}
		
	}
	
	private Player getNewPlayer(Point position, int[] controls){
		
		if(weaponType == TURRET){
			return new Turret(position, controls);
		}else if(weaponType == DISCHARGE){
			return new Discharge(position, controls);
		}else if(weaponType == GLOB){
			return new Glob(position, controls);
		}else if(weaponType == SPREAD){
			return new Spread(position, controls);
		}else if(weaponType == SPRAY){
			return new Spray(position, controls);
		}else if(weaponType == PULSE){
			return new Pulse(position, controls);
		}else if(weaponType == BURST){
			return new Burst(position, controls);
		}else if(weaponType == CROSS){
			return new Cross(position, controls);
		}else if(weaponType == CLOCK){
			return new Clock(position, controls, comets);
		}else{
			return new Player(position, controls);
		}
		
	}
	
	private void reset(){	//method reset

		Point spawnPosition = new Point(Display.getWidth() / 2, Display.getHeight() / 2);
		chargeBars.clear();
		Player.resetPlayerCount();
		for(int i = 0; i < playerCount; i++){
			String[] controls = new String[]{
				dataSet.getValue("craft" + i + "up"),
				dataSet.getValue("craft" + i + "left"),
				dataSet.getValue("craft" + i + "down"),
				dataSet.getValue("craft" + i + "right"),
				dataSet.getValue("craft" + i + "action1"),
				dataSet.getValue("craft" + i + "action2")};
			if(playerCount != 1){
				float multiplier = 64;
				if(gameMode == PVP) multiplier = 256;
				spawnPosition = new Point(
					(float)(Display.getWidth() / 2 + multiplier * Math.sin(i * 2 * Math.PI / playerCount + Math.PI / 2)),
					(float)(Display.getHeight() / 2 + multiplier * Math.cos(i * 2 * Math.PI / playerCount + Math.PI / 2)));
			}
			players.add(getNewPlayer(spawnPosition, ControllablePolygon.controlsToValues(controls)));
			
			if(dataSet.getValue("craft" + i + "type").equals("STANDARD")){
				players.get(i).setControlType(ControllablePolygon.STANDARD);
			}else if(dataSet.getValue("craft" + i + "type").equals("RELATIVE")){
				players.get(i).setControlType(ControllablePolygon.RELATIVE);
			}else{
				players.get(i).setControlType(ControllablePolygon.TANK);
			}
			chargeBars.add(
				new Bar(
					128, 16, 0,
					Text.columnRowToPoint(2, Text.getRightColumn(5, 2), Text.getBottomRow(2) - playerCount + i),
					(float)players.get(i).getCharge() / Player.MAXCHARGE));
		}
		
		if(refreshSeed.getState() && useSeed.getState()){
			cometType = new Random(levelSeed);
			Comet.setRandom(levelSeed);
		}

		playMenuState = false;
		difHunted = 0;
		huntedNormal = 0;
		huntedFaster = 0;
		huntedHunter = 0;
		huntedSeeker = 0;
		difficulty = 1;
		tipNumber = random.nextInt(tips.length);
		explosion.clear();
		generateStars();
		paused.setState(false);
		gameState = true;

	}	//close method reset
	
	private void reload(){	//method reload
		
		Text.init(Color.parseColor(dataSet.getValue("text")));
		Player.init(
			Color.parseColor(dataSet.getValue("craft")), 
			Color.parseColor(dataSet.getValue("craftTrail")));
		Laser.init(
			Color.parseColor(dataSet.getValue("laser")),
			Color.parseColor(dataSet.getValue("laserOverlay")),
			Color.parseColor(dataSet.getValue("laserGlow")), 
			Color.parseColor(dataSet.getValue("laserTrail")));
		Comet.init(
			Color.parseColor(dataSet.getValue("comet")),
			Color.parseColor(dataSet.getValue("cometTrail")),
			Color.parseColor(dataSet.getValue("cometTracker")));
		GameCursor.init(
			dataSet.getValue("showCursor").equals("true"),
			Color.parseColor(dataSet.getValue("cursor")));

		Player.resetPlayerCount();
		volumeBar = new Bar(volumeBar.getWidth(), volumeBar.getHeight(), 0, volumeBar.getPosition(), volumeBar.getValue());
		
		for(int i = 0; i < chargeBars.size(); i++){
			chargeBars.set(
				i,
				new Bar(
					chargeBars.get(i).getWidth(),
					chargeBars.get(i).getHeight(),
					0,
					chargeBars.get(i).getPosition(),
					chargeBars.get(i).getValue()));
		}
		
		explosionCount = Integer.parseInt(dataSet.getValue("explosionCount"));
		minExplosionSize = Integer.parseInt(dataSet.getValue("minExplosionSize"));
		maxExplosionSize = Integer.parseInt(dataSet.getValue("maxExplosionSize"));
		explosionColor = new Color[dataSet.countName("explosion")];
		for(int i = 0; i < explosionColor.length; i++){	//for
			explosionColor[i] = Color.parseColor(dataSet.getValue("explosion" + i));
		}	//close for
		starCount = Integer.parseInt(dataSet.getValue("starCount"));
		minStarSize = Integer.parseInt(dataSet.getValue("minStarSize"));
		maxStarSize = Integer.parseInt(dataSet.getValue("maxStarSize"));
		starsColor = new Color[dataSet.countName("stars")];
		for(int i = 0; i < starsColor.length; i++){
			starsColor[i] = Color.parseColor(dataSet.getValue("stars" + i));
		}
		background = new FirstPolygon(
			FirstPolygon.sizeToPoints(Display.getWidth(), Display.getHeight()),
			0,
			new Point(Display.getWidth() / 2, Display.getHeight() / 2),
			Color.parseColor(dataSet.getValue("background")));
		foreground = new FirstPolygon(
			FirstPolygon.sizeToPoints(Display.getWidth(), Display.getHeight()),
			0,
			new Point(Display.getWidth() / 2, Display.getHeight() / 2),
			Color.parseColor(dataSet.getValue("foreground")));
		generateStars();
		for(int i = 0; i < explosion.size(); i++){
			explosion.get(i).setColor(explosionColor[random.nextInt(explosionColor.length)]);
			explosion.set(i, new MovingPolygon(
				explosion.get(i).getPoints(),
				explosion.get(i).getDirection(),
				explosion.get(i).getPosition(),
				explosion.get(i).getColor(),
				explosion.get(i).getSpeed(),
				0
				));
			Color trailColor = new Color(explosion.get(i).getColor().getValues());
			trailColor.setAlpha(trailColor.getAlpha() / 2);
		}
		for(int i = 0; i < players.size(); i++){
			ArrayList<Laser> laserCache = players.get(i).getLasers();
			double directionCache = players.get(i).getDirection();
			for(int j = 0; j < laserCache.size(); j++){
				laserCache.set(
					j,
					new Laser(
						laserCache.get(j).getDirection(),
						laserCache.get(j).getPosition(),
						laserCache.get(j).getSpeed()));
			}
			players.set(i, getNewPlayer(players.get(i).getPosition(), players.get(i).getControls()));
			players.get(i).setLasers(laserCache);
			players.get(i).setDirection(directionCache);
		}
		for(int i = 0; i < comets.size(); i++){
			double directionCache = comets.get(i).getDirection();
			comets.set(
				i,
				new Comet(
					comets.get(i).getHeight(),
					comets.get(i).getSpeed(),
					comets.get(i).getPosition(),
					FirstPolygon.EMPTY,
					comets.get(i).showTracker));
			comets.get(i).setDirection(directionCache);
		}
		cursor = new GameCursor(50, 50);
		
	}	//close method reload
	
	private int typeToInt(String type){
		
		if(type.equals("STANDARD")){
			return Player.STANDARD;
		}else if(type.equals("RELATIVE")){
			return Player.RELATIVE;
		}else{
			return Player.TANK;
		}
		
	}
	
	private void freezeAll(){
		for(int i = 0; i < stars.size(); i++){
			stars.get(i).freeze();
		}
		for(int i = 0; i < explosion.size(); i++){
			explosion.get(i).freeze();
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).freeze();
		}
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).freeze();
		}
	}
	
	private void unfreezeAll(){
		for(int i = 0; i < stars.size(); i++){
			stars.get(i).unfreeze();
		}
		for(int i = 0; i < explosion.size(); i++){
			explosion.get(i).unfreeze();
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).unfreeze();
		}
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).unfreeze();
		}
	}
	
	private void shake(){
		
		double direction = random.nextDouble() * Math.PI * 2;
		Point shakeAmount =
			new Point((float)Math.cos(direction) * shakeIntensity, (float)Math.sin(direction) * shakeIntensity);
		Point newRenderPos =
			new Point(FirstPolygon.getRenderPosition().x + shakeAmount.x, FirstPolygon.getRenderPosition().y + shakeAmount.y);
		if(newRenderPos.distanceTo(new Point(0, 0)) > maxShake){
			return;
		}
		FirstPolygon.setRenderPosition(new Point(newRenderPos));
		
	}
	
	private void killPlayer(int index){

		for(int k = 0; k < 4; k++){
			generateExplosion(
				(int)players.get(index).getPosition().x,
				(int)players.get(index).getPosition().y);
		}
		generateGhost(players.get(index));
		players.remove(index);
		chargeBars.remove(index);
		
	}
	
	private void generateGhost(FirstPolygon polygon){
		ghosts.add(
			new FirstPolygon(
				polygon.getPoints(),
				polygon.getDirection(),
				new Point(polygon.getPosition()),
				new Color(polygon.getColor().getValues())));
	}
	
	private void close(){	//method close

		dataSet.setValue("showShake", "" + showShake.getState());
		dataSet.setValue("showTrail", "" + Trail.showTrail.getState());
		dataSet.setValue("showNumber", "" + showNumber.getState());
		dataSet.setValue("masterVolume", "" + Sound.getMasterVolume());
		dataSet.setValue("useSeed", "" + useSeed.getState());
		dataSet.setValue("refreshSeed", "" + refreshSeed.getState());
		dataSet.setValue("backgroundRun", "" + backgroundRun.getState());
		dataSet.writeDataToFile();
		System.exit(99);
		
	}	//method close
	
}	//close class Game