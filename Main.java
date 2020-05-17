
import java.io.IOException;
public class Main {

	public static void main(String[] args) throws IOException {
		
		String[] data = new String[54];
		for(int i=0; i<54; i++)
			data[i] = "ricardo/test" + (i+1) + ".jpg";
		double[][] Y = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				.3, 0, 0, .95, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}};
		NeuralNetwork ricardoIdentifier = new NeuralNetwork(data, Y);
		ricardoIdentifier.optimize(1000, 0.01);
		
		
		////////////////////////////////////////////////////////////
		// Enter your image directory here
		// Example: test = {"parent_directory/Umaar.jpg"}
		////////////////////////////////////////////////////////////
		String[] test = {};
		double[][] result = ricardoIdentifier.predict(test);
		for(int i=0; i<result[0].length; i++) {
			if(result[0][i] > 0.9)
				System.out.println("All hail the king!");
			else if(result[0][i] < 0.1)
				System.out.println("You don't deserve to live >:(");
			else
				System.out.println("You are " + result[0][i]*100 + "% ricardo");
		}
    }

}
