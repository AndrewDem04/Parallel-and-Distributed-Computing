package Lab5_RGBtoGrayScaleParallel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RGBtoGrayScaleParallel {

	/*
		        	Size		1T    		2T    		4T    8T   
	1house.jpg  	1264KB  	253 ms  	174 ms  	157   145 ms
	2aerial.jpg 	1728KB  	539 ms  	387 ms  	405   381 ms
	3tiger.jpg  	2084KB  	1320 ms 	876 ms  	769   685 ms
	4food.jpg   	2644KB  	1148 ms 	918 ms   	954   717 ms
	5landscape.jpg 	2933KB  	847 ms  	632 ms   	710   461 ms
	6berries.jpg 	3998KB   	1032 ms 	876 ms   	643   729 ms
	7lake.jpg   	6252KB   	1375 ms 	1060 ms  	835   853 ms

	Notes:
	- In most cases, increasing the number of threads improves performance, but the improvement isn't always linear.
	- For larger images, using more threads seems to have a more noticeable impact.
	- The times don’t always decrease with more threads, which may be due to the image being too small.

	Explanation of the code:
	- The image is split into sections, each handled by a separate thread.
	- By having a horizontal splitting , 
	each thread process a range of columns, ensuring better memory locality.

*/


   public static void main(String args[]) {
		
		String fileNameR = null;
		String fileNameW = null;
		int numThreads = 0;
		
		if (args.length != 3) {
			System.out.println("Usage: java RGBtoGrayScale <file to read > <file to write> <number of threads>");
			System.exit(1);
		}
		fileNameR = args[0];
		fileNameW = args[1];
		numThreads = Integer.parseInt(args[2]);
				
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNameR));
		} catch (IOException e) {}

		long start = System.currentTimeMillis(); 
		
		double redCoefficient = 0.299;
		double greenCoefficient = 0.587;
		double blueCoefficient = 0.114;

		int chunkWidth = img.getWidth() / numThreads;

		Thread[] threads = new Thread[numThreads];
		
		for (int i = 0; i < numThreads; i++) {
    		// Calculate the start and end pixel columns for each thread to process (splitting the image horizontally)
			int from = i * chunkWidth;
    		int to = (i == numThreads - 1) ? img.getWidth() : from + chunkWidth;

    		threads[i] = new GrayScaleWorker(img, redCoefficient, greenCoefficient, blueCoefficient, from, to);
			threads[i].start();

		}
		
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	    long elapsedTimeMillis = System.currentTimeMillis()-start;
       
		try {
		  File file = new File(fileNameW);
		  ImageIO.write(img, "jpg", file);
		} catch (IOException e) {}
      
		System.out.println("Done...");
		System.out.println("time in ms = "+ elapsedTimeMillis);
   }

   static class GrayScaleWorker extends Thread {
	BufferedImage img;
    private double r, g, b;
    private int start, end;

    public GrayScaleWorker(BufferedImage img , double r, double g, double b, int start, int end) {
		this.img = img;
        this.r = r;
        this.g = g;
        this.b = b;
        this.start = start;
        this.end = end;
    }

    public void run() {
		for (int x = start; x < end; x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				//Retrieving contents of a pixel
				int pixel = img.getRGB(x,y);
				//Creating a Color object from pixel value
				Color color = new Color(pixel, true);
				//Retrieving the R G B values, 8 bits per r,g,b
				//Calculating GrayScale
				int red = (int) (color.getRed() * r);
				int green = (int) (color.getGreen() * g);
				int blue = (int) (color.getBlue() * b);
				//Creating new Color object
				color = new Color(red+green+blue, 
				                  red+green+blue, 
				                  red+green+blue);
				//Setting new Color object to the image
				img.setRGB(x, y, color.getRGB()); 
			}
		}
    }
  }
}
