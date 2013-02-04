public class Fractal
{
	private static double leftBound, rightBound, bottomBound, topBound;
	private static double domain, range;
	protected static int screenHeight, screenWidth;
	
	public Fractal(int w, int h)
	{
		screenWidth = w;
		screenHeight = h;
	}
	
	public void setBounds(double[] b)
	{
		//sets xmin, xmax, ymin, ymax
		
		leftBound = b[0];
		rightBound = b[1];
		bottomBound = b[2];
		topBound = b[3];
		
		domain = rightBound - leftBound;
		range = topBound - bottomBound;
	}
	
	public static double getReal(int column)
	{
		return domain * column / screenWidth + leftBound;
	}
	
	public static double getImaginary(int row)
	{
		return topBound - (range * row / screenHeight);
	}
	
	public int getRealInv(double real)
	{
		return (int)(((real - leftBound) * screenWidth) / domain);
	}
	
	public int getImaginaryInv(double imag)
	{
		return -(int)(((imag - topBound) * screenHeight) / range);
	}
}