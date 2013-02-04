import java.util.ArrayList;
import java.awt.Point;

public class Mandelbrot extends Fractal
{
	protected ComplexNumber z, c, target;
	protected static int ITERATIONS = 32768;
	protected int iterations;
	protected double magnitude;
	//Widescreen 16:10 bounds
	//protected static double[] bounds = {-2.75, 1.25, -1.333, 1.333};
	//protected static double deltaX = 2;
	//protected static double deltaY = 1.333;
	//Widescreen 16:9 bounds
	//protected double[] bounds = {-2.75, 1.25, -1.15, 1.15};
	//protected static double deltaX = 2;
	//protected static double deltaY = 1.15;
	//Square 4:3 bounds
	//protected double[] bounds = {-2.75, 1.25, -1.5, 1.5};
	//protected static double deltaX = 2;
	//protected static double deltaY = 1.5;
	//1:1 bounds
	//protected double[] bounds = {-2.25, 0.75, -1.5, 1.5};
	//protected static double deltaX = 1.5;
	//protected static double deltaY = 1.5;
	protected static double[] bounds;
	protected static double deltaX, deltaY;
	protected double moveX, moveY, zoom;
	protected int FRAMES;
	protected double targetDelta = 1.6492674416640032E-14 / FRAMES;
	
	public Mandelbrot(int w, int h)
	{
		super(w,h);
		double ratio = w*1.0/h;
		System.out.println("Ratio = " + ratio);
		if(ratio == 1.6)
		{
			double[] b = {-2.79, 1.29, -1.333, 1.333};
			bounds = b;
			deltaX = 2.04;
			deltaY = 1.333;
		}
		else if (ratio == 1.777777777777777)
		{
			double[] b = {-2.75, 1.25, -1.15, 1.15};
			bounds = b;
			deltaX = 2;
			deltaY = 1.15;
		}
		else if(ratio == 1.3333333333333333)
		{
			double[] b = {-2.75, 1.25, -1.5, 1.5};
			bounds = b;
			deltaX = 2;
			deltaY = 1.5;
		}
		else if(ratio == 1.2727272727272727)
		{
			double[] b = {-2.4, 0.9, -1.42, 1.18};
			bounds = b;
			deltaX = 1.65;
			deltaY = 1.3;
		}
		else if(ratio == 1.25)
		{
			double[] b = {-2.5, 1, -1.4, 1.4};
			bounds = b;
			deltaX = 1.75;
			deltaY = 1.4;
		}
		else if(ratio == 0.5) //iPhone - need to calc properly...
		{
			double[] b = {-2.5, 1, -1.4, 1.4};
			bounds = b;
			deltaX = 1.75;
			deltaY = 1.4;
		}
		else
		{
			double[] b = {-2.75, 0.75, -1.5, 1.5};
			bounds = b;
			deltaX = 1.5;
			deltaY = 1.5;
		}
		
		setBounds(bounds);
		
	}
	
	public void setMovieParameters(int f, ComplexNumber t)
	{
		FRAMES = f;
		target = t;
	}
	public void setC(int column, int row)
	{
		magnitude = 0;
		double z1 = 0, z2 = 0,z21 = 0 ,z22 = 0, c1 = getReal(column), c2 = getImaginary(row);
		//long z1 = 0, z2 = 0,z21 = 0 ,z22 = 0, c1 = toFP(getReal(column)), c2 = toFP(getImaginary(row));
		//ComplexNumber c = new ComplexNumber(getReal(column), getImaginary(row));
		//ComplexNumber z = new ComplexNumber();
		iterations = 0;
		
		while(iterations<ITERATIONS && (z1 * z1 + z2 * z2) /*z.magnitudeSquared()*/ < 4)
		{
			//z<-z^2 + c
			//Old and busted
			//z = c.add(z.square());
			//Newer hotness
			z21 = (z1 * z1 - z2 * z2);
			z22 = (2 * z1 * z2);
			z1 = c1 + z21;
			z2 = c2 + z22;
			//Newest Hotness - Integer Maths.
			/*z21 = ((z1 * z1) - (z2 * z2));
			z22 = (0x10000 * z1 * z2);
			z1 = c1 + z21;
			z2 = c2 + z22;*/
			iterations++;
		}
		magnitude = (z1 * z1 + z2 * z2);
		//System.out.println(magnitude);
		c = new ComplexNumber(z1, z2);
	}
	
	static long toFP(double FP)
	{
		return (long)(FP * 0x10000);
	}
	
	public void modifyBounds(int x, int y, double fact)
	{
		deltaX *= fact;
		deltaY *= fact;
		bounds[0] = getReal(x) - deltaX;
		bounds[1] = getReal(x) + deltaX;
		bounds[2] = getImaginary(y) - deltaY;
		bounds[3] = getImaginary(y) + deltaY;
		//System.out.println("DeltaX = " + deltaX);
		//System.out.println("DeltaY = " + deltaX);
		setBounds(bounds);
	}
	
	public void modifyBounds(double x, double y, double fact)
	{
		deltaX *= fact;
		deltaY *= fact;
		bounds[0] = x - deltaX;
		bounds[1] = x + deltaX;
		bounds[2] = y - deltaY;
		bounds[3] = y + deltaY;
		System.out.println("DeltaX = " + deltaX);
		System.out.println("DeltaY = " + deltaX);
		setBounds(bounds);
	}
	
	//Takes the destination x / FRAMES, destination y / FRAMES, frame number
	public void modifyMovieBounds(double dx, double dy, int i)
	{
		moveX = moveX(i, dx);
		moveY = moveY(i, dy);
		double zoomX = zoomX(i);
		double zoomY = zoomY(i);
		System.out.println("ZoomX = " + zoomX);
		System.out.println("ZoomY = " + zoomY);
		//System.out.println(moveY);
		
		//delta *= 0.6;
		bounds[0] = moveX - zoomX;
		bounds[1] = moveX + zoomX;
		bounds[2] = moveY - zoomY;
		bounds[3] = moveY + zoomY;
		setBounds(bounds);
	}
	
	public ArrayList<Point> getOrbit(int col, int row)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		ComplexNumber c = new ComplexNumber(getReal(col), getImaginary(row));
		ComplexNumber z = new ComplexNumber();
		
		//generate my orbits
		for(int i = 0; i < 512; i++)
		{
			z = c.add(z.square());
			//System.out.println(z);
			points.add(new Point(getRealInv(z.getReal()), getImaginaryInv(z.getImaginary())));
		}
		
		return points;
	}
	
	public double moveX(int i, double dx)
	{
		//no movement
		return target.getReal();
		//linear
		//return dx * i;
		//logarithmic
		//double C = (-0.75 - target.getReal())/Math.log10(FRAMES);
		//double log10k = (-0.75)/C;
		//return -C * (Math.log10(i) - log10k);
	}
	
	public double moveY(int i, double dy)
	{
		//no movement
		return target.getImaginary();
		//linear
		//return dy * i;
		//logarithmic
		//double C = target.getImaginary() / Math.log10(FRAMES);
		//return C * Math.log10(i);
	}
	
	public double zoomX(int i)
	{
		//no zoom
		//return 1.5;
		//linear
		deltaX *= 0.99;
		return deltaX;
		//exponential
		//double za = 1.6492674416640032E-14;
		//double zb = 3;
		//double logk = (za*Math.log10(FRAMES))/(zb-za);
		//double C = za/logk;
		//return C*(logk+Math.log10(FRAMES-i+1));
		//Logarithmic
		//double C = (1.5 - 1.6492674416640032E-14)/Math.log10(FRAMES);
		//double log10k = (1.5)/C;
		//return -C * (Math.log10(i) - log10k);
	}
	
	public double zoomY(int i)
	{
		deltaY *= 0.99;
		return deltaY;
	}
	
	public String getC()
	{
		return c.toString() + " iterations:" + getIterations();
	}
	
	public void setI(int I)
	{
		ITERATIONS = I;
	}
	
	public int getI()
	{
		return ITERATIONS;
	}
	
	public double getMagnitude()
	{
		return magnitude;
	}
	
	public boolean isInSet()
	{
		return iterations == ITERATIONS;
	}
	
	public int getIterations()
	{
		return iterations;
	}
}