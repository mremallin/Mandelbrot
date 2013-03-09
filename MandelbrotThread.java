import java.util.concurrent.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

class MandelbrotThread implements Runnable
{
    private int screenWidth, screenHeight;
    private int iterations;
    private int startRow, numRows;
    private Mandelbrot mand;
    private BufferedImage canvas;
    private Palette palette;
    private CountDownLatch latch;

    public MandelbrotThread(int screenWidth,
                            int screenHeight,
                            int iterations,
                            int startRow,
                            int numRows,
                            BufferedImage canvas,
                            Palette p,
                            CountDownLatch l,
                            Mandelbrot m)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.iterations = iterations;
        this.startRow = startRow;
        this.numRows = numRows;
        this.canvas = canvas;
        this.palette = p;
        this.latch = l;
        this.mand = m;
    }

    public void run()
    {
        Graphics2D g2 = (Graphics2D)canvas.getGraphics();

        for (int r = startRow; r < (startRow + numRows); r++)
        {
            for (int c = 0; c < screenWidth; c++)
            {
                mand.setC(c, r);
                if (mand.isInSet())
                    g2.setColor(Color.black);
                else
                    g2.setColor(palette.getColor(mand.getIterations()));
                g2.drawLine(c, r-startRow, c, r-startRow);
            }
        }
        latch.countDown();
    }
}
