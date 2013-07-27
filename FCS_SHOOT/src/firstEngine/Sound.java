package firstEngine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class Sound{	//class Sound
	
	private Clip clip;
    private FloatControl controller;

	private String name;
	private int volume;
	private static int masterVolume;
	private static int masterVolumeStorage;
	private int loops;
	
	public Sound(String name){	//constructor
		
		setName(name);
		if(findSound()) setVolume(100);
		else System.out.println("Problem encountered initializing sound");
		
	}	//close constructor
	
	public void play(){	//method play
		
		setVolume(getVolume());
		clip.stop();
		clip.setMicrosecondPosition(0);
		clip.start();
		
	}	//close method play
	
	public static void init(int masterVolume){	//method init
		
		setMasterVolume(masterVolume);
		
	}	//close method init
	
	public boolean findSound(){	//method init

        try{	//try

			AudioInputStream stream = AudioSystem.getAudioInputStream(
				new File(Data.DIR_PATH + "res" + Data.SEP + "sfx" + Data.SEP + getName() + ".wav"));
			clip = (Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
			clip.open(stream);
	        controller = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		}catch(Exception e){	//catch
			
			e.printStackTrace();
			return false;
			
		}	//close catch
		
		return true;
		
	}	//close method init
	
	public String getName(){	//method getName
		
		return name;
		
	}	//close method getName
	
	public void setName(String name){	//method setName
		
		this.name = name;
		
	}	//close method setName
	
	public int getVolume(){	//method getVolume
		
		return volume;
		
	}	//close method getVolume
	
	public void setVolume(int volume){	//method setVolume

		float newVol =
			((float)(volume * masterVolume) / 10000) * (controller.getMaximum() - controller.getMinimum()) + controller.getMinimum();
		
		if(newVol >= controller.getMinimum() && newVol <= controller.getMaximum()){	//if
			
			this.volume = volume;
			controller.setValue(newVol);
			
		}else if(newVol < controller.getMinimum()){	//else
			
			this.volume = 0;
			controller.setValue(controller.getMinimum());
			
		}else{	//else
			
			this.volume = 100;
			controller.setValue(controller.getMaximum());
		
		}	//close else
		
	}	//close method setVolume
	
	public static int getMasterVolume(){	//method getVolume
		
		return masterVolume;
		
	}	//close method getVolume
	
	public static void setMasterVolume(int masterVolume){	//method setVolume
		
		if(masterVolume <= 100 && masterVolume >= 0) Sound.masterVolume = masterVolume;
		else if(masterVolume < 0) Sound.masterVolume = 0;
		else Sound.masterVolume = 100;
		Sound.masterVolumeStorage = Sound.masterVolume;
		
	}	//close method setVolume
	
	public static boolean isMute(){	//method isMute
		
		return masterVolume == 0 && masterVolume != masterVolumeStorage;
		
	}	//close method isMute
	
	public static void mute(){	//method mute
		
		masterVolume = 0;
		
	}	//close method mute
	
	public static void unmute(){	//method unmute
		
		masterVolume = masterVolumeStorage;
		
	}	//close method unmute
	
	public int getLoops(){	//method getLoops
		
		return loops;
		
	}	//close method getLoops
	
	public void setLoops(int loops){	//method setLoops
		
		this.loops = loops;
		
	}	//close method setLoops
	
}	//close class Sound