import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessor {
	
	private static BufferedImage image = null;
	
	private static File file = null;
	
        /**
        * takes the image directory as String input 
        *
        * @param  inputImagePath an String
        * @throws IOException unless the file is found
        */
	public static void inputImage(String inputImagePath) {
		try {
			file = new File(inputImagePath);
			image = ImageIO.read(file);
		}
		catch(IOException ex) {
			System.out.println("Failed to read image");
			System.out.println(ex);
		}
	}
	/**
         * resizes the image into new dimension
         * @param inputImagePath
         * @param outputImagePath
         * @param scaledWidth
         * @param scaledHeight
         * @throws IOException 
         */
	public static void resizeImage(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException {
		inputImage(inputImagePath);

		BufferedImage outputImage = new BufferedImage(scaledWidth,
			scaledHeight, image.getType());

		Graphics2D g = outputImage.createGraphics();
		g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();

		String formatName = outputImagePath.substring(outputImagePath.
			lastIndexOf(".") + 1);

		ImageIO.write(outputImage, formatName, new File(outputImagePath));
	}
        /**
         * converts a set of input images to a matrix
         * @param inputImagePath
         * @return
         * @throws IOException 
         */
	public static double[][] imageToMatrix(String[] inputImagePath) throws IOException {
		int numOfImage = inputImagePath.length;
		String[] processedImagePath = new String[numOfImage];
		for(int i=0; i<numOfImage; i++) {
			processedImagePath[i] = inputImagePath[i].substring(0, inputImagePath[i].lastIndexOf(".")) + "resized" +inputImagePath[i].substring(inputImagePath[i].lastIndexOf("."));
			resizeImage(inputImagePath[i], processedImagePath[i], 64, 64);
		}
		double[][] X = new double[64*64*3][numOfImage];
		for(int i=0; i<numOfImage; i++) {
			inputImage(processedImagePath[i]);
			int height = image.getHeight();
			int width = image.getWidth();
			int prod = height*width;
			int j = 0;
			for(int y=0; y<height; y++) {
				for(int x=0; x<width; x++) {
					int pixel = image.getRGB(x, y);
					X[j][i] = ((pixel>>16) & 0xff) / 255;
					X[prod+j][i] = ((pixel>>8) & 0xff) / 255;
					X[2*prod+j][i] = (pixel & 0xff) / 255;
					j++;
				}
			}
		}
		return X;
	}

}
