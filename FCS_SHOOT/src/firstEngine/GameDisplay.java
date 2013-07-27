package firstEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GameDisplay{	//class GameDisplay

	private static int fps;
	private static int delta;
	public static int frameCap;
	private static Timer titleDelay = new Timer();
	public static int setFrameCap;

	public static void init(int frameCap){	//method init

		try{	//try
			
			Display.setDisplayMode(new DisplayMode(800, 600));
			loadIcons();
			Display.create();
				
		}catch(Exception e){	//catch
			
			e.printStackTrace();
			System.exit(0);
			
		}	//close catch
		
		setFrameCap = frameCap;
		GameDisplay.frameCap = setFrameCap;
		initGL();
		
	}	//close method init
	
	public static void setFullscreen(boolean fullscreen){	//method setFullscreen
		
		if(fullscreen){	//if
			
			try{	//try
				
				Display.destroy();
				Display.setDisplayMode(Display.getDisplayMode());
				Display.setFullscreen(true);
				Display.create();
				initGL();
				
			}catch (LWJGLException e){	//catch
				
				e.printStackTrace();
				
			}	//close catch
			
		}else{	//else
			
			try{	//try
				
				Display.destroy();
				Display.setDisplayMode(new DisplayMode(800, 600));
				Display.setFullscreen(false);
				Display.create();
				initGL();
				
			}catch (LWJGLException e){	//catch
				
				e.printStackTrace();
				
			}	//close catch
			
		}	//close else
		
	}	//close method setFullscreen
	
	public static int getDelta(){	//method getDelta
		
		return delta;
		
	}	//close method getDelta
	
	public static void setDelta(){	//method setDelta
		
		delta = Timer.getDelta();
		
	}	//close method setDelta

	public static void update(){	//method update
		
		setDelta();
		Display.update();
		Display.sync(frameCap);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		if(Display.isCloseRequested()){
			System.exit(0);
		}
		
	}	//close method update
	
	public static void setTitle(String title){	//method setTitle
		
		Display.setTitle(title);
		
	}	//close method setTitle
	
	public static void setDebugTitle(int polygonCount){	//method setDebugTitle
				
		String title = "FPS: " + fps +
				" | TME: " + Timer.getTime() +
				" | POL: " + polygonCount +
				" | MSE: " + "x" + Mouse.getX() + "/" + "y" + (Display.getHeight() - Mouse.getY())
				;
		fps++;
		if(titleDelay.getDelay((long)(1000 * Timer.getGameSpeed()))){
			setTitle(title);
			fps = 0;
		}
		
	}	//close method setDebugTitle
	
	public static void loadIcons() throws IOException{
		
		BufferedImage[] iconImages = new BufferedImage[3];
		ByteBuffer[] icons = new ByteBuffer[iconImages.length];
		
		for(int i = 0; i < iconImages.length; i++){
			BufferedImage image = ImageIO.read(new File(Data.DIR_PATH + "res" + Data.SEP + "gfx" + Data.SEP + "icon" + i + ".png"));
			byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
			int counter = 0;
			for (int j = 0; j < image.getHeight(); j++){
				for (int k = 0; k < image.getWidth(); k++){
					int colorSpace = image.getRGB(k, j);
					buffer[counter + 0] = (byte)((colorSpace << 8) >> 24);
					buffer[counter + 1] = (byte)((colorSpace << 16) >> 24);
					buffer[counter + 2] = (byte)((colorSpace << 24) >> 24);
					buffer[counter + 3] = (byte)(colorSpace >> 24);
					counter += 4;
				}
			}
			icons[i] = ByteBuffer.wrap(buffer);
		}

		Display.setIcon(icons);
		
	}

	private static void initGL(){	//method startGL
		
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}	//close method startGL
	
}	//close class GameDisplay