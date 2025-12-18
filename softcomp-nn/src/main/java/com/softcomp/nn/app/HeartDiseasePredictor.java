package com.softcomp.nn.app;

import com.softcomp.nn.Model;
import com.softcomp.nn.activationfunction.ReLU;
import com.softcomp.nn.activationfunction.Sigmoid;
import com.softcomp.nn.initializers.XavierNormal;
import com.softcomp.nn.lossfunctions.BinaryCrossEntropy;

import com.softcomp.nn.model.SplitResult;

public class HeartDiseasePredictor {

    public static void main(String[] args) throws Exception {
        String[] labels = CSVHelper.getAllColumnNames("Heart_disease_cleveland_new.csv");
        System.out.println(labels[13]);
        double[][] X = CSVHelper.selectColumns("Heart_disease_cleveland_new.csv",
                new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, 1);
        double[][] Y = CSVHelper.selectColumns("Heart_disease_cleveland_new.csv",
                new int[] { 13 }, 1);
        System.out.println("Dataset: " + X.length + " patients, " + X[0].length + " features");
        Model.normalizeMinMax(X);
        SplitResult split = Model.trainTestSplit(X, Y, 0.3);
        System.out.println("Training on " + split.X_train.length + " samples");
        System.out.println("Testing on " + split.X_test.length + " samples\n");
        Model model = new Model();
        model.setWeightInitializer(new XavierNormal());
        model.setLossFunction(new BinaryCrossEntropy());
        model.setActivationFunction(new ReLU());
        model.setLearningRate(0.01);
        model.setBatchSize(32);
        model.setEpoches(1000);
        model.createNetwork(3,
                new int[] { 13, 32, 16 },
                new int[] { 32, 16, 1 });
        model.getNetwork().getLayers()[model.getNetwork().getLayers().length - 1].setActivationFunction(new Sigmoid());

        model.train(split.X_train, split.Y_train);
        CSVHelper.writeCSV("X_test.csv", split.X_test);
        CSVHelper.writeCSV("Y_test.csv", split.Y_test);
        model.saveModel("model.nn");

        double[][] predResults = model.predict(split.X_test);
        LossPlot.plotLoss(model.getLoses());

        double TN = 0, TP = 0, FP = 0, FN = 0;
        for (int i = 0; i < predResults.length; i++) {
            int pred = predResults[i][0] >= 0.5 ? 1 : 0;
            int actual = (int) split.Y_test[i][0];

            if (pred == 1 && actual == 1) {
                TP++;
            } else if (pred == 1 && actual == 0) {
                FP++;
            } else if (pred == 0 && actual == 0) {
                TN++;
            } else if (pred == 0 && actual == 1) {
                FN++;
            }
        }
        System.out.println("accuracy is " + ((TN + TP) / predResults.length) * 100 + "%");
        System.out.println("precision is " + ((TP) / (TP + FP)) * 100 + "%");
        System.out.println("Recall is " + ((TP) / (TP + FN)) * 100 + "%");

    }
}