package com.softcomp.nn.app;

import org.knowm.xchart.*;
import java.util.*;

public class LossPlot {

    public static void plotLoss(List<Double> losses) {
        List<Integer> epochs = new ArrayList<>();
        for (int i = 0; i < losses.size(); i++) {
            epochs.add(i + 1);
        }

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Training Loss")
                .xAxisTitle("Epoch")
                .yAxisTitle("Loss")
                .build();

        chart.addSeries("Loss", epochs, losses);
        new SwingWrapper<>(chart).displayChart();
    }

  

}
