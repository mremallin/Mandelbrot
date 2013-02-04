/**
 * The ComplexNumber Class
 * @author Mike Mallin <br>
 * Last Modified: May 9, 2007
 */

public class ComplexNumber
{
	private double real, imaginary;
	
	/**
	 * This creates a new complex number with the value 0+0i
	 */
	public ComplexNumber()
	{
		initialize(0,0);
	}
	
	/**
	 * This creates a new complex number with the value r+0i
	 * @param double r the real value of the complex number
	 */
	public ComplexNumber(double r)
	{
		initialize(r,0);
	}
	
	/**
	 * This creates a new complex number with the value r+ii
	 * @param double r the real value of the complex number
	 * @param double i the imaginary value of the complex number
	 */
	public ComplexNumber(double r, double i)
	{
		initialize(r,i);
	}
	
	//set the complex number values
	private void initialize(double r, double i)
	{
		real = r;
		imaginary = i;
	}
	/**
	 * This adds two complex numbers together
	 * @param other - another complex number
	 * @return ComplexNumber - the added complex number
	 */
	public ComplexNumber add(ComplexNumber other)
	{
		return new ComplexNumber(real + other.getReal() , imaginary + other.getImaginary());
	}
	
	/**
	 * This subtracts two complex numbers
	 * @param other - another complex number
	 * @return ComplexNumber - the subtracted complex number
	 */
	public ComplexNumber subtract(ComplexNumber other)
	{
		return new ComplexNumber(getReal() - other.getReal() , getImaginary() - other.getImaginary());
	}
	
	/**
	 * This multiplies two complex numbers together
	 * @param other - another complex number
	 * @return ComplexNumber - the resulting complex number
	 */
	public ComplexNumber multiply(ComplexNumber other)
	{
		double t1 = (real * other.getReal()) - (imaginary * other.getImaginary());
		double t2 = (real * other.getImaginary()) + (imaginary * other.getReal());
		return new ComplexNumber(t1, t2);
	}
	
	/**
	 * This divides a complex number by another
	 * @param other - the divisor complex number
	 * @return ComplexNumber -  the result of the division
	 */
	public ComplexNumber divide(ComplexNumber other)
	{
		double divisor = (other.getReal() * other.getReal())
						+ (other.getImaginary() * other.getImaginary());
		double top1 = (real * other.getReal())
						+ (imaginary * other.getImaginary());
		double top2 = (getImaginary() * other.getReal())
						- (getReal() * other.getImaginary());
		return (new ComplexNumber((top1 / divisor) , (top2 / divisor)));
	}
	
	/**
	 * This gives you the square of the ComplexNumber
	 * @return ComplexNumber - the square
	 */
	public ComplexNumber square()
	{
		return new ComplexNumber( real * real
				- imaginary * imaginary
				, 2.0 * real * imaginary );
	}
	
	/**
	 * This returns the position of z to the origin of the complex plane
	 * @return double - the magnitude of the ComplexNumber
	 */
	public double magnitude()
	{
		return Math.sqrt(magnitudeSquared());
	}
	
	/**
	 * This returns the magnitude squared
	 * @return double - the squared magnitude
	 */
	public double magnitudeSquared()
	{
		return real * real + imaginary * imaginary;
	}
	
	/**
	 * This gives you the conjugate of the ComplexNumber
	 * @return ComplexNumber - the conjugate
	 */
	public ComplexNumber conjugate()
	{
		return new ComplexNumber(getReal(), -getImaginary());
	}
	
	/**
	 * Grabs the real value of a complex number
	 * @return the real value of the complex number
	 */
	public double getReal()
	{
		return real;
	}
	
	/**
	 * Grabs the imaginary value of a complex number
	 * @return the imaginary value of the complex number
	 */
	public double getImaginary()
	{
		return imaginary;
	}
	
	public static ComplexNumber parseComplexNumber(String n)
	{
		String real = "", imag = "";
		int i = 1;
		
		while(i < n.length())
		{
			if(n.charAt(i) == '-' || n.charAt(i) == '+')
			{
				real = n.substring(0, i);
				imag = n.substring(i+1, n.length()-1);
			}
			i++;
		}
		
		return new ComplexNumber(Double.parseDouble(real), Double.parseDouble(imag));
	}
	
	/**
	 * The toString method
	 */
	public String toString()
	{
		if(getImaginary() < 0)
			return (getReal() + "" + getImaginary() +"i");
		return (getReal() + "+" + getImaginary() +"i");
	}
}