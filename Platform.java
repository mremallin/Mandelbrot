/****************************************************************
 * PURPOSE: To serve as a platform for simple Graphics projects
 * DATE:	2005 05 13 (Adapted from Edmund Lee 2004 05 08)
 ****************************************************************/

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.*;

/*
 * TO DO: FIX Julia sets.
 */
public class Platform extends JFrame
{
	int width;
	int height;
	
	public static final long serialVersionUID = 1;

	public Platform() {
		super("Java Mandelbrot by Mike Mallin '08");

		// add some content
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		
		width = d.width; // for getting screen resolution.
		height = d.height;
		
		getContentPane().add(new Content(width,height));

		// setup the frame 	
		setBounds(0,0,width,height + 23);
		setVisible(true);
	}

	public static void main( String[] args ) {
		Platform platform = new Platform();
		platform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


// inner Content class
class Content extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
	public static final long serialVersionUID = 8092008;
	final int WIDTH;
	final int HEIGHT;
	String iters = JOptionPane.showInputDialog("Enter the number of iterations:");
	int iter;
	String pnum;
	int FRAMES = 3200;
	Palette palette;
	Mandelbrot mandelbrot;
	Julia j;
	double time, percent;
	int num, f;
	double factor = 0.1;
	ComplexNumber target = new ComplexNumber(-0.05438854883194559, 0.8249678954084787);
	ComplexNumber juliaTarget;
	double dx = target.getReal() / FRAMES;
	double dy = target.getImaginary() / FRAMES;
	boolean location = false, movie = false, hasMoved = false, hasBoxMoved = false,
			orbits = false, julia = false, inFormationMovie = false, isBoxMode = false;
	String name = "";
	String onScreen = "";
	BufferedImage offscreenImage;
	ArrayList<Point> last;
	MouseEvent firstpoint, lastpoint, previousdrag;
	
	public Content(int w, int h) {
		super();
		if(iters == null || iters.equals("") || Character.isLetter(iters.charAt(0)))
		{
			JOptionPane.showMessageDialog(this, "Fine, don't choose! I'll just choose 256 for you."
					, "You didn't give me any iterations!", 2, null);
			iters = "256";
		}
		
		iter = Integer.parseInt(iters);
		WIDTH = w;
		HEIGHT = h;
		
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		addMouseMotionListener(this);
		
		mandelbrot = new Mandelbrot(WIDTH, HEIGHT);
		mandelbrot.setMovieParameters(FRAMES, target);
		mandelbrot.setI(iter);
		
		target = new ComplexNumber(-0.4, 0.6);
		j = new Julia(WIDTH, HEIGHT, target);
		j.setI(iter);
		
		pnum = JOptionPane.showInputDialog("Which Palette? (0-8)");
		if(pnum == null || pnum.equals("") || Character.isLetter(pnum.charAt(0)))
		{
			JOptionPane.showMessageDialog(this, "Fine, don't choose them! I'll just choose random colours for you."
					, "You didn't choose your colors!", 2, null);
			pnum = "2";
		}
		if(pnum.equals("2"))
		{
			String seed = JOptionPane.showInputDialog("Any specific seed?");
			if(seed == null || seed.equals("") || Character.isLetter(seed.charAt(0)))
					palette = new Palette(Integer.parseInt(pnum), iter);
				else
					palette = new Palette(iter, seed);
		}
		else
			palette = new Palette(Integer.parseInt(pnum), iter);
		num = 0;
	}
	
	public void paintComponent ( Graphics g ) {
		super.paintComponent( g );
		//drawIt(g);
		if(offscreenImage == null)
		{
			offscreenImage = (BufferedImage) createImage(WIDTH, HEIGHT);
			if(!julia)
				drawIt(offscreenImage.getGraphics());
			else
				drawJulia(offscreenImage.getGraphics());
		}
		g.drawImage(offscreenImage, 0, 0, Color.WHITE, this);
	}
	
	private void drawIt(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		long start = System.currentTimeMillis();
		//mandelbrot.setBounds(bounds);
		double percent = 0.0;
		for(int r = 0; r < HEIGHT; r++)
		{
			for(int c = 0; c < WIDTH; c++)
			{
				mandelbrot.setC(c, r);
				//System.out.println(mandelbrot.getC());
				if(mandelbrot.isInSet())
						g2.setColor(Color.black);
					else
						g2.setColor(palette.getColor(mandelbrot.getIterations()));
				g2.drawLine(c, r, c, r);
			}
			percent = r*1.0/HEIGHT;
			//System.out.print("Percent Done:" + percent * 100 + "% \r");	
		}
		if(!inFormationMovie)
		{
			g2.setColor(Color.white);
			g2.drawString(onScreen, 0, HEIGHT - 5);
			//g2.drawString(name, WIDTH - 50, HEIGHT - 5);
			time = (System.currentTimeMillis() - start) / 1000.0;
			System.out.println("Time for this frame is: " + time + "s.");
			//System.out.println("Done!");
		}
		this.getGraphics().drawImage(offscreenImage, 0, 0, Color.WHITE, this);
	}
	
	private void drawJulia(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		long start = System.currentTimeMillis();
		//j.setBounds(bounds);
		double percent = 0.0;	
		for(int r = 0; r < HEIGHT; r++)
		{
			for(int c = 0; c < WIDTH; c++)
			{
				j.iterate(c, r);
				//System.out.println(mandelbrot.getC());
				if(j.isInSet())
					g2.setColor(Color.BLACK);
					else
					g2.setColor(palette.getColor(j.getIterations()));
				g2.drawLine(c, r, c, r);
			}
			percent = r*1.0/HEIGHT;
			System.out.print("Percent Done:" + percent * 100 + "% \r");	
		}
		if(!inFormationMovie)
		{
			g2.setColor(Color.white);
			g2.drawString(onScreen, 0, HEIGHT - 5);
			//g2.drawString(name, WIDTH - 50, HEIGHT - 5);
			time = (System.currentTimeMillis() - start) / 1000.0;
			System.out.println("Time for this frame is: " + time + "s.");
			//System.out.println("Done!");
		}
		this.getGraphics().drawImage(offscreenImage, 0, 0, Color.WHITE, this);
	}
	
	private void makeMovie()
	{
		for(f = 0; f <= FRAMES; f++)
		{
			mandelbrot.modifyMovieBounds(dx, dy, f);
			//drawIt(this.getGraphics());
			screenCapture();
			System.out.println("Screen Captured!");
			System.out.println(f*1.0/FRAMES * 100 + "%");
		}
	}
	
	private void makeFormationMovie()
	{
		inFormationMovie = !inFormationMovie;
		for(int i = 1; i < 64; i++) //64 iteration cap for speed.
		{
			
			onScreen = "Iterations: " + i;
			if (!julia)
			{
				mandelbrot.setI(i);
				drawIt(offscreenImage.getGraphics());
			}
			else
			{
				j.setI(i);
				drawJulia(offscreenImage.getGraphics());
			}
			Graphics2D g2 = (Graphics2D) this.getGraphics();
			g2.drawImage(offscreenImage, 0, 0, Color.WHITE, this);
			g2.setColor(Color.white);
			g2.drawString(onScreen, 0, 10);
		}
		inFormationMovie = !inFormationMovie;
	}
	
	private void catchup(int frame)
	{
		for(int i = 1; i <= frame; i++)
			mandelbrot.modifyBounds(target.getReal(), target.getImaginary(), factor);
	}	
	
	public void mouseClicked(MouseEvent e)
	{
		if(!isBoxMode)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{	
				if(!julia)
				{
					//Mouse Button 1
					//Send mouse coodinates
					mandelbrot.modifyBounds(e.getX(), e.getY(), factor);
					//System.out.print("Mouse Received! - Zooming in to: ");
					//Re-calculate the onScreen values
					onScreen = (new ComplexNumber(Fractal.getReal(e.getX())
							, Fractal.getImaginary(e.getY())).toString());
					/*System.out.println(new ComplexNumber(mandelbrot.getReal(e.getX())
					, mandelbrot.getImaginary(e.getY())).toString());*/
					//Redraw the set.
					drawIt(offscreenImage.getGraphics());
				}
				else
				{
					j.modifyBounds(e.getX(), e.getY(), factor);
					onScreen = (new ComplexNumber(Fractal.getReal(e.getX())
							, Fractal.getImaginary(e.getY())).toString());
					drawJulia(offscreenImage.getGraphics());
				}
				
			}
			//Mouse Button 3
			if(e.getButton() == MouseEvent.BUTTON3)
			{	//Mouse Button 1
				//Send mouse coodinates
				mandelbrot.modifyBounds(e.getX(), e.getY(), factor*100);
				System.out.print("Mouse Received! - Zooming in to: ");
				//Re-calculate the onScreen values
				onScreen = (new ComplexNumber(Fractal.getReal(e.getX())
						, Fractal.getImaginary(e.getY())).toString());
				System.out.println(new ComplexNumber(Fractal.getReal(e.getX())
						, Fractal.getImaginary(e.getY())).toString());
				//Redraw the set.
				drawIt(offscreenImage.getGraphics());
			}
			hasBoxMoved = false;
		}
	}
	
	public void keyTyped(KeyEvent k)
	{
		System.out.println(k);
		//enable box mode
		if(k.getKeyChar() == 'b')
			isBoxMode = !isBoxMode;
		//center on target
		if(k.getKeyChar() == 'c')
			mandelbrot.modifyBounds(mandelbrot.getRealInv(target.getReal())
					, mandelbrot.getImaginaryInv(target.getImaginary()), 1);
		//change zoom factor
		if(k.getKeyChar() == 'f')
		{
			String newFactor = JOptionPane.showInputDialog("Enter the zoom factor:");
			if(newFactor == null)
				return;
			factor = (1.0/Integer.parseInt(newFactor));
		}
		//reset iteration and palette
		if(k.getKeyChar() == 'i')
		{
			iters = JOptionPane.showInputDialog("Enter the number of iterations:");
			if(iters == null)
				return;
			mandelbrot.setI(Integer.parseInt(iters));
			j.setI(Integer.parseInt(iters));
			palette = new Palette(Integer.parseInt(pnum), Integer.parseInt(iters));
			
			if (!julia)
				drawIt(offscreenImage.getGraphics());
			else
				drawJulia(offscreenImage.getGraphics());
		}
		//switch to julia
		if(k.getKeyChar() == 'j')
		{
			julia = !julia;
			if (!julia)
				drawIt(offscreenImage.getGraphics());
			else
				drawJulia(offscreenImage.getGraphics());
		}
		//list the current centered point.
		if(k.getKeyChar() == 'l')
			JOptionPane.showMessageDialog(this, "The current point is: " + new ComplexNumber(Fractal.getReal(WIDTH/2)
					, Fractal.getImaginary(HEIGHT/2)), "Current Centered Point", 1, null);
		//proper movie creation.
		if(k.getKeyChar() == 'm')
		{
			movie = true;
			target = ComplexNumber.parseComplexNumber(JOptionPane.showInputDialog("What is your desired target?"));
			if(target == null)
				return;
			String start1 = JOptionPane.showInputDialog(("What is the starting frame?"));
			if (start1 == null)
				return;
			int start = Integer.parseInt(start1);
			catchup(start);
			num = start;
			FRAMES = Integer.parseInt(JOptionPane.showInputDialog(("How many frames?")));
			makeMovie();
		}
		//Mandelbrot formation.
		if(k.getKeyChar() == 'n')
			makeFormationMovie();
		//orbits
		if(k.getKeyChar() == 'o')
			orbits = !orbits;
		//palette
		if(k.getKeyChar() == 'p')
		{
			pnum = JOptionPane.showInputDialog("Which Palette? (0-8)");
			if(pnum == null || pnum.equals("") || Character.isLetter(pnum.charAt(0)) || pnum.length() >= 2 || pnum.length() < 1)
				return;
			if(pnum.equals("2"))
			{
				String seed = JOptionPane.showInputDialog("Any specific seed?");
				if(seed == null || seed.equals("") || Character.isLetter(seed.charAt(0)))
						palette = new Palette(Integer.parseInt(pnum), iter);
					else
						palette = new Palette(iter, seed);
			}
			else
				palette = new Palette(Integer.parseInt(pnum), iter);
			palette = new Palette(Integer.parseInt(pnum), Integer.parseInt(iters));
			drawIt(offscreenImage.getGraphics());
		}
		//reset
		if(k.getKeyChar() == 'r')
		{
			if(!julia)
			{
				mandelbrot = new Mandelbrot(WIDTH, HEIGHT);
				drawIt(offscreenImage.getGraphics());
			}
			else
			{

			}
		}
		//screen capture
		if(k.getKeyChar() == 's')
			screenCapture();
		//set target
		if(k.getKeyChar() == 't')
			target = ComplexNumber.parseComplexNumber(JOptionPane.showInputDialog("What is your desired target?"));
		//zoom on current point
		if(k.getKeyChar() == 'z')
			mandelbrot.modifyBounds(mandelbrot.getRealInv(target.getReal())
					, mandelbrot.getImaginaryInv(target.getImaginary()), 0.1);
	}
	
	public void mouseMoved(MouseEvent e)
	{
		if(orbits)
		{
			ArrayList<Point> p = mandelbrot.getOrbit(e.getX(), e.getY()); 
			//Get me a canvas to draw on.
			Graphics2D g2 = (Graphics2D) this.getGraphics();
			g2.setXORMode(Color.white);
			//Let's start to draw
			if(hasMoved)
			{
				//Nuke the first lines
				for(int i = 0; i < last.size()-1; i++)
					g2.drawLine(((int)last.get(i).getX()), (int)last.get(i).getY()
							, (int)last.get(i+1).getX(), (int)last.get(i+1).getY());
			}
				//Add the new lines
				for(int i = 0; i < p.size()-1; i++)
					g2.drawLine((int)p.get(i).getX(), (int)p.get(i).getY()
							, (int)p.get(i+1).getX(), (int)p.get(i+1).getY());
			last = p;
			hasMoved = true;
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(isBoxMode)
			firstpoint = e;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(isBoxMode)
		{
			lastpoint = e;
			//How this WORKS: Takes the centre point of the box drawn. So that I don't
			//have to worry about aspect ratios and the like.
			//Mouse Button 1
			//Send mouse coodinates
			mandelbrot.modifyBounds(firstpoint.getX() + ((lastpoint.getX() - firstpoint.getX())/2)
					, firstpoint.getY() + ((lastpoint.getY() - firstpoint.getY())/2), factor);
			//System.out.print("Mouse Received! - Zooming in to: ");
			//Re-calculate the onScreen values
			onScreen = (new ComplexNumber(Fractal.getReal(firstpoint.getX() + ((lastpoint.getX() - firstpoint.getX())/2))
					, Fractal.getImaginary(firstpoint.getY() + ((lastpoint.getY() - firstpoint.getY())/2))).toString());
			/*System.out.println(new ComplexNumber(mandelbrot.getReal(e.getX())
			, mandelbrot.getImaginary(e.getY())).toString());*/
			//Redraw the set.
			drawIt(offscreenImage.getGraphics());
			
			hasBoxMoved = false;
			firstpoint = null;
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(isBoxMode)
		{
			//Drawing Order: Top, Right, Bottom, Left.
			Graphics2D g2 = (Graphics2D) this.getGraphics();
			g2.setXORMode(Color.white);
			//Let's start to draw
			if(hasBoxMoved)
			{
				//nuke the old lines.
				g2.drawLine(firstpoint.getX(), firstpoint.getY(), previousdrag.getX(), firstpoint.getY());
				g2.drawLine(previousdrag.getX(), firstpoint.getY(), previousdrag.getX(), previousdrag.getY());
				g2.drawLine(previousdrag.getX(), previousdrag.getY(), firstpoint.getX(), previousdrag.getY());
				g2.drawLine(firstpoint.getX(), previousdrag.getY(), firstpoint.getX(), firstpoint.getY());
			}	
			//Add the new lines
			g2.drawLine(firstpoint.getX(), firstpoint.getY(), e.getX(), firstpoint.getY());
			g2.drawLine(e.getX(), firstpoint.getY(), e.getX(), e.getY());
			g2.drawLine(e.getX(), e.getY(), firstpoint.getX(), e.getY());
			g2.drawLine(firstpoint.getX(), e.getY(), firstpoint.getX(), firstpoint.getY());
			hasBoxMoved = true;
			previousdrag = e;
		}
	}
	//Blank methods. Unimplemented
	//MouseListener
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	//KeyListener
	public void keyPressed(KeyEvent k){}
	public void keyReleased(KeyEvent k){}
	
	public void screenCapture()	{ 
		num++;
		File file;
		if(!movie)
			file = new File(JOptionPane.showInputDialog("What do you want the file to be named", (new ComplexNumber(Fractal.getReal(WIDTH/2), Fractal.getImaginary(HEIGHT/2) ))) + ".png");
		else
			file = new File("Fractal_" + num + ".png");
		if(file.equals(JOptionPane.UNINITIALIZED_VALUE))
			return;
		   try{
			  javax.imageio.ImageIO.write(offscreenImage, "png", file);
		   }
		   catch(Exception e){
		   	 System.out.println(e);
		   }
		   this.getGraphics().drawImage(offscreenImage, 0, 0, this);
		   //offscreenImage.flush();
		}
}