package com.softcomp.nn.lossfunctions;

public interface LossFunction {
   double calculate(double[] pred,double[] actual);
   double[] gradLoss(double[] pred,double[] actual);
}
