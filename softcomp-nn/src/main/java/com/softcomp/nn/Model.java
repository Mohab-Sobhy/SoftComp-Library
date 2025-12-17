package com.softcomp.nn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.softcomp.nn.activationfunction.ActivationFunction;
import com.softcomp.nn.activationfunction.Sigmoid;
import com.softcomp.nn.initializers.WeightInitializer;
import com.softcomp.nn.initializers.XavierNormal;
import com.softcomp.nn.lossfunctions.LossFunction;
import com.softcomp.nn.lossfunctions.MeanSquaredError;
import com.softcomp.nn.model.Layer;
import com.softcomp.nn.model.Network;
import com.softcomp.nn.model.SplitResult;

public class Model implements Serializable {
   private Network network;
   private LossFunction lossFunction = new MeanSquaredError();
   private WeightInitializer weightInitializer = new XavierNormal();
   private ActivationFunction activationFunction = new Sigmoid();
   private int batchSize = 3;
   private int epoches = 100;
   private double learningRate = 0.01;
   private List<Double> loses;

   public Model(Network network) {
      if (network == null) {
         throw new NullPointerException("network cannot be null");
      }
      this.network = network;
   }

   public Model() {
      createNetwork();
   }

   public Model(int numOfLayers, int[] inputSizes, int[] outputSizes,
         ActivationFunction[] activationFunctions, WeightInitializer[] weightInitializers, LossFunction lossFunction) {
      createNetwork(numOfLayers, inputSizes, outputSizes, activationFunctions, weightInitializers, lossFunction);
   }

   public Model(int numOfLayers, int[] inputSizes, int[] outputSizes) {
      createNetwork(numOfLayers, inputSizes, outputSizes);
   }

   public void createNetwork(int numOfLayers, int[] inputSizes, int[] outputSizes,
         ActivationFunction[] activationFunctions, WeightInitializer[] weightInitializers, LossFunction lossFunction) {
      Objects.requireNonNull(inputSizes, "input sizes cannot be null");
      Objects.requireNonNull(outputSizes, "output sizes cannot be null");
      Objects.requireNonNull(activationFunctions, "activaition functions cannot be null");
      Objects.requireNonNull(weightInitializers, "weight initializers functions cannot be null");
      Objects.requireNonNull(weightInitializers, "loss function cannot be null cannot be null");
      Layer[] layers = new Layer[numOfLayers];
      for (int i = 0; i < numOfLayers; i++) {
         Objects.requireNonNull(activationFunctions[i], "activaition function cannot be null");
         Objects.requireNonNull(weightInitializers[i], "weight initializer functions cannot be null");
         layers[i] = new Layer(inputSizes[i], outputSizes[i], activationFunctions[i], weightInitializers[i]);
      }
      this.lossFunction = lossFunction;
      network = new Network(layers);
   }

   public void createNetwork(int numOfLayers, int[] inputSizes, int[] outputSizes) {
      Objects.requireNonNull(inputSizes, "input sizes cannot be null");
      Objects.requireNonNull(outputSizes, "output sizes cannot be null");
      Layer[] layers = new Layer[numOfLayers];
      for (int i = 0; i < numOfLayers; i++) {
         layers[i] = new Layer(inputSizes[i], outputSizes[i], activationFunction, weightInitializer);
      }
      network = new Network(layers);
   }

   public void createNetwork() {
      Layer[] layers = new Layer[3];
      layers[0] = new Layer(4, 4, activationFunction, weightInitializer);
      layers[1] = new Layer(4, 4, activationFunction, weightInitializer);
      layers[2] = new Layer(4, 4, activationFunction, weightInitializer);
      network = new Network(layers);
   }

   public void train(double[][] X, double[][] Y) {
      ValidateXY(X, Y);
      if (X.length == 0) {
         throw new IllegalArgumentException("Training data is empty");
      }

      int nSamples = X.length;
      int inputSize = X[0].length;
      int outputSize = Y[0].length;
      loses = new ArrayList<>();

      for (int epoch = 0; epoch < epoches; epoch++) {
         shuffleDataset(X, Y);

         double epochLossSum = 0.0;

         for (int start = 0; start < nSamples; start += batchSize) {
            int end = Math.min(start + batchSize, nSamples);
            int currentBatchSize = end - start;

            double[][] batchPred = new double[currentBatchSize][outputSize];
            double[][] batchGrad = new double[currentBatchSize][outputSize];

            for (int i = 0; i < currentBatchSize; i++) {
               if (X[start + i].length != inputSize || Y[start + i].length != outputSize) {
                  throw new NullPointerException("the input size or output size does not match the original size");
               }
               batchPred[i] = network.forward(X[start + i]);
               epochLossSum += lossFunction.calculate(batchPred[i], Y[start + i]);
               batchGrad[i] = lossFunction.gradLoss(batchPred[i], Y[start + i]);
               network.backward(batchGrad[i]);
            }

            network.updateWeights(learningRate, currentBatchSize);
         }
         loses.add(epochLossSum / nSamples);

      }
   }

   public double[] predict(double[] input) {
      ValidateX(input);
      return network.forward(input);
   }

   public double[][] predict(double[][] inputs) {
      if (inputs == null) {
         throw new NullPointerException("inputs cannot be null");
      }
      double[][] outputs = new double[inputs.length][];
      for (int i = 0; i < inputs.length; i++) {
         outputs[i] = network.forward(inputs[i]);
      }
      return outputs;
   }

   public static void shuffleDataset(double[][] X, double[][] Y) {
      ValidateXY(X, Y);
      Random rand = new Random();
      for (int i = 0; i < X.length; i++) {
         int j = rand.nextInt(i + 1);

         double[] tempX = X[i];
         X[i] = X[j];
         X[j] = tempX;

         double[] tempY = Y[i];
         Y[i] = Y[j];
         Y[j] = tempY;
      }
   }

   public static SplitResult trainTestSplit(double[][] X, double[][] Y, double testRatio) {
      ValidateXY(X, Y);
      shuffleDataset(X, Y);
      int n = X.length;
      int testSize = (int) (n * testRatio);
      int trainSize = n - testSize;

      double[][] X_train = Arrays.copyOfRange(X, 0, trainSize);
      double[][] Y_train = Arrays.copyOfRange(Y, 0, trainSize);

      double[][] X_test = Arrays.copyOfRange(X, trainSize, n);
      double[][] Y_test = Arrays.copyOfRange(Y, trainSize, n);

      return new SplitResult(X_train, Y_train, X_test, Y_test);
   }

   public static void normalizeMinMax(double[] X) {
      ValidateX(X);
      if (X.length == 0) {
         return;
      }
      double min = X[0], max = X[0];
      for (double val : X) {
         min = Math.min(min, val);
         max = Math.max(max, val);
      }
      for (int i = 0; i < X.length; i++) {
         if (min == max)
            X[i] = 0;
         else
            X[i] = (X[i] - min) / (max - min);
      }
   }

   public static void normalizeMinMax(double[][] X) {
      ValidateX(X);
      if (X.length == 0) {
         return;
      }

      int numSamples = X.length;
      int numFeatures = X[0].length;

      for (int col = 0; col < numFeatures; col++) {
         double[] column = new double[numSamples];
         for (int row = 0; row < numSamples; row++) {
            column[row] = X[row][col];
         }

         normalizeMinMax(column);

         for (int row = 0; row < numSamples; row++) {
            X[row][col] = column[row];
         }
      }
   }

   public static void ValidateXY(double[][] X, double[][] Y) {

      for (int i = 0; i < X.length; i++) {
         if (X[i] == null || Y[i] == null) {
            throw new NullPointerException("Sample at index " + i + " is null");
         }
      }

      if (X == null || Y == null) {
         throw new NullPointerException("X and Y cannot be null");
      }

      if (X.length != Y.length) {
         throw new IllegalArgumentException("number of input samples does not match number of output samples");
      }
   }

   public static void ValidateX(double[] X) {
      if (X == null) {
         throw new NullPointerException("this sample cannot be null");
      }

   }

   public static void ValidateX(double[][] X) {
      if (X == null) {
         throw new NullPointerException("these samples cannot be null");
      }
   }

   ///////
   ///
   ///
   public Network getNetwork() {
      return network;
   }

   public LossFunction getLossFunction() {
      return lossFunction;
   }

   public WeightInitializer getWeightInitializer() {
      return weightInitializer;
   }

   public ActivationFunction getActivationFunction() {
      return activationFunction;
   }

   public int getBatchSize() {
      return batchSize;
   }

   public int getEpoches() {
      return epoches;
   }

   public double getLearningRate() {
      return learningRate;
   }

   public List<Double> getLoses() {
      return loses;
   }

   public void setNetwork(Network network) {
      if (network == null) {
         throw new NullPointerException("Network cannot be null");
      }
      this.network = network;
   }

   public void setLossFunction(LossFunction lossFunction) {
      if (lossFunction == null) {
         throw new NullPointerException("Loss function cannot be null");
      }
      this.lossFunction = lossFunction;
   }

   public void setWeightInitializer(WeightInitializer weightInitializer) {
      if (weightInitializer == null) {
         throw new NullPointerException("Weight initializer cannot be null");
      }
      this.weightInitializer = weightInitializer;
   }

   public void setActivationFunction(ActivationFunction activationFunction) {
      if (activationFunction == null) {
         throw new NullPointerException("Activation function cannot be null");
      }
      this.activationFunction = activationFunction;
   }

   public void setBatchSize(int batchSize) {
      if (batchSize <= 0) {
         throw new IllegalArgumentException("Batch size must be greater than 0");
      }
      this.batchSize = batchSize;
   }

   public void setEpoches(int epoches) {
      if (epoches <= 0) {
         throw new IllegalArgumentException("Number of epochs must be greater than 0");
      }
      this.epoches = epoches;
   }

   public void setLearningRate(double learningRate) {
      if (learningRate <= 0) {
         throw new IllegalArgumentException("Learning rate must be greater than 0");
      }
      this.learningRate = learningRate;
   }

   public void saveModel(String path) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {

         oos.writeObject(this);

      } catch (IOException e) {
         throw new RuntimeException("Failed to save model", e);
      }
   }

   public static Model loadModel(String path) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {

         return (Model) ois.readObject();

      } catch (IOException | ClassNotFoundException e) {
         throw new RuntimeException("Failed to load model", e);
      }
   }

}