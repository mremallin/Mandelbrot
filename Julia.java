public class Julia extends Fractal
{
	private static int iterations, ITERATIONS;
	private ComplexNumber currC, oldC;
	private double[] bounds;
	private double deltaX, deltaY;
	
	public Julia(int w, int h, ComplexNumber c1)
	{
		super(w, h);
		oldC = c1;
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
		else
		{
			double[] b = {-2.75, 0.75, -1.5, 1.5};
			bounds = b;
			deltaX = 1.5;
			deltaY = 1.5;
		}
		
		bounds[0] -= oldC.getReal();
		bounds[1] -= oldC.getReal();
		
		setBounds(bounds);
	}
	
	public void setI(int i)
	{
		ITERATIONS = i;
	}

	public boolean isInSet()
	{
		return iterations == ITERATIONS;
	}
	
	public void setJC(ComplexNumber c)
	{
		this.oldC = c;
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
		//System.out.println("DeltaX = " + deltaX);
		//System.out.println("DeltaY = " + deltaX);
		setBounds(bounds);
	}
	
	public void iterate(int col, int row)
	{
		//System.out.println("Col: " + col + " Row: " + row);
		//double z1 = 0, z2 = 0, z21 = getReal(col), z22 = getImaginary(col), c1 = c.getReal(), c2 = c.getImaginary();
		ComplexNumber z = new ComplexNumber(getReal(col), getImaginary(row));
		iterations = 0;
		currC = new ComplexNumber(oldC.getReal(), oldC.getImaginary());
		while(iterations<ITERATIONS && /*(z1 * z1 + z2 * z2)*/ z.magnitudeSquared() < 4)
		{
			//z<-z^2 + c
			//Old and busted
			z = currC.add(z.square());
			//New hotness
			/*z21 = (z1 * z1 - z2 * z2);
			z22 = (2 * z1 * z2);
			z1 = c1 + z21;
			z2 = c2 + z22;*/
			iterations++;
		}
		//System.out.println(iterations);
	}
	
	public int getIterations()
	{
		return iterations;
	}
}