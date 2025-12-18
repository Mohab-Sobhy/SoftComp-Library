package com.softcomp.nn.app;

import com.softcomp.nn.Model;

public class HearDiseaseModel {
    public static void main(String[] args) throws Exception {
        String[] labels = CSVHelper.getAllColumnNames("Heart_disease_cleveland_new.csv");
        double[][] X_test = CSVHelper.selectColumns("X_test.csv",
                new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, 0);
        double[][] Y_test = CSVHelper.selectColumns("Y_test.csv", new int[] { 0 }, 0);
        Model model = Model.loadModel("model.nn");
        double[][] predResults = model.predict(X_test);
        LossPlot.plotLoss(model.getLoses());

        double TN = 0, TP = 0, FP = 0, FN = 0;
        for (int i = 0; i < predResults.length; i++) {
            int pred = predResults[i][0] >= 0.5 ? 1 : 0;
            int actual = (int) Y_test[i][0];

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
