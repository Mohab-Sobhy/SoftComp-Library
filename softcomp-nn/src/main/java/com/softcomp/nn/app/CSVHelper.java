package com.softcomp.nn.app;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static double[][] selectColumns(String filePath, int[] columns) throws Exception {
        List<double[]> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                double[] row = new double[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = Double.parseDouble(line[columns[i]].trim());
                }
                dataList.add(row);
            }
        }

        return dataList.toArray(new double[0][]);
    }

    public static String[] getAllColumnNames(String filePath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readNext();
        }
    }

    public static void writeCSV(String filePath, double[][] data) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (double[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    pw.print(row[i]);
                    if (i < row.length - 1)
                        pw.print(",");
                }
                pw.println();
            }
        }
    }

}
