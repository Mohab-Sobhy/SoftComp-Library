package com.softcomp.nn;

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
import com.softcomp.nn.model.Layer;
import com.softcomp.nn.model.Network;
import com.softcomp.nn.model.SplitResult;

public class Model {
   private Network network;
   private LossFunction lossFunction;
   private WeightInitializer weightInitializer=new XavierNormal();
   private ActivationFunction activationFunction = new Sigmoid();
   private int batchSize=3;
   private int epoches=100;
   private double learningRate=0.2;
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
   public Model(int numOfLayers,int[] inputSizes,int[] outputSizes,
      ActivationFunction[] activationFunctions,WeightInitializer[] weightInitializers,LossFunction lossFunction){
     createNetwork(numOfLayers, inputSizes, outputSizes, activationFunctions, weightInitializers, lossFunction);
   }
   public Model(int numOfLayers,int[] inputSizes,int[] outputSizes){
       createNetwork(numOfLayers, inputSizes, outputSizes);
   }
   public void createNetwork(int numOfLayers,int[] inputSizes,int[] outputSizes,
      ActivationFunction[] activationFunctions,WeightInitializer[] weightInitializers,LossFunction lossFunction){
          Objects.requireNonNull(inputSizes,"input sizes cannot be null");
      Objects.requireNonNull(outputSizes,"output sizes cannot be null");
      Objects.requireNonNull(activationFunctions,"activaition functions cannot be null");
      Objects.requireNonNull(weightInitializers,"weight initializers functions cannot be null");
      Objects.requireNonNull(weightInitializers,"loss function cannot be null cannot be null");
      Layer[] layers=new Layer[numOfLayers];
      for(int i=0;i<numOfLayers;i++){
         Objects.requireNonNull(activationFunctions[i],"activaition function cannot be null");
         Objects.requireNonNull(weightInitializers[i],"weight initializer functions cannot be null");
         layers[i]=new Layer(inputSizes[i], outputSizes[i], activationFunctions[i], weightInitializers[i]);
      }
      this.lossFunction=lossFunction;
      network=new Network(layers);
      }
      public void createNetwork(int numOfLayers,int[] inputSizes,int[] outputSizes){
          Objects.requireNonNull(inputSizes,"input sizes cannot be null");
      Objects.requireNonNull(outputSizes,"output sizes cannot be null");
        Layer[] layers=new Layer[numOfLayers];
      for(int i=0;i<numOfLayers;i++){
         layers[i]=new Layer(inputSizes[i], outputSizes[i], activationFunction, weightInitializer);
      }
      network=new Network(layers);
      }
      public void createNetwork(){
          Layer[] layers=new Layer[3];
      layers[0]=new Layer(4, 4, activationFunction, weightInitializer);
      layers[1]=new Layer(4, 4, activationFunction, weightInitializer);
      layers[2]=new Layer(4, 4, activationFunction, weightInitializer);
      network=new Network(layers);
      }
  
   public void train(double[][] X, double[][] Y) {
      double epocheLossSum=0;
      double count=0;
      ValidateXY(X, Y);
      if (X.length == 0) {
       throw new IllegalArgumentException("Training data is empty");
     }

      int xSize=X[0].length,ySize=Y[0].length;
      loses=new ArrayList<>();
      for (int epoche = 0; epoche < epoches; epoche++) {
         int nSamples = X.length;
         for (int start = 0; start < nSamples; start += batchSize) {
            int end = Math.min(nSamples, start + batchSize);
            double[][] batchX = Arrays.copyOfRange(X, start, end);
            double[][] batchY = Arrays.copyOfRange(Y, start, end);
            double[][] batchPred = new double[batchX.length][];
            for (int i = 0; i < batchX.length; i++) {
               if(batchX[i].length!=xSize){
                  throw new IllegalArgumentException("this input sample does not match the orignial input sample");
               }
               batchPred[i] = network.forward(batchX[i]);
               epocheLossSum+=lossFunction.calculate(batchPred[i], batchY[i]);
               count++;
            }
            double[][] batchGrad = new double[batchX.length][];
            for (int i = 0; i < batchX.length; i++) {
                if(batchY[i].length!=ySize){
                  throw new IllegalArgumentException("this output sample does not match the orignial output sample");
               }
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
         loses.add(epocheLossSum/count);
         epocheLossSum=0;
         count=0;
      }
   }

   public double[] predict(double[] input) {
      ValidateX(input);
      return network.forward(input);
   }

   public List<double[]> predict(List<double[]> inputs) {
      if(inputs==null){
         throw new NullPointerException("inputs cannot be null");
      }
      List<double[]> outputs = new ArrayList<>();
      for (int i = 0; i < inputs.size(); i++) {
         outputs.add(network.forward(inputs.get(i)));
      }
      return outputs;
   }

   public static void shuffleDataset(double[][] X, double[][] Y) {
      ValidateXY(X, Y);
      Random rand = new Random();
      for (int i = 0; i < X.length; i++) {
         int j = rand.nextInt(i+1);

         double[] tempX = X[i];
         X[i] = X[j];
         X[j] = tempX;

         double[] tempY = Y[i];
         Y[i] = Y[j];
         Y[j] = tempY;
      }
   }

   public static SplitResult trainTestSplit(double[][] X, double[][] Y, double testRatio) {
      ValidateXY(X,Y);
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

   public static void normalizeMinMax(double[][] X){
      ValidateX(X);
      for(int i=0;i<X.length;i++){
         normalizeMinMax(X[i]);
      }
   }
   public static void ValidateXY(double[][] X, double[][] Y){

      for (int i = 0; i < X.length; i++) {
    if (X[i] == null || Y[i] == null) {
        throw new NullPointerException("Sample at index " + i + " is null");
    }
}

      if (X == null || Y == null) {
    throw new NullPointerException("X and Y cannot be null");
}

      if(X.length!=Y.length){
         throw new IllegalArgumentException("number of input samples does not match number of output samples");
      }
   }

    public static void ValidateX(double[] X){
         if (X == null) {
            throw new NullPointerException("this sample cannot be null");
         }

   }

   public static void ValidateX(double[][] X){
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
}