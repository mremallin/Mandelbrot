import java.awt.Color;
import java.util.*;

public class Palette
{
Color[] palette;
int iter;
int choice;
	public Palette(int c, int it) {
		//REMEMBER TO CHANGE THIS WHEN YOU CHANGE THE ITERATIONS in MANDELBROT.
		iter = it;
		palette = new Color[iter+1];
		choice = c;
		//palette[0] = Color.BLACK;
		// REPLACE THE FOLLOWING WITH A CUSTOM PALETTE
		//for (int i=1; i<256; i++) {
		//	palette[i]=new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
		//}
		//DONT FORGET HERE TOO
		switch(c)
		{
			case(0):
				for (int i=1; i<iter; i++)
				{
					palette[i]=new Color(i%256,i%256,i%256);
				}
			break;
			//Fire Red 32k
			case(1):
				for(int i = 0; i < iter/6; i++)
					palette[i] = new Color((i * 2) % 256, 0, 0);
				for(int i = (iter/6)+1; i < 2*(iter/6); i++)
					palette[i] = new Color((i * 2) % 256, (i ^ 2) % 256, 0);
				for(int i = 2*(iter/6)+1; i < 3*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, 0);
				for(int i = 3*(iter/6)+1; i < 4*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, (i ^ 4) % 256);
				for(int i = 4*(iter/6)+1; i < 5*(iter/6); i++)
					palette[i] = new Color(0, 0 , (i ^ 4) % 256);
				for(int i = 5*(iter/6)+1; i < 6*(iter/6); i++)
					palette[i] = new Color((i * 2) % 256, 0, (i ^ 4) % 256);
				break;
				//Random
			default:
			case(2):
				Random random = new Random();
				for (int i=1; i<iter; i++)
				{
					palette[i]=new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
				}
				break;
				//Emerald Green
			case(3):
				for (int i=1; i<iter; i++)
				{
					palette[i]=new Color((i) % 256,(i+60) % 256,(i + 5) % 256);
				}
				break;
				//Bluebomb 32k
			case(4):
				for(int i = 0; i < iter/6; i++)
					palette[i] = new Color(0, 0, (i * 2) % 256);
				for(int i = (iter/6)+1; i < 2*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, (i * 2) % 256);
				for(int i = 2*(iter/6)+1; i < 3*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, 0);
				for(int i = 3*(iter/6)+1; i < 4*(iter/6); i++)
					palette[i] = new Color((i ^ 4) % 256, (i ^ 2) % 256, 0);
				for(int i = 4*(iter/6)+1; i < 5*(iter/6); i++)
					palette[i] = new Color((i ^ 4) % 256, 0, 0);
				for(int i = 5*(iter/6)+1; i < 6*(iter/6); i++)
					palette[i] = new Color((i ^ 4) % 256, 0, (i * 2) % 256);
				break;
				//Lichen 256
			case(5):
				for(int i = 0; i < iter/6; i++)
					palette[i] = new Color((i * 2) % 256, 0, 0);
				for(int i = (iter/6)+1; i < 2*(iter/6); i++)
					palette[i] = new Color((i * 2) % 256, (i ^ 2) % 256, 0);
				for(int i = 2*(iter/6)+1; i < 3*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, 0);
				for(int i = 3*(iter/6)+1; i < 4*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 2) % 256, (i ^ 4) % 256);
				for(int i = 4*(iter/6)+1; i < 5*(iter/6); i++)
					palette[i] = new Color(0, 0 , (i ^ 4) % 256);
				for(int i = 5*(iter/6)+1; i < 6*(iter/6); i++)
					palette[i] = new Color((i * 2) % 256, 0, (i ^ 4) % 256);
				break;
				//Redwood 32k
			case(6):
				for(int i = 0; i < iter/6; i++)
					palette[i] = new Color((i * 4) % 256, 0, 0);
				for(int i = (iter/6)+1; i < 2*(iter/6); i++)
					palette[i] = new Color((i * 4) % 256, 0, (i ^ 2) % 256);
				for(int i = 2*(iter/6)+1; i < 3*(iter/6); i++)
					palette[i] = new Color(0, 0, (i ^ 2) % 256);
				for(int i = 3*(iter/6)+1; i < 4*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 4) % 256, (i ^ 2) % 256);
				for(int i = 4*(iter/6)+1; i < 5*(iter/6); i++)
					palette[i] = new Color(0, (i ^ 4) % 256, 0);
				for(int i = 5*(iter/6)+1; i < 6*(iter/6); i++)
					palette[i] = new Color((i * 2) % 256, (i ^ 4) % 256, 0);
				break;
			//Cool Blue.
			case(7):
				for(int i = 0; i < iter; i++)
					palette[i] = new Color((10 + i) % 256, (50 + i) % 256, (150 + 2*i) % 256 );
				break;
			//Dark Side of the Moon!
			case(8):
				for(int i = 0; i < iter/8; i++)
					palette[i] = Color.black;
				for(int i = (iter/7)+1; i < 2*(iter/7); i++)
					palette[i] = new Color(234, 64, 89);
				for(int i = 2*(iter/7)+1; i < 3*(iter/7); i++)
					palette[i] = new Color(235, 159, 67);
				for(int i = 3*(iter/7)+1; i < 4*(iter/7); i++)
					palette[i] = new Color(250, 220, 52);
				for(int i = 4*(iter/7)+1; i < 5*(iter/7); i++)
					palette[i] = new Color(138, 182, 78);
				for(int i = 5*(iter/7)+1; i < 6*(iter/7); i++)
					palette[i] = new Color(104, 162, 230);
				for(int i = 6*(iter/7)+1; i < 7*(iter/7); i++)
					palette[i] = new Color(136, 118, 134);
				break;
				
		}
				
	}	
	
	public Palette(int iters, String seed)
	{
		iter = iters;
		palette = new Color[iter+1];
		
		Random random = new Random(Integer.parseInt(seed));
		for (int i=1; i<iter; i++)
		{
			palette[i]=new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
		}
	}
	
	// accessor method...
	public Color getColor(int i) {
		return palette[i];
	}
	
	public int getChoice()
	{
		return choice;
	}


}