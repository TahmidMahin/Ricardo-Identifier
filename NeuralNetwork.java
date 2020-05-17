import java.io.IOException;
import java.util.Random;

public class NeuralNetwork {
	private double[][] X;
	private double[][] Y;
	private double[][] W;
	private double[][] dW;
	private double b;
	private double db;
	private double m;
	/**
         * the constructor initializes w matrix with Gaussian distribution
         * @param X
         * @param Y 
         */
	public NeuralNetwork(double[][] X, double[][] Y) {
		this.X = X;
		this.Y = Y;
		Random rand = new Random();
		W = new double[X.length][1];
		for(int i=0; i<W.length; i++)
			for(int j=0; j<W[0].length; j++)
				W[i][j] = rand.nextGaussian();
		b = 0;
		m = X[0].length;
	}
	/**
         * the constructor initializes w matrix with positive Gaussian distribution
         * @param data
         * @param Y
         * @throws IOException 
         */
	public NeuralNetwork(String[] data, double[][] Y) throws IOException {
		X = ImageProcessor.imageToMatrix(data);
		this.Y = Y;
		Random rand = new Random();
		W = new double[X.length][1];
		for(int i=0; i<W.length; i++)
			for(int j=0; j<W[0].length; j++)
				W[i][j] = rand.nextGaussian();
		b = 0;
		m = X[0].length;
	}
	/**
         * calculates the sigmoid, prediction A matrix, dW, db 
         * @return the cost value
         */
	public double propagate() {
		LowCostNumpy np = new LowCostNumpy();
		double[][] A = np.sigmoid(np.add(np.dot(np.transpose(W), X), b));
		double cost = -np.sum(np.add(np.multiply(Y, np.log(A)), np.multiply(np.subtract(1, Y), np.log(np.subtract(1, A))))) / m;
		
		dW = np.multiply(np.dot(X, np.transpose(np.subtract(A, Y))), 1/m);
		db = np.sum(np.subtract(A, Y)) / m;
		
		return cost;
	}
	/**
         * Calculates cost after each iteration
         * @param iterations
         * @param learningRate 
         */
	public void optimize(int iterations, double learningRate) {
		LowCostNumpy np = new LowCostNumpy();
		int cnt = iterations;
		while(iterations-- > 0) {
			double cost = propagate();
			W = np.subtract(W, np.multiply(learningRate, dW));
			b = b - learningRate*db;
			if(iterations%100 == 0)
				System.out.println("cost after iteration " + (cnt-iterations) + ": " +cost);
		}
	}
	/**
         * 
         * @param test
         * @return 
         */
	public double[][] predict(double[][] test) {
		LowCostNumpy np = new LowCostNumpy();
		double m = (double)test[0].length;
		double[][] prediction = np.add(b, np.sigmoid(np.dot(np.transpose(W), test)));
		return prediction;
	}
	/**
         * 
         * @param data
         * @return
         * @throws IOException 
         */
	public double[][] predict(String[] data) throws IOException {
		double[][] test = ImageProcessor.imageToMatrix(data);
		LowCostNumpy np = new LowCostNumpy();
		double m = (double)test[0].length;
		double[][] prediction = np.sigmoid(np.add(b, np.dot(np.transpose(W), test)));
		return prediction;
	}
}
