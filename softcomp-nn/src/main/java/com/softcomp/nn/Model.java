package com.softcomp.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.softcomp.nn.activationfunction.ActivationFunction;
import com.softcomp.nn.activationfunction.Sigmoid;
import com.softcomp.nn.initializers.WeightInitializer;
import com.softcomp.nn.lossfunctions.LossFunction;
import com.softcomp.nn.model.Network;
import com.softcomp.nn.model.SplitResult;

public class Model {
   Network network;
   LossFunction lossFunction;
   WeightInitializer weightInitializer;
   ActivationFunction activationFunction = new Sigmoid();
   int batchSize;
   int epoches;
   double learningRate;

   Model(Network network) {
      if (network == null) {
         throw new NullPointerException("network cannot be null");
      }
      this.network = network;
   }

   Model() {

   }

   public void train(double[][] X, double[][] Y) {
      for (int epoche = 0; epoche < epoches; epoche++) {
         int nSamples = X.length;
         for (int start = 0; start < nSamples; start += batchSize) {
            int end = Math.min(nSamples, start + batchSize);
            double[][] batchX = Arrays.copyOfRange(X, start, end);
            double[][] batchY = Arrays.copyOfRange(Y, start, end);
            double[][] batchPred = new double[batchX.length][];
            for (int i = 0; i < batchX.length; i++) {
               batchPred[i] = network.forward(batchX[i]);
            }
            double[][] batchGrad = new double[batchX.length][];
            for (int i = 0; i < batchX.length; i++) {
               batchGrad[i] = lossFunction.gradLoss(batchPred[i], batchY[i]);
            }
            double[] avgGrad = new double[batchGrad[0].length];
            for (int i = 0; i < avgGrad.length; i++) {
               double sum = 0;
               for (int j = 0; j < batchGrad.length; j++) {
                  sum += batchGrad[j][i];
               }
               avgGrad[i] = sum / (end - start);
            }
            network.backward(avgGrad, learningRate);
         }
      }
   }

   public double[] predict(double[] input) {
      return network.forward(input);
   }

   public List<double[]> predict(List<double[]> inputs) {
      List<double[]> outputs = new ArrayList<>();
      for (int i = 0; i < inputs.size(); i++) {
         outputs.add(network.forward(inputs.get(i)));
      }
      return outputs;
   }

   public static void shuffleDataset(double[][] X, double[][] Y) {
      Random rand = new Random();
      for (int i = 0; i < X.length; i++) {
         int j = rand.nextInt(X.length);

         double[] tempX = X[i];
         X[i] = X[j];
         X[j] = tempX;

         double[] tempY = Y[i];
         Y[i] = Y[j];
         Y[j] = tempY;
      }
   }

   public static SplitResult trainTestSplit(double[][] X, double[][] Y, double testRatio) {
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

}